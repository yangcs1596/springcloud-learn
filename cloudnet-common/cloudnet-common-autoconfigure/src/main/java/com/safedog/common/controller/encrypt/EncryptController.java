package com.safedog.common.controller.encrypt;

import com.safedog.common.autoconfigure.property.EncryptablePropertyResolver;
import com.safedog.common.util.encryptor.StringEncryptor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ycs
 * @description
 * @date 2021/10/19 9:12
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/encrypt")
@Api(tags = {"tools-明文加密"},description = "加密工具")
public class EncryptController {

    private final EncryptablePropertyResolver encryptablePropertyResolver;

    @ApiOperation(value="加密明文")
    @GetMapping("/getEncrypt")
    public String getEncrypt(String plaintext){
        String ciphertext = "";
        try {
            StringEncryptor encryptor = encryptablePropertyResolver.getEncryptor();
            if(null != encryptor){
                ciphertext = encryptor.encrypt(plaintext);
            }
        } catch (Exception e) {
            log.error("加密明文失败=============={}", e );
        }
        return ciphertext;
    }

}
