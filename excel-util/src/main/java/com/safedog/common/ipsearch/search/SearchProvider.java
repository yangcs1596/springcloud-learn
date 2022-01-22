package com.safedog.common.ipsearch.search;

import com.safedog.common.ipsearch.model.IPInfoDataVO;

public interface SearchProvider {
    IPInfoDataVO search(String ip);

    IPInfoDataVO search(long ip);
}
