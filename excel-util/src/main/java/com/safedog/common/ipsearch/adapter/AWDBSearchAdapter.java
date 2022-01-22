package com.safedog.common.ipsearch.adapter;

import com.safedog.common.ipsearch.model.IPInfoDataVO;
import com.safedog.common.ipsearch.search.SearchProvider;
import com.safedog.common.ipsearch.utils.ByteUtil;
import com.safedog.geoip.DatabaseReader;
import com.safedog.geoip.exception.GeoIpException;
import com.safedog.geoip.model.IpPlusCity;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Optional;

public class AWDBSearchAdapter implements SearchProvider {
    private final DatabaseReader reader;

    public AWDBSearchAdapter(File dbFile) throws IOException {
        this.reader = new DatabaseReader.Builder(dbFile).build();
    }

    @Override
    public IPInfoDataVO search(String ip) {
        try {
            InetAddress addr = InetAddress.getByName(ip);
            Optional<IpPlusCity> result = this.reader.query(addr);
            return result.map(this::convert).orElse(null);
        } catch (IOException| GeoIpException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IPInfoDataVO search(long ip) {
        return this.search(ByteUtil.longToIp(ip));
    }

    private IPInfoDataVO convert(IpPlusCity info) {
        return new IPInfoDataVO(info.getCountry(), info.getProvince(), info.getCity(), info.getOwner());
    }
}
