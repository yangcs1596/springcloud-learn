package com.safedog.common.ipsearch.make;

import com.safedog.common.ipsearch.model.IPSearchConstant;
import com.safedog.common.ipsearch.utils.ByteUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 一键生成search.db
 * 可以通过配置IpSearchConstant的GZIP来控制是否压缩
 *
 * @author ymz
 * @date 2020/04/22 11:10
 */
@Slf4j
public class MakeMain implements IPSearchConstant {
    public static final String WRITE_DIR = "E:/IP_city_single_WGS84_mysql/";

    public static final String SRC_FILE_PATH = WRITE_DIR + IP_MERGE;
    public static final String SORTED_FILE_PATH = WRITE_DIR + "sorted_" + IP_MERGE;
    public static final String SEARCH_DB_PATH = WRITE_DIR + SEARCH_DB;

    public static void main(String[] args) throws Exception{
        make();
//        oneKeyMake();
    }

    /**
     * 一键生成search.db
     * 可以通过配置IpSearchConstant的GZIP来控制是否压缩
     */
    public static void oneKeyMake() {
        try {
            // 将ip_info_new.csv压缩转换成特定格式
//            Adapter.myIpInfo2IpMerge();
            // 使用make()方法生成search.db
            DatMaker datMaker = new DatMaker();
            datMaker.make(SEARCH_DB_PATH, SRC_FILE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void make() throws IOException{
        Adapter adapter = new Adapter();
        StopWatch sw1 = new StopWatch("sort");
        log.info("开始排序文件");
        sw1.start();
//        adapter.sortFile(SRC_FILE_PATH, SORTED_FILE_PATH);
        sw1.stop();
        log.info("结束排序文件,耗时{}s", sw1.getTime(TimeUnit.SECONDS));
        // 使用make()方法生成search.db
        log.info("开始构建IP库");
        StopWatch sw2 = new StopWatch("make");
        sw2.start();
        DatMaker datMaker = new DatMaker();
        datMaker.make(SEARCH_DB_PATH, SORTED_FILE_PATH);
        sw2.stop();
        log.info("结束构建IP库,耗时{}s", sw2.getTime(TimeUnit.SECONDS));
    }

}
