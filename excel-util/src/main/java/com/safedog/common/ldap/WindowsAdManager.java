package com.safedog.common.ldap;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.util.Map;

@Slf4j
public class WindowsAdManager {

    private final Hashtable<String, String> env;


    public WindowsAdManager(Map<String, String> env) {
        if (StringUtils.isBlank(env.get(Context.PROVIDER_URL))) {
            throw new IllegalArgumentException("no attribute java.naming.provider.url properties");
        }
        if (StringUtils.isBlank(env.get(Context.SECURITY_AUTHENTICATION))) {
            throw new IllegalArgumentException("no attribute java.naming.security.authentication");
        }
        this.env = new Hashtable<>(env);
        if (StringUtils.isBlank(env.get(Context.INITIAL_CONTEXT_FACTORY))) {
            this.env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        }
    }

    /**
     * 对用户的UID及密码进行验证
     *
     * @param userName
     * @param password
     * @return
     */
    public boolean authenticate(String userName, String password) {
        env.put(Context.SECURITY_PRINCIPAL, userName);
        env.put(Context.SECURITY_CREDENTIALS, password);
        DirContext ctx = null;
        try {
            ctx = new InitialDirContext(env);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException ne) {
                    ne.printStackTrace();
                    log.error("", ne);
                }
            }
        }
        return false;
    }
}
