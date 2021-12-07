package com.safedog.cloudnet.es;

import com.alibaba.fastjson.JSONObject;
import com.safedog.cloudnet.CloudnetApplicationTests;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ycs
 * @description
 * @date 2021/12/2 20:09
 */
@Slf4j
public class ElasticSearchUtilsTest extends CloudnetApplicationTests {
    @Autowired
    private JestClient jestClient;
    @Test
    public void testQuery(){
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            searchSourceBuilder.query(boolQueryBuilder);
            Search build = new Search.Builder(searchSourceBuilder.toString()).addIndex("cloudarmor_asset_app_risk_index").addType("AssetAppRiskInfo").build();
            SearchResult execute = jestClient.execute(build);
            log.info(JSONObject.toJSONString(execute));
        } catch (Exception e) {
            log.info(e.getMessage());
        }


    }
}
