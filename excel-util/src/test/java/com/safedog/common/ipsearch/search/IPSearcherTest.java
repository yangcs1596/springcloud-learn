package com.safedog.common.ipsearch.search;

import com.alibaba.fastjson.JSON;
import com.safedog.common.ipsearch.model.DataBlock;
import com.safedog.common.ipsearch.model.IPInfoDataVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ymz
 * @date 2021/08/20 9:43
 */
@Slf4j
public class IPSearcherTest {

    @Test
    public void memorySearch() {
        DataBlock dataBlock = IPSearcher.memorySearch("1.0.1.1");
        System.out.println(dataBlock.toString());
    }

    @Test
    public void testMemorySearch() throws Exception{
        log.info("初始化IP查询库");

        StopWatch sw = new StopWatch("init");
        sw.start();
        IPSearcher.memorySearch("1.1.1.1");
        sw.stop();
        log.info("初始化结束,耗时: {}s", sw.getTime(TimeUnit.SECONDS));
        String[] ips = {
                "223.255.252.1",
                "223.255.253.1",
                "223.255.254.1",
                "223.255.255.1",
                "224.0.0.1",
                "0.0.0.1",
                "1.0.0.1",
                "1.0.1.1",
                "1.0.4.1",
                "1.0.8.1",
                "8.8.8.8",
                "114.114.114.114",
                "223.5.5.5",
                "180.76.76.76",
                "117.30.73.194"
        };
        StopWatch s = new StopWatch();
        for (String ip : ips) {
            s.reset();
            s.start();
            DataBlock dataBlock = IPSearcher.memorySearch(ip);
//            System.out.println(dataBlock.toString());
            s.stop();
            log.info("查询IP: {} --> {},耗时: {}ms", ip, dataBlock.toString(), s.getTime(TimeUnit.MILLISECONDS));
        }
        log.info("查询结束");
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void testGetIPInfoDataVO() throws Exception{
        List<String> ipList = Stream.of("28.0.0.251","67.132.44.177","5.70.165.245","185.57.156.29","204.81.84.42","9.187.238.90","162.30.169.70","217.157.216.112","206.255.196.239","4.45.44.138","220.129.138.31","77.246.40.126","7.29.147.12","175.3.5.125","177.227.43.246","197.65.226.94","6.127.118.109","206.142.78.78","71.66.60.227","178.70.247.228","2.54.250.216","53.105.93.231","26.105.45.213","204.158.115.55","197.217.237.246","210.189.163.169","90.195.102.227","66.156.149.224","9.53.177.69","85.57.163.75","2.47.32.57").collect(Collectors.toList());
        ipList.forEach(ip -> {
            IPInfoDataVO ipInfoDataVO = IPSearcher.memorySearch(ip).getIPInfoDataVO();
            System.out.println("ip:" + ip + ",ipInfoDataVO:" + JSON.toJSONString(ipInfoDataVO));
        });

    }
}
