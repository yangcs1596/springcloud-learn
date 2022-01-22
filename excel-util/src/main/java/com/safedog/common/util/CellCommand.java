package com.safedog.common.util;

import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.util.SafeEncoder;

/**
 * Redis-Cell 模块指令 提供漏洞算法，原子的限流指令
 * @author   hcl on 2017/11/16.
 */
public enum CellCommand implements ProtocolCommand {
    /**限流 */
    CLTHROTTLE("CL.THROTTLE");

    private final byte[] raw;

    CellCommand(String alt) {
        this.raw = SafeEncoder.encode(alt);
    }

    @Override
    public byte[] getRaw() {
        return raw;
    }
}
