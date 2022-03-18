package com.safedog.common.util.sftp;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;

/**
 * @author ycs
 * @description 执行远程linux命令，使用了ssh连接
 * @date 2021/10/13 11:12
 */
@Slf4j
public class SftpUtil {

    public static final String PROJECT = System.getProperty("user.dir");

    private ChannelSftp sftp;
    private ChannelExec exec;
    private Session session;

    private static ThreadLocal<SftpUtil> sftpUtilLocal = new ThreadLocal<SftpUtil>();


    /**
     * 构造方法初始化
     */
    private SftpUtil(String sftp_txb_username, String sftp_txb_host, String sftp_txb_port, String sftp_txb_password) {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(sftp_txb_username, sftp_txb_host, Integer.parseInt(sftp_txb_port));
            //密码是否需要加解密
            session.setPassword(sftp_txb_password);
            Properties config = new Properties();
            //修改服务器/etc/ssh/sshd_config中 GSSSAPIAuthentication的值yes为no，解决用户不能远程登录问题
            config.put("userauth.gssapi-with-mic", "no");
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
        } catch (JSchException e) {
            log.info("============sftp连接失败============{}", e);
        }
    }

    /**
     * 连接sftp
     * @param sftp_txb_username
     * @param sftp_txb_host
     * @param sftp_txb_port
     * @param sftp_txb_password
     * @return
     */
    public static SftpUtil login(String sftp_txb_username, String sftp_txb_host, String sftp_txb_port, String sftp_txb_password){
        SftpUtil sftpUtil = sftpUtilLocal.get();
        if(null == sftpUtil || !sftpUtil.isConnected()){
            sftpUtilLocal.set(new SftpUtil(sftp_txb_username, sftp_txb_host, sftp_txb_port, sftp_txb_password));
        }
        return sftpUtilLocal.get();
    }

    /**
     * 判断是否已连接
     * @return
     */
    private boolean isConnected(){
        return session != null && session.isConnected();
    }

    /**
     * 连接sftp
     * @return
     */
    public SftpUtil connectSftp() throws JSchException {
        //获取sftp通道
        sftp = (ChannelSftp)session.openChannel("sftp");
        sftp.connect();
        log.info("============sftp连接成功============");
        return this;
    }
    /**
     * 关闭连接最好在执行的finally
     * 关闭连接 server
     */
    public void logout() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if(exec != null){
            if(exec.isConnected()){
                exec.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
            sftpUtilLocal.remove();
        }
    }

    /**
     * 执行命令行
     * @param cmd
     * @return
     */
    public boolean execCommand(String cmd){
        try {
            if(StringUtils.hasLength(cmd)){
                log.info("执行命令====================={}", cmd);
                //非法执行命令行 删除操作
                if(cmd.contains("rm -rf")){
                    return false;
                }
                //命令行模式
                exec = (ChannelExec)session.openChannel("exec");
                exec.setCommand(cmd);
                exec.setInputStream(null);
                exec.setErrStream(System.err);
                //连接并且已执行cmd
                exec.connect();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getErrStream()));
                String line = "";
                StringBuffer stringBuffer = new StringBuffer();
                while((line = bufferedReader.readLine()) != null){
                    stringBuffer.append(line);
                }
                bufferedReader.close();
                //关闭连接
                exec.disconnect();
                log.info("运行命令结果=================={}", stringBuffer.toString());
                if(stringBuffer.toString().contains("fail") || stringBuffer.toString().contains("error")){
                    return false;
                }
                return true;
            }
        } catch (Exception e) {
            log.info("==========执行失败=======" + e.getMessage());
        }
        return false;
    }

    /**
     * 将本地文件上传到sftp指定目录
     * 将输入流的数据上传到sftp作为文件
     *
     * @param sftpFileName sftp端文件名
     * @param sftp_up_path sftp目录
     */
    public boolean upload(String sftpFileName, String sftp_up_path, InputStream inputStream) {
        boolean flag = false;
        try {
            if (isDirExist(sftp_up_path)) {
                sftp.cd(sftp_up_path);
            } else {
                //目录不存在，则创建文件夹
                String[] dirs = sftp_up_path.split("/");
                String tempPath = "";
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) {
                        continue;
                    }
                    tempPath += "/" + dir;
                    if (isDirExist(tempPath)) {
                        sftp.cd(tempPath);
                    } else {
                        sftp.mkdir(tempPath);
                        sftp.cd(tempPath);
                    }
                }
            }
            sftp.put(inputStream, sftpFileName);  //上传文件
            inputStream.close();
            flag = true;
            log.info("sftp文件上传成功：【{}】", sftpFileName);

        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
            log.info("sftp文件上传失败：【{}】", sftpFileName);
        }
        return flag;
    }


    /**
     * 将指定目录下的sftp文件 下载到本地
     *
     * @param downloadFile   下载的文件
     * @param sftp_down_path sftp目录
     * @param saveFile       存在本地的路径
     */
    public boolean download(String downloadFile, String sftp_down_path, String saveFile) {
        boolean flag = false;
        try {
            if (isDirExist(sftp_down_path)) {
                sftp.cd(sftp_down_path);
                File file = new File(saveFile);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                sftp.get(downloadFile, fileOutputStream);
                fileOutputStream.close();
                flag = true;
                log.info("sftp文件下载成功：【{}】", downloadFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
            log.info("sftp文件下载失败：【{}】", downloadFile);
        }

        return flag;
    }


    /**
     * 验证文件夹
     *
     * @param remotePath
     * @return
     */
    public boolean isDirExist(String remotePath) {
        boolean flag = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(remotePath);
            flag = sftpATTRS.isDir();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            flag = false;
        }
        return flag;
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     */
    public void delete(String directory, String deleteFile) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
    }

    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     */
    public List<String> listFiles(String directory) {
        List<String> list = new ArrayList<>();
        Vector<ChannelSftp.LsEntry> vector = new Vector<>();
        try {
            vector = sftp.ls(directory);
            for (ChannelSftp.LsEntry file : vector) {
                String fileName = file.getFilename();
                list.add(fileName);
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return list;
    }


    /**
     * 创建目录
     * @param directory
     * @return
     */
    public boolean mkFile(String directory){
        if(StringUtils.hasLength(directory)){
            try {
                sftp = (ChannelSftp)session.openChannel("sftp");
                sftp.connect();
                log.info("SftpUtil sftp isConnected {}", sftp.isConnected());
                //TODO 需要判断路径是否是目录的路径，防止文件路径
                //1、查看目录是否存在
                Vector ls;
                try {
                    ls = sftp.ls(directory);
                } catch (SftpException e) {
                    ls = null;
                }
                if(ls == null){
                    //目录不存在，新建目录
                    sftp.mkdir(directory);
                }
                //2、执行切换cd, 进入目录
                sftp.cd(directory);
            } catch (Exception e) {
                log.info("file error {}", e.getMessage());
            } finally {
                sftp.disconnect();
            }
        }
        return false;
    }

    //上传文件测试
    public static void main(String[] args) throws Exception {
        SftpUtil sftpUtil = SftpUtil.login("root", "192.168.89.246", "22", "safedog@shx19");
        //执行过程
        //export RSYNC_PASSWORD="n:P57N.,\"\`4bYSHMO-#I*2cXai|:q@X^"  && rsync -rlptD -vih -a  --port 22873
        // --delete mybk@192.168.88.45::linshi/ubuntu /home/data/patch_library/centos;
//        sftpUtil.execCommand("export RSYNC_PASSWORD=\"1\"  && rsync -rlptD -vih -a  --port 1  --delete 1@1::1");
        sftpUtil.execCommand("export RSYNC_PASSWORD=\"n:P57N.,\\\"\\`4bYSHMO-#I*2cXai|:q@X^\"  && rsync -rlptD -vih -a  --port 22873  --delete mybk@192.168.88.45::cpatch/win /home/data/win;");
        sftpUtil.mkFile("/home/data/win");
        //退出
        sftpUtil.logout();

    }

}

