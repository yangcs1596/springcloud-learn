package com.safedog.common.ipsearch.search;

import com.safedog.common.ipsearch.make.Adapter;
import org.junit.Test;

import java.io.File;

import static com.safedog.common.ipsearch.model.IPSearchConstant.IP_MERGE;

public class TestAdapter {
    Adapter adapter = new Adapter();
    @Test
    public void testMyIpInfo2IpMerge() throws Exception{
        File mergedFile = new File("src/test/resources/" + IP_MERGE);
//        mergedFile.deleteOnExit();
        adapter.convert(new File("src/test/resources/ip_info_new.csv"), mergedFile);
    }
}
