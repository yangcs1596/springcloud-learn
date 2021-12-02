package com.safedog.common.autoconfigure.idgenerator;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wuxin
 */
@Data
@ConfigurationProperties("notary.id")
public class IdGeneratorProperties {

    private Long workerId;
    private Long datacenterId;

    private MyBatis mybatis;

    public static class MyBatis {

        boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

}
