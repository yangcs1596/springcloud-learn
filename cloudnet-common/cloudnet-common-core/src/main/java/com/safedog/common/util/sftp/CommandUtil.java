package com.safedog.common.util.sftp;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author ycs
 * @description 直接掉linux执行命令，不用ssh连接
 * exec方法通过创建一个子进程执行Linux命令，因此频繁地通过Java调用Linux命令对性能影响非常大，不要在程序中过度使用这种方式。
 * @date 2022/3/11 16:55
 */
@Slf4j
public class CommandUtil {
    public static String run(String command){
        Scanner input = null;
        String result = "";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            //等待命令执行完成
//            process.waitFor(10, TimeUnit.SECONDS);
            InputStream is = process.getErrorStream();
            input = new Scanner(is);
            while (input.hasNextLine()) {
                result += input.nextLine() + "\n";
            }

//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
//            String readLine = null;
//            while ((readLine = bufferedReader.readLine()) != null) {
//                result += readLine + "\n";
//            }
            result = command + "\n" + result; //加上命令本身，打印出来
            log.info("exec command  result {}", result);
        } catch (Exception e) {
            log.info("exec command error {}", e);
        } finally {
            if (input != null) {
                input.close();
            }
            if (process != null) {
                process.destroy();
            }
        }
        return result;
    }

    public static String run(String[] command) throws IOException {
        return run(command.toString());
    }

    /**
     * 判断进程是否存在
     * @param processName
     * @return
     */
    public static boolean findProcess(String processName) {
        BufferedReader bufferedReader = null;
        try {
            //linux ps -ef |grep agent|grep -v grep
            Process proc = Runtime.getRuntime().exec("tasklist -fi " + '"' + "imagename eq " + processName +'"');
            bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(processName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception ex) {}
            }
        }
    }
}
