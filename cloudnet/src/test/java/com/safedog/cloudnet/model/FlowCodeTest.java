package com.safedog.cloudnet.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author ycs
 * @description
 * @date 2022/2/18 16:05
 */
@SpringBootTest
@Slf4j
public class FlowCodeTest {
    @Test
    public void test_ReadJson() {

        FastAutoGeneratorStudent
                .create("张三")
                .schoolModel(builder -> {
                    builder.schoolName("哈佛大学")
                            .schoolName("hf0001");
                })
                .classModel(builder -> {
                    builder.className("大一 14班");
                })
                .teacherModel(builder -> {
                    builder.teacherName("米开朗基德");
                })
                .excute();

    }

}
