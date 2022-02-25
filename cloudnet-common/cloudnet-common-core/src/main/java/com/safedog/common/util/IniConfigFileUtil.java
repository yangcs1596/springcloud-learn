package com.safedog.common.util;

import org.ini4j.Config;
import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * @author ycs
 * @description
 * @date 2022/2/23 16:43
 */
public final class IniConfigFileUtil {


    /**
     * 更新ini文件内容
     *
     * 例子:
     * [StateRule]
     * ProcDeriveMonit=0
     * ProcPromoteMonit=0
     * ProcStartingMonit=1
     * RevshellStrictMode=0
     *
     * @param in
     * @param key
     * @param value
     * @return
     * @throws IOException
     */
    public static String updateFile(InputStream in, String key, String value) throws IOException {
        //重要：关闭转义处理，否则\会丢失
        Config.getGlobal().setEscape(false);
        Ini ini = new Ini(in);
        Profile.Section stateRule = ini.get("StateRule");
        if(stateRule.containsKey(key)){
            stateRule.replace(key, value);
        }else {
            stateRule.add(key, value);
        }

        //返回内容
        StringWriter writer = new StringWriter();
        ini.store(writer);
        return writer.toString().replaceAll(" = ", "=");
    }
}
