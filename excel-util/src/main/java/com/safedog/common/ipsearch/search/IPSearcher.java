package com.safedog.common.ipsearch.search;

import com.safedog.common.ipsearch.adapter.AWDBSearchAdapter;
import com.safedog.common.ipsearch.adapter.IpSearchAdapter;
import com.safedog.common.ipsearch.model.DataBlock;
import com.safedog.common.ipsearch.model.IPInfoDataVO;
import com.safedog.common.ipsearch.model.IPSearchConstant;
import com.safedog.common.ipsearch.utils.ByteUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

/**
 * Searcher
 *
 * @author ymz
 * @date 2019-12-16 00:13
 **/
@Slf4j
public class IPSearcher implements IPSearchConstant {
    private SearchProvider searchProvider;

    private IPSearcher() {
        try {
            Properties props = this.getIpSearcherProps();
            IPSearchDBType dbType = this.resolveIPSearchDBType(props);
            if (Objects.equals(IPSearchDBType.AWDB, dbType)) {
                String dbFilePathStr = props.getProperty(IPSEARCH_DB_FILEPATH);
                File dbFile = new File(dbFilePathStr);
                if (!dbFile.isFile() || !dbFile.exists() || !dbFile.canRead()) {
                    throw new IllegalArgumentException("ip search db path :[" + dbFilePathStr + "] is [not a file|not exist|can't read]");
                }
                this.searchProvider = new AWDBSearchAdapter(dbFile);
                log.info("Use awdb IpSearch db,DB File Path: [{}]",dbFile.getAbsolutePath());
            }else {
                this.searchProvider = new IpSearchAdapter();
                log.info("Use safedog IpSearch db");
            }
        } catch (Exception e) {
            log.error("Load ip search props error.", e);
            log.info("Use safedog IpSearch db.");
            searchProvider = new IpSearchAdapter();
        }
    }

    private IPSearchDBType resolveIPSearchDBType(Properties properties) {
        if (Objects.isNull(properties) || properties.isEmpty()) {
            return IPSearchDBType.SDDB;
        }
        try {
            return IPSearchDBType.fromCode(Integer.parseInt(properties.getProperty(IPSEARCH_DB_TYPE_KEY)))
                    .orElse(IPSearchDBType.SDDB);
        } catch (Exception e) {
            log.error("Resolve IPSearchDBType Exception.", e);
            return IPSearchDBType.SDDB;
        }
    }

    private Properties getIpSearcherProps() {
        InputStream is = IPSearcher.class.getClassLoader().getResourceAsStream(IPSEARCH_PROP_FILE);
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (NullPointerException|IOException e) {
            log.error("Load IpSearch Props File Error,File: [{}].", IPSEARCH_PROP_FILE);
        }
        return properties;
    }

    /**
     * memorySearch ip
     * @param targetIp ip
     * @return
     */
    public static DataBlock memorySearch(long targetIp) {
        IPInfoDataVO ipInfoDataVO = getInstance().getSearchProvider().search(targetIp);
        return Optional.ofNullable(ipInfoDataVO).map(DataBlock::new).orElse(null);
    }

    /**
     * memorySearch
     * @param ip ip
     * @return
     */
    public static DataBlock memorySearch(String ip) {
        IPInfoDataVO ipInfoDataVO = getInstance().getSearchProvider().search(ByteUtil.ipToLong(ip));
        return Optional.ofNullable(ipInfoDataVO).map(DataBlock::new).orElse(null);
    }

    public SearchProvider getSearchProvider() {
        return searchProvider;
    }

    public static class Instance {
        public static IPSearcher INSTANCE = new IPSearcher();
    }


    public static IPSearcher getInstance() {
        return Instance.INSTANCE;
    }
}
