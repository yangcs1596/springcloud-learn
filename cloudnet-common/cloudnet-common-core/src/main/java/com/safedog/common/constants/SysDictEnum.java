package com.safedog.common.constants;

/**
 * @author ycs
 * @description
 * @date 2021/10/16 14:43
 */
public enum SysDictEnum {
    DEFAUlT("default", "默认类型，不会返回任意字典，匹配的code的第一个");
    private String code;
    private String description;
    SysDictEnum(String code, String description){
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }}

