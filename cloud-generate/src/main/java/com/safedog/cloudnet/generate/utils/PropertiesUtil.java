package com.safedog.cloudnet.generate.utils;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesUtil {


    static Properties prop = new Properties();

    static {
        try {
            InputStream in = PropertiesUtil.class.getResourceAsStream("/application.properties");
            if (in != null) {
                prop.load(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        String value = prop.getProperty(key);
        return StringUtils.hasLength(value) ? value : "";
    }

}
