package com.safedog.common.autoconfigDuplicate.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "encrypt.config")
public class EncryptablePropertyResolverProperties {
    private String key = "ycs2021learn";
    private String prefix = "ENC(";
    private String suffix = ")";
    private String version = "1.0";
}
