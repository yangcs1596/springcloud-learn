package com.safedog.common.ldap;

import lombok.extern.slf4j.Slf4j;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;

@Slf4j
public class LdapSSLSocketFactory extends SSLSocketFactory {

    private SSLSocketFactory sslSocketFactory;

    public LdapSSLSocketFactory() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{new LdapTrustManager()}, new SecureRandom());
            this.sslSocketFactory = ctx.getSocketFactory();
        } catch (Exception e) {
            log.info("init LDAP ssl socket factory error.", e);
        }
    }

    public static synchronized SocketFactory getDefault() {
        return new LdapSSLSocketFactory();
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return sslSocketFactory.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return sslSocketFactory.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(Socket socket, String s, int i, boolean b) throws IOException {
        return sslSocketFactory.createSocket(socket, s, i, b);
    }

    @Override
    public Socket createSocket(String s, int i) throws IOException, UnknownHostException {
        return sslSocketFactory.createSocket(s, i);
    }

    @Override
    public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) throws IOException, UnknownHostException {
        return sslSocketFactory.createSocket(s, i, inetAddress, i1);
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        return sslSocketFactory.createSocket(inetAddress, i);
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) throws IOException {
        return sslSocketFactory.createSocket(inetAddress, i, inetAddress1, i1);
    }
}
