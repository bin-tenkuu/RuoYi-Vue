package com.ruoyi.common.util.uuid;

import lombok.val;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * ID生成器工具类
 *
 * @author ruoyi
 */
public class IdUtils {
    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID() {
        val uuid = UUID.randomUUID();
        return toString(uuid, false);
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID() {
        val uuid = UUID.randomUUID();
        return toString(uuid, true);
    }

    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 随机UUID
     */
    public static String fastUUID() {
        val random = ThreadLocalRandom.current();
        val uuid = new UUID(random.nextLong(), random.nextLong());
        return toString(uuid, false);
    }

    /**
     * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String fastSimpleUUID() {
        val random = ThreadLocalRandom.current();
        val uuid = new UUID(random.nextLong(), random.nextLong());
        return toString(uuid, true);
    }

    private static String toString(UUID uuid, boolean isSimple) {
        if (isSimple) {
            val mostSigBits = uuid.getMostSignificantBits();
            val leastSigBits = uuid.getLeastSignificantBits();
            // time_low
            return digits(mostSigBits >> 32, 8) +
                    // time_mid
                    digits(mostSigBits >> 16, 4) +
                    // time_high_and_version
                    digits(mostSigBits, 4) +
                    // variant_and_sequence
                    digits(leastSigBits >> 48, 4) +
                    // node
                    digits(leastSigBits, 12);
        }
        return uuid.toString();
    }

    /**
     * 返回指定数字对应的hex值
     *
     * @param val 值
     * @param digits 位
     * @return 值
     */
    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }
}
