package com.safedog.common.ipsearch.model;

import com.safedog.common.ipsearch.search.FileMode;

/**
 * @author ymz
 * @date 2019/12/12 15:28
 */
public interface IPSearchConstant {
    /**
     * don`t edit
     */
    int HEAD_BLOCK_LENGTH = 8;

    String IP_INFO_NEW = "ip_info_new.csv";
    String IP_MERGE = "ip_merge.txt";
    String SEARCH_DB = "ipsearch_v1.2.db";
    String ERROR_LOG = "error_log.txt";

    String IPSEARCH_PROP_FILE = "ipsearch.properties";

    String IPSEARCH_DB_TYPE_KEY = "ipsearch.db.type";

    String IPSEARCH_DB_FILEPATH = "ipsearch.db.filepath";
    /**
     * show dev log
     */
    boolean DEV_DEBUG = true;
    /**
     * enable gzip
     */
    boolean GZIP = true;

    /**
     * db File Read Mode
     */
    FileMode FILE_MODE = FileMode.MEMORY_MAPPED;
}
