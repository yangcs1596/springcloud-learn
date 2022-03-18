package com.safedog.common.constants;

/**
 * @author ycs
 * @description enum默认输出的是enum.name()方法
 * @date 2022/3/15 17:02
 */
public enum MoodEnum {
    Happy("开心"),
    Sad("难过"),
    Excite("惊吓");
    private String mood;

    MoodEnum(String mood) {
        this.mood = mood;
    }

    public String getMood() {
        return mood;
    }}
