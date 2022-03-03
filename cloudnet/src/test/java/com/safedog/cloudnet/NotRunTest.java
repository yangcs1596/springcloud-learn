package com.safedog.cloudnet;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.safedog.cloudnet.entity.mysql.SysUser;
import com.safedog.cloudnet.model.TestModel;
import com.safedog.common.util.CornUtil;
import com.safedog.common.util.VersionUtil;
import com.safedog.common.util.ZLibUtil;
import com.safedog.common.util.column.MyColumnUtil;
import com.safedog.common.util.date.CompletionDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * @author ycs
 * @description
 * @date 2021/9/23 10:01
 */
@SpringBootTest
@Slf4j
public class NotRunTest {

    @Test
    public void test_foreach() {
        System.out.println(System.getProperty("user.dir"));
        HashMap<String, String> map = new HashMap<>();
        map.put("A", "G");
        map.put("B", "F");
        map.put("V", "T");
        map.forEach((a, b) -> {
            System.out.printf("key:%s==========value:%s", a, b);
            System.out.println();
        });
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>(5);
        System.out.println(objectObjectHashMap.size() == 0);
    }

    @Test
    public void xx() throws Exception {
        String path = ResourceUtils.getURL("classpath:").getPath();
        System.out.println(path);
        System.out.println(ZLibUtil.encode("12354989"));
        System.out.println(ZLibUtil.decode(ZLibUtil.encode("12354989")));
        System.out.println(ZLibUtil.decode("eJw1jcEKwjAQRP9lzsGgaIVeFc+C3koPyyaRaNjIphGl9N8NiKfhwZuZYQbpraCfwVkmiuI1usYDdiHs/bbbrIPrMBpMn6dHD0oJBuScNqgS37219kVqtYp1mR9eV6UFFoNQhZt1zHzVfCe5MMkp6+H/1IY4USk/pzXPmhnL+AWUWjJn"));
    }

    @Test
    public void testJsonIgnore() {
        TestModel testModel = new TestModel();
        testModel.setAge(15);
        testModel.setName("张三");
        System.out.println(JSONObject.toJSONString(testModel));
        String str ="a\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "b\n" +
                "\n" +
                "\n";
        System.out.println(str.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1").replaceAll("^((\r\n)|\n)", ""));
    }

    /**
     * 将config文件输出成config文件
     */
    @Test
    public void testYaml() throws Exception {
        File file = new File("C:\\Users\\ASUS\\Desktop\\config");
        InputStream inputStream = new FileInputStream(file);
        String read = IoUtil.read(inputStream, Charsets.UTF_8);
        Yaml yaml = new Yaml();
        String yamlStr = yaml.dump(read);
        //读取 JSON 字符串
        //String jsonModels = StringEscapeUtils.unescapeHtml(models);
//        JsonNode jsonNodeTree = new ObjectMapper().readTree(read);
//        -----------------------------------------------
//        JsonNode jsonNode = new YAMLMapper().readTree(file);
//        //转换成 YAML 字符串
//        String yamlStr = new YAMLMapper().writeValueAsString(jsonNode);
        inputStream.close();

        System.out.println(read);
    }

    @Test
    public void test_cronUtil() {
        LocalDateTime localDateTime = CornUtil.calculatNextTime("52 50 13 */2 * ?");

        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator("52 50 13 */2 * ?");
        Date next = cronSequenceGenerator.next(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        System.out.println(next);
    }


    /**
     * 理解为二维数组
     * table为两个key，确定一个value
     */
    @Test
    public void testTable() {
        Table<String, String, Object> basedTable = HashBasedTable.create();
        basedTable.put("A", "A", "A");
        basedTable.put("A", "A", "B");
        basedTable.put("A", "B", "B");
        basedTable.put("B", "B", "B");
        for (Table.Cell<String, String, Object> stringStringObjectCell : basedTable.cellSet()) {

        }
        System.out.println(basedTable);

    }

    @Test
    public void pathTest() {
        String classPath = NotRunTest.class.getClassLoader().getResource(File.separator).getPath();
        System.out.println(classPath);
        System.out.println(StringUtils.substringBeforeLast(classPath, "/target"));
        System.out.println(System.getProperty("user.dir"));

        String path = Thread.currentThread().getContextClassLoader().getResource("//").getPath();
        System.out.println(path);
    }

    @Test
    public void testDate() {
        LocalDateTime today = LocalDateTime.now(ZoneId.of("+08:00"));
        System.out.println(TimeZone.getDefault().toZoneId());
        today = LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), 0, 0);
        LocalDateTime beforeDay = today.plusDays(-29);
        List<String> dayList = CompletionDateUtils.completionDate(beforeDay, today);
        System.out.println(dayList);
//        System.out.println(SerializedLambda.resolve().getImplMethodName());

        System.out.println(MyColumnUtil.getFieldName(SysUser::getName).toUpperCase());

        System.out.println(LocalDateTime.now().minusMinutes(5L));
    }

    @Test
    public void test_ReadJson() {
//        ClassPathResource fontResource = new ClassPathResource("Eviction.json");
//        try(InputStream inputStream = fontResource.getInputStream()) {
//            byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
//            String str = new String(bytes, Charsets.UTF_8);
//        } catch (IOException e) {
//
//        }
        /**
         * 输出字符的base64
         */
        System.out.println(Base64.encodeBase64String("admin:1qaz@WSX".getBytes()));
        System.out.println(Base64.encodeBase64String("root:root".getBytes()));

        /***
         * 写入ini文件
         */
//        try(FileInputStream in = new FileInputStream("C:\\Users\\ASUS\\Desktop\\aaa")) {
//            IniConfigFileUtil.updateFile(in, "FileMonit", "1");
//        } catch (Exception e) {
//            log.info("");
//        }

        System.out.println(VersionUtil.compareVersion("1.2.31", "1.2.35"));
        System.out.println(("a" + "b").hashCode());
        System.out.println(LocalDateTime.now().minusMinutes(5));

    }

}

