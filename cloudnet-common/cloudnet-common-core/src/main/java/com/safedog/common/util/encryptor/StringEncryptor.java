package com.safedog.common.util.encryptor;

public interface StringEncryptor {

    String decrypt(final String encryptMessage);

    String encrypt(final String message);
}
