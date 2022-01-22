package com.safedog.common.ipsearch.search;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum IPSearchDBType {
    /**
     * 内置IP库
     */
    SDDB(0),
    /**
     * 埃文AWDB库
     */
    AWDB(1);
    private final int code;

    public int getCode() {
        return code;
    }

    IPSearchDBType(int code) {
        this.code = code;
    }

    public static Optional<IPSearchDBType> fromCode(final int code) {
        return Arrays.stream(IPSearchDBType.values())
                .filter(item -> Objects.equals(item.getCode(), code))
                .findFirst();
    }

}
