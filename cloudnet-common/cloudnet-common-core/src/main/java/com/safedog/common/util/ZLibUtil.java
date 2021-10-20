package com.safedog.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * 与客户端下发数据，以及参数回传时使用的格式。
 * 主要使用encode和decode两个方法。
 */
@Slf4j
public class ZLibUtil {

    public static String encode(String str) {
        return Base64.getEncoder().encodeToString(compress(str));
    }

    public static String decode(String str) {
        byte[] data = Base64.getDecoder().decode(str);
        byte[] output = ZLibUtil.decompress(data);
        String outputStr = new String(output);
        return outputStr;
    }

    public static String compress2String(String data) {
        return new String(compress(data.getBytes()));
    }

    public static byte[] compress(String data) {
        return compress(data.getBytes());
    }

    /**
     * 压缩字节数组
     *
     * @param data
     * @return
     */
    public static byte[] compress(byte[] data) {
        byte[] output;

        Deflater compresser = new Deflater();

        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            log.error(e.getMessage(), e);
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        compresser.end();
        return output;
    }

    /**
     * 压缩 字节数组到输出流
     *
     * @param data
     * @param os
     */
    public static void compress(byte[] data, OutputStream os) {
        DeflaterOutputStream dos = new DeflaterOutputStream(os);
        try {
            dos.write(data, 0, data.length);
            dos.finish();
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 解压缩 字节数组
     *
     * @param data
     * @return
     */
    public static byte[] decompress(byte[] data) {
        byte[] output;
        Inflater decompresser = new Inflater();
        decompresser.reset();
        decompresser.setInput(data);
        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            output = data;
            log.error(e.getMessage(), e);
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        decompresser.end();
        return output;
    }

    /**
     * 解压缩 输入流 到字节数组
     * @param is
     * @return
     */
    public static byte[] decompress(InputStream is) {
        InflaterInputStream iis = new InflaterInputStream(is);
        ByteArrayOutputStream o = new ByteArrayOutputStream(1024);
        try {
            int i = 1024;
            byte[] buf = new byte[i];
            while ((i = iis.read(buf, 0, i)) > 0) {
                o.write(buf, 0, i);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return o.toByteArray();
    }
}
