package com.safedog.common.ipsearch.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * data block class
 *
 * @author ymz
 */
@Getter
@Setter
public class DataBlock implements Serializable {
    /**
     * region address
     */
    private String region;

    /**
     * region filePointer in the db file
     */
    private long dataPtr;

    /**
     * country 设置的长度
     */
    private Integer COUNTRY_LENGTH = 1;

    /**
     * province 设置的长度
     */
    private Integer PROVINCE_LENGTH = 2;

    /**
     * city 设置的长度
     */
    private Integer CITY_LENGTH = 3;

    /**
     * operators 设置的长度
     */
    private Integer OPERATORS_LENGTH = 4;

    public DataBlock() {
    }

    public DataBlock(String region, long dataPtr) {
        this.region = region;
        this.dataPtr = dataPtr;
    }

    public DataBlock(String region) {
        this(region, 0);
    }

    public DataBlock(IPInfoDataVO data) {
        Objects.requireNonNull(data);
        this.region = String.join("|", data.getCountry(), data.getProvince(), data.getCity(), data.getOperators());
        this.dataPtr = 0;
    }

    @Override
    public String toString() {
        return region;
    }

    public IPInfoDataVO getIPInfoDataVO() {
        String region = this.getRegion();
        String[] split = region.split("\\|");
        IPInfoDataVO ipInfoDataVO = new IPInfoDataVO();
        if (split.length >= COUNTRY_LENGTH) {
            ipInfoDataVO.setCountry("0".equals(split[0]) ? "" : split[0]);
        }
        if (split.length >= PROVINCE_LENGTH) {
            ipInfoDataVO.setProvince("0".equals(split[1]) ? "" : split[1]);
        }
        if (split.length >= CITY_LENGTH) {
            ipInfoDataVO.setCity("0".equals(split[2]) ? "" : split[2]);
        }
        if (split.length >= OPERATORS_LENGTH) {
            ipInfoDataVO.setOperators("0".equals(split[3]) ? "" : split[3]);
        }
        return ipInfoDataVO;
    }

}
