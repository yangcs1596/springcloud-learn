package com.safedog.common.ipsearch.adapter;

import com.safedog.common.ipsearch.model.DataBlock;
import com.safedog.common.ipsearch.model.IPInfoDataVO;
import com.safedog.common.ipsearch.search.Reader;
import com.safedog.common.ipsearch.search.SearchProvider;
import com.safedog.common.ipsearch.utils.ByteUtil;
import com.safedog.common.ipsearch.utils.GZipUtils;

import java.io.File;
import java.util.Optional;

import static com.safedog.common.ipsearch.model.IPSearchConstant.FILE_MODE;
import static com.safedog.common.ipsearch.model.IPSearchConstant.SEARCH_DB;

public class IpSearchAdapter implements SearchProvider {
    private final Reader reader;

    public IpSearchAdapter() {
        this.reader = new Reader(new File(ByteUtil.getPath(SEARCH_DB + GZipUtils.EXT)), FILE_MODE);
    }

    @Override
    public IPInfoDataVO search(String ip) {
        return this.search(ByteUtil.ipToLong(ip));
    }

    @Override
    public IPInfoDataVO search(long ip) {
        DataBlock dataBlock = this.reader.search(ip);
        return Optional.ofNullable(dataBlock).map(DataBlock::getIPInfoDataVO).orElse(null);
    }
}
