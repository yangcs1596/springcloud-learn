package com.safedog.common.ipsearch.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * IP地理位置信息
 * @author ymz
 * @date 2020/04/22 11:47
 */
@Setter
@Getter
public class IPInfoDataVO implements Serializable {
    public IPInfoDataVO(){

    }
    public IPInfoDataVO(String country, String province, String city, String operators) {
        this.country = country;
        this.province = province;
        this.city = city;
        this.operators = operators;
    }

    /**
     * 国家
     */
    private String country;
    /**
     * 省/特区
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 运营商(ip_info_new表里是area但是实际上存的是运营商，比如方正宽带/电信)
     */
    private String operators;

    @Override
    public String toString() {
        return new StringJoiner(", ", IPInfoDataVO.class.getSimpleName() + "[", "]")
                .add("country='" + country + "'")
                .add("province='" + province + "'")
                .add("city='" + city + "'")
                .add("operators='" + operators + "'")
                .toString();
    }
}
