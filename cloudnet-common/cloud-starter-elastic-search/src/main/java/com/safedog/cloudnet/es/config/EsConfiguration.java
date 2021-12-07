package com.safedog.cloudnet.es.config;

import com.google.gson.GsonBuilder;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * 注册JEST客户端
 *
 */
@Configuration
public class EsConfiguration {

    @Value("${elasticsearch.jest.uris:}")
    private String uris;

    @Value("${elasticsearch.jest.user:}")
    private String user;

    @Value("${elasticsearch.jest.passport:}")
    private String passport;

    @Value("${elasticsearch.jest.connection-timeout: 20000}")
    private int connectionTimeOut;

    @Value("${elasticsearch.jest.read-timeout: 20000}")
    private int readTimeOut;

    @Bean
    public JestClient jestClient() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(Collections.singletonList(uris))
                .defaultCredentials(user, passport)
                .gson(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create())
                .multiThreaded(true)
                .connTimeout(connectionTimeOut)
                .readTimeout(readTimeOut)
                .build());
        return factory.getObject();
    }
}
