package com.safedog.common.ipsearch.search;

import com.alibaba.fastjson.JSONArray;
import com.safedog.common.ipsearch.model.DataBlock;
import com.safedog.common.ipsearch.utils.ByteUtil;
import com.safedog.common.ipsearch.utils.GZipUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static com.safedog.common.ipsearch.model.IPSearchConstant.*;

@Slf4j
public class Reader {

    private final Object LOCK = new Object();
    private boolean memory_mode_load = false;
    /**
     * for memory mode
     */
    private int[] ipSegments;
    private int[] ipRegionPtr;
    private short[] ipRegionLen;

    /**
     * for memory mode
     * the original db binary string
     */
    private byte[] dataRegion = null;

    private BufferHolder bufferHolder;

    private final File db;
    private final FileMode fileMode;

    public Reader(File db, FileMode fileMode) {
        this.db = db;
        this.fileMode = fileMode;
    }


    public DataBlock search(long targetIp) {
        int ip = (int) targetIp;
        // load data
        if (!memory_mode_load) {
            synchronized (LOCK) {
                if (!memory_mode_load) {
                    loadFileToMemoryMode();
                }
            }
        }

        if (ipSegments == null || ipSegments.length == 0) {
            throw new IllegalArgumentException("initialization failed...");
        }

        int index = binarySearch(ipSegments, 0, ipSegments.length - 1, ip);

        if (index == -1) {
            return null;
        }
        int dataPtr = ipRegionPtr[index >> 1];
        short len = ipRegionLen[index >> 1];
//        String region = new String(dataRegion, dataPtr, len, StandardCharsets.UTF_8).trim();
        byte[] drArr = new byte[len];
        ByteBuffer byteBuffer = bufferHolder.getBuffer();
        byteBuffer.rewind();
        byteBuffer.position(dataPtr);
        byteBuffer.get(drArr, 0, len);
        String region = new String(drArr, StandardCharsets.UTF_8).trim();
        return new DataBlock(region, dataPtr);
    }

    /**
     * binarySearch
     * @param arr
     * @param low
     * @param high
     * @param searchNumber
     * @return
     */
    private int binarySearch(int[] arr, int low, int high, long searchNumber) {
        int mid;
        while (low <= high) {
            mid = (low + high) >> 1;
            if (arr[mid] > searchNumber) {
                high = mid - 1;
            } else if (arr[mid] < searchNumber) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        if (low > arr.length - 1 || high < 0) {
            return -1;
        }
        return high;
    }

    /**
     * load data into memory
     */
    private void loadFileToMemoryMode() {
        File searchDb = this.db;
        if (GZIP) {
            GZipUtils.decompress(this.db, false);
            searchDb = new File(ByteUtil.getPath(SEARCH_DB));
        }
        RandomAccessFile raf = null;
        try {
            StopWatch sw = new StopWatch();
            sw.start();

            raf = new RandomAccessFile(searchDb, "r");

            byte[] headBlock = new byte[HEAD_BLOCK_LENGTH];
            raf.seek(0L);
            raf.readFully(headBlock, 0, HEAD_BLOCK_LENGTH);
            // head block
            long dataEndPtr = ByteUtil.get32Long(headBlock, 0);
            long ipSegmentsEndPrt = ByteUtil.get32Long(headBlock, 4);
            // length
            int dataLen = (int) dataEndPtr;
            // container
//            dataRegion = new byte[dataLen];
            final FileChannel channel = raf.getChannel();
            bufferHolder = new BufferHolder(channel, dataLen, this.fileMode);
            byte[] searchInfoBytes = new byte[(int) (ipSegmentsEndPrt - dataEndPtr)];
            // read file
            raf.seek(0);
//            raf.readFully(dataRegion, 0, dataLen);
            raf.seek(dataEndPtr);
            raf.readFully(searchInfoBytes, 0, searchInfoBytes.length);
            // deserialization
            deserialization(searchInfoBytes);
            sw.stop();
            log.info("load file cost time: {}ms", sw.getTime(TimeUnit.MILLISECONDS));
            memory_mode_load = true;
        } catch (IOException o) {
            throw new RuntimeException("load file error.", o);
        } finally {
            ByteUtil.ezIOClose(raf);
            if (GZIP) {
                ByteUtil.fileDel(ByteUtil.getPath(SEARCH_DB));
            }
        }

    }

    /**
     * deserialization
     * @param searchInfoBytes json bytes
     */
    private void deserialization(byte[] searchInfoBytes) {
        StopWatch unzipSw = new StopWatch();
        unzipSw.start();
        String unzipJsonStr = ByteUtil.unzipString(searchInfoBytes);
        unzipSw.stop();
        if (log.isDebugEnabled()) {
            log.debug("Unzip searchInfoBytes cost time: {} ms", unzipSw.getTime(TimeUnit.MILLISECONDS));
        }
        StopWatch parseArrSw = new StopWatch();
        parseArrSw.start();
        JSONArray jsonArray = JSONArray.parseArray(unzipJsonStr);
        parseArrSw.stop();
        if (log.isDebugEnabled()) {
            log.debug("Parse searchInfoArr cost time: {} ms", parseArrSw.getTime(TimeUnit.MILLISECONDS));
        }
        JSONArray arrayList0 = jsonArray.getJSONArray(0);
        JSONArray arrayList1 = jsonArray.getJSONArray(1);
        JSONArray arrayList2 = jsonArray.getJSONArray(2);
        ipSegments = new int[arrayList0.size()];
        ipRegionPtr = new int[arrayList1.size()];
        ipRegionLen = new short[arrayList2.size()];
        StopWatch ipSegmentSw = new StopWatch();
        ipSegmentSw.start();
        for (int i = 0; i < arrayList0.size(); i++) {
            ipSegments[i] = (int) arrayList0.get(i);
        }
        ipSegmentSw.stop();
        if (log.isDebugEnabled()) {
            log.debug("IpSegment Arr cost time: {} ms", ipSegmentSw.getTime(TimeUnit.MILLISECONDS));
        }

        StopWatch ipRegionSw = new StopWatch();
        ipRegionSw.start();
        for (int i = 0; i < arrayList1.size(); i++) {
            ipRegionPtr[i] = (int) arrayList1.get(i);
            ipRegionLen[i] = (short) (int) arrayList2.get(i);
        }
        ipRegionSw.stop();
        if (log.isDebugEnabled()) {
            log.debug("IpRegion Arr cost time: {} ms", ipRegionSw.getTime(TimeUnit.MILLISECONDS));
        }
    }
}
