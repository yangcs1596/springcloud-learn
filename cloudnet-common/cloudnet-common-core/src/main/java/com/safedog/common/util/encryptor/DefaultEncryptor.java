package com.safedog.common.util.encryptor;

public class DefaultEncryptor implements StringEncryptor{

    private String key;

    public DefaultEncryptor(String key) {
        this.key = key;
    }

    @Override
    public String decrypt(String encryptMessage) {
        return AESUtil.decrypt(encryptMessage, key);
    }

    @Override
    public String encrypt(String message) {
        return AESUtil.encrypt(message, key);
    }
}
