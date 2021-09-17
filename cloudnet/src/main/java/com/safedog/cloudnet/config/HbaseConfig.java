package com.safedog.cloudnet.config;

import com.safedog.cloudnet.template.HBaseTemplate;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HbaseConfig {
    @Bean
    @ConditionalOnBean(HadoopConfig.class)
    public HBaseTemplate hBaseConfiguration(){
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.client.retries.number", "0");
        configuration.set("hbase.zookeeper.quorum", "192.168.89.246");
        /**注意要映射好本地的域名 ===》  对应在hbase安装目录下conf的hbase-site.xml 的配置hbase.zookeeper.quorum 域名地址**/
        return new HBaseTemplate(configuration);
    }
}
