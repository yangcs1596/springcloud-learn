package com.safedog.common.autoconfig.property;

import com.safedog.common.util.encryptor.StringEncryptor;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
public class EncryptablePropertyResolver {

    private StringEncryptor encryptor;
    private String prefix;
    private String suffix;

    public EncryptablePropertyResolver(StringEncryptor encryptor, String prefix, String suffix) {
        this.encryptor = encryptor;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String resolvePropertyValue(String value) {
        return Optional.ofNullable(value)
                .filter(this::isEncrypted)
                .map(resolvedValue -> {
                    String unwrappedProperty = resolvedValue.trim().substring(prefix.length(), resolvedValue.length() - suffix.length());
                    return encryptor.decrypt(unwrappedProperty);

                })
                .orElse(value);
    }

    private boolean isEncrypted(String property){
        if(property == null){
            return false;
        }
        final String trimValue= property.trim();
        return (trimValue.startsWith(prefix) &&
                trimValue.endsWith(suffix));
    }
}
