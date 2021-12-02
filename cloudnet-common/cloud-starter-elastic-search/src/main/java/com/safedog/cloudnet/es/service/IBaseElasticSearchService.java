package com.safedog.cloudnet.es.service;

/**
 * @author ycs
 * @description
 * @date 2021/11/19 9:13
 */
public interface IBaseElasticSearchService<T> {
    void createIndex(String index);

    void deleteIndex(String index);

//    (T t) void insertDataByBulk;
}
