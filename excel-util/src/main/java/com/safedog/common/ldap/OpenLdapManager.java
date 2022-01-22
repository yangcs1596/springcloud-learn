package com.safedog.common.ldap;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringJoiner;


@Slf4j
public class OpenLdapManager {

    private static final String CN_START = "cn=";
    private static final String DC_START = "dc=";
    private static final String UID_START = "uid=";
    private static final String OU_START = "ou=";
    private static final String LDAPS_TEMPLATE = "ldaps://%s:%s/";
    private final Hashtable<String, String> env;

    /**
     * 需要自行设置 配置 Context.PROVIDER_URL(ldap://127.0.0.1/) 和 Context.SECURITY_PRINCIPAL(ou=RD3,dc=safedog,dc=cn)
     *
     * @param env
     */
    public OpenLdapManager(Map<String, String> env) {
        if (StringUtils.isBlank(env.get(Context.PROVIDER_URL))) {
            throw new IllegalArgumentException("no attribute java.naming.provider.url properties");
        }
        this.env = new Hashtable<>(env);
        if (StringUtils.isBlank(this.env.get(Context.SECURITY_AUTHENTICATION))) {
            this.env.put(Context.SECURITY_AUTHENTICATION, "simple");
        }

        if (StringUtils.isBlank(env.get(Context.INITIAL_CONTEXT_FACTORY))) {
            this.env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        }
    }

    /**
     * SSL或TLS使用此方法初始化，需要提供host地址或port地址，port地址为空则会默认设置为636
     *
     * @param env
     * @param host     LDAP服务器地址
     * @param port     端口
     * @param protocol 协议 ssl或tls
     */
    public OpenLdapManager(Map<String, String> env, String host, String port, String protocol) {
        if (StringUtils.isBlank(protocol)) {
            throw new IllegalArgumentException("please set protocol");
        }
        if (StringUtils.isBlank(host)) {
            throw new IllegalArgumentException("please set host");
        }
        this.env = new Hashtable<>(env);
        if (StringUtils.isBlank(this.env.get(Context.SECURITY_AUTHENTICATION))) {
            this.env.put(Context.SECURITY_AUTHENTICATION, "simple");
        }
        String tmpPort = port;
        if (StringUtils.isBlank(port)) {
            tmpPort = "636";
        }
        if (StringUtils.isBlank(env.get(Context.INITIAL_CONTEXT_FACTORY))) {
            this.env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        }
        String url = String.format(LDAPS_TEMPLATE, host, tmpPort);
        this.env.put(Context.PROVIDER_URL, url);
        this.env.put(Context.SECURITY_PROTOCOL, protocol);
        this.env.put("java.naming.ldap.factory.socket", LdapSSLSocketFactory.class.getName());
    }

    /**
     * 对用户的UID及密码进行验证,如果提供的 信息中有CN，则会进行对比验证
     *
     * @param uid
     * @param password
     * @return
     */
    public boolean authenticate(String uid, String password) {
        String principalStr = this.env.get(Context.SECURITY_PRINCIPAL);

        String dcStr = "";
        if (StringUtils.isNotEmpty(principalStr)) {
            String[] priArr = principalStr.split(",");
            StringBuilder newPrincipalStr = null;
            if (uid.indexOf("=") > 0) {
                newPrincipalStr = new StringBuilder(uid);
            } else {
                newPrincipalStr = new StringBuilder(UID_START).append(uid);
            }

            StringJoiner dcJoiner = new StringJoiner(",");
            for (String str : priArr) {
                if (str.trim().startsWith(DC_START)) {
                    dcJoiner.add(str);
                }
                newPrincipalStr.append(',').append(str);
            }
            dcStr = dcJoiner.toString();
            principalStr = newPrincipalStr.toString();
            env.put(Context.SECURITY_PRINCIPAL, principalStr);
        } else {
            env.put(Context.SECURITY_PRINCIPAL, uid);
        }
        env.put(Context.SECURITY_CREDENTIALS, password);
        DirContext ctx = null;
        try {
            ctx = new InitialLdapContext(env, null);

            if (StringUtils.isNotBlank(principalStr)) {
                SearchControls searchControls = new SearchControls();
                searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                searchControls.setReturningAttributes(new String[]{"objectGUID", "name", "uid"});
                NamingEnumeration<SearchResult> result = ctx.search(dcStr, "objectClass=account", searchControls);

                if (result == null) {
                    return false;
                } else {
                    if (result.hasMoreElements()) {
                        return true;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            log.error("validate ldap account error. {}", e.getMessage());
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException ne) {
                    log.error("", ne);
                }
            }
        }
        return false;
    }

    /**
     * 增加验证CN用于信息验证
     *
     * @param cn
     * @param uid
     * @param password
     * @return
     */
    public boolean authenticate(String cn, String uid, String password) {
        String principalStr = this.env.get(Context.SECURITY_PRINCIPAL);
        if (StringUtils.isBlank(principalStr)) {
            throw new IllegalArgumentException("no attr java.naming.security.principal");
        }
        String newPrincipalStr = new StringBuilder(CN_START).append(cn).append(',').append(principalStr).toString();
        this.env.put(Context.SECURITY_PRINCIPAL, newPrincipalStr);
        return this.authenticate(uid, password);
    }

}
