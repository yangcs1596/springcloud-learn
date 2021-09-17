package com.safedog.common.autoconfig.property;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "encrypt.config")
public class EncryptablePropertyResolverProperties {
    private String key = "ycs2021learn";
    private String prefix = "ENC(";
    private String suffix = ")";
    private String version = "1.0";
}
