//package com.safedog.cloudnet.es.util;
//
//import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//
//import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
//
///**
// * @author ycs
// * @description
// * @date 2021/11/19 9:04
// */
//public class ElasticSearchUtils {
//
//    private ElasticsearchRestTemplate elasticsearchTemplate;
//
//    private ElasticsearchRepository elasticRepository;
//
//    private Pageable pageable = PageRequest.of(0,10);
//
//
//    public static void jsonBuild() throws Exception{
//        XContentBuilder script = jsonBuilder().startObject().field("script", "ctx._source.age=15").endObject();
//        script.flush();
//        System.out.println(script.string());
//    }
//
//    public static void main(String[] args) {
//        try {
//            jsonBuild();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
