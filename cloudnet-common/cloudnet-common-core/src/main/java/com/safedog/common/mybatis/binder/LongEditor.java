package com.safedog.common.mybatis.binder;

import org.springframework.beans.propertyeditors.PropertiesEditor;

/**
 * org.springframework.beans.propertyeditors.PropertiesEditor
 * JavaBean PropertyEditor接口，将字符串属性值转换成正确的类型；
 */
public class LongEditor extends PropertiesEditor {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.equals("")) {
            text = "-1";
        }
        setValue(Long.parseLong(text));
    }

    @Override
    public String getAsText() {
        return getValue().toString();
    }
}
