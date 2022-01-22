package com.safedog.common.ipsearch.make;

import com.safedog.common.ipsearch.model.IPSearchConstant;
import com.safedog.common.ipsearch.utils.ByteUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Objects;
import java.util.TreeMap;

/**
 * convert ipinfo csv file to IP_MERGE txt file
 *
 * @author ymz
 * @date 2019/12/12 18:35
 */
@Slf4j
public class Adapter implements IPSearchConstant {
    public void convert(File srcFile, File dstFile) throws IOException{
        // binary search needs to be sorted
        TreeMap<Integer, String> treeMap = new TreeMap<>((key1, key2) -> {
            if (key1 == null || key2 == null) {
                return 0;
            }
            return key1.compareTo(key2);
        });
        Objects.requireNonNull(srcFile);
        Objects.requireNonNull(dstFile);

        BufferedReader bfr = new BufferedReader(new FileReader(srcFile));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dstFile));

        log.info("start IpInfo2IpMerge...");
        String str = "";
        int count = 0;
        while ((str = bfr.readLine()) != null) {
            if (str.startsWith("#")) {
                continue;
            }
            str = str.replaceAll("\"", "");
            if (str.trim().length() <= 0) {
                continue;
            }
            str = str.replaceAll("\\*", "0");
            //"614","001.045.000.000","001.045.255.255","19726336","19791871","中国","北京","北京","cheeryzone.com.cn","联通","*
            // "13","001.001.008.000","001.001.008.255","16844800","16845055","中国","广东","珠海","*"                ,"电信","*"
            //  0      1                  2                   3         4       5      6    7      8                    9   10
            String[] split = str.split(",");
            if (split.length < 10) {
                continue;
            }
            String sip = split[1].trim();
            String eip = split[2].trim();
            //--------------国家----------------|-----省/特区-----------|--------市--------------|------运营商-------
            String region = split[5].trim() + "|" + split[6].trim() + "|" + split[7].trim() + "|" + split[9].trim();
            if (DEV_DEBUG) {
                if (ByteUtil.ipToInteger(eip) < ByteUtil.ipToInteger(sip)) {
                    System.out.println(sip);
                    System.out.println(eip);
                    System.out.println(region);
                    break;
                }
            }
            String line = sip + "|" + eip + "|" + region;
            treeMap.put(ByteUtil.ipToInteger(split[1]), line);
            count++;
        }
        treeMap.forEach((k, v) -> {
            try {
                bufferedWriter.write(v);
                bufferedWriter.newLine();
            } catch (IOException e) {
                // ignore
            }
        });
        log.info("read lines: " + count);
        bufferedWriter.flush();
        bufferedWriter.close();
        log.info("build success.");
    }

    public void sortFile(String readFilePath,String writeFilePath) throws IOException{
        TreeMap<Integer, String> treeMap = new TreeMap<>((key1, key2) -> {
            if (key1 == null || key2 == null) {
                return 0;
            }
            return key1.compareTo(key2);
        });

        BufferedReader bfr = new BufferedReader(new FileReader(readFilePath));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(writeFilePath));
        log.info("start Sort...");
        String str = "";
        int count = 0;
        while ((str = bfr.readLine()) != null) {
            String[] split = str.split("\\|");
            String line = str;
            String sip = split[0];
            treeMap.put(ByteUtil.ipToInteger(sip), line);
            count++;
        }
        treeMap.forEach((k, v) -> {
            try {
                bufferedWriter.write(v);
                bufferedWriter.newLine();
            } catch (IOException e) {
                // ignore
            }
        });
        log.info("read lines: " + count);
        bufferedWriter.flush();
        bufferedWriter.close();
        log.info("build success.");
    }

    public static void myIpInfo2IpMerge() throws Exception {
        Adapter adapter = new Adapter();
        adapter.convert(ByteUtil.checkFileExists(ByteUtil.getPath(IP_INFO_NEW)), ByteUtil.checkFileAndNew(ByteUtil.getPath(IP_MERGE)));
    }
}
