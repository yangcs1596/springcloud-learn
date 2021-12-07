//package com.safedog.cloudnet.es.service.impl;
//
//import com.safedog.cloudnet.es.service.IBaseElasticSearchService;
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
//import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//
//import java.io.IOException;
//
///**
// * @author ycs
// * @description
// * @date 2021/11/19 9:15
// */
//@Slf4j
//public abstract class BaseElasticSearchImpl implements IBaseElasticSearchService {
//    @Autowired
//    private ElasticsearchRestTemplate elasticsearchTemplate;
//    @Autowired
//    private ElasticsearchRepository elasticRepository;
//
//    private Pageable pageable = PageRequest.of(0,10);
//
//    @Autowired
//    private RestHighLevelClient restHighLevelClient;
//
//    @Override
//    public void createIndex(String index) {
//        CreateIndexResponse response = null;
//        try {
//            // 1. 创建索引请求
//            CreateIndexRequest firstIndex = new CreateIndexRequest(index);
//            // 2. 客户端执行创建索引的请求
//             response = restHighLevelClient.indices().create(firstIndex, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            log.info("createIndex ============={}", response);
//        }
//    }
//
//    @Override
//    public void deleteIndex(String index) {
//    }
//}
