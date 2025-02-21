package com.ruoyi.common.util.uuid;

import com.ruoyi.common.util.DateUtil;
import com.ruoyi.common.util.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ruoyi 序列生成类
 */
public class Seq {
    // 通用序列类型
    public static final String COMM_SEQ_TYPE = "COMMON";

    // 上传序列类型
    public static final String UPLOAD_SEQ_TYPE = "UPLOAD";

    // 通用接口序列数
    private static final AtomicInteger COMM_SEQ = new AtomicInteger(1);

    // 上传接口序列数
    private static final AtomicInteger UPLOAD_SEQ = new AtomicInteger(1);

    // 机器标识
    private static final String MACHINE_CODE = "A";

    /**
     * 获取通用序列号
     *
     * @return 序列值
     */
    public static String getId() {
        return getId(COMM_SEQ_TYPE);
    }

    /**
     * 默认16位序列号 yyMMddHHmmss + 一位机器标识 + 3长度循环递增字符串
     *
     * @return 序列值
     */
    public static String getId(String type) {
        var atomicInt = UPLOAD_SEQ_TYPE.equals(type) ? UPLOAD_SEQ : COMM_SEQ;
        return getId(atomicInt, 3);
    }

    /**
     * 通用接口序列号 yyMMddHHmmss + 一位机器标识 + length长度循环递增字符串
     *
     * @param atomicInt 序列数
     * @param length    数值长度
     * @return 序列值
     */
    public static String getId(AtomicInteger atomicInt, int length) {
        return DateUtil.dateTimeNow() + MACHINE_CODE + getSeq(atomicInt, length);
    }

    /**
     * 序列循环递增字符串[1, 10 的 (length)幂次方), 用0左补齐length位数
     *
     * @return 序列值
     */
    private synchronized static String getSeq(AtomicInteger atomicInt, int length) {
        // 先取值再+1
        var value = atomicInt.getAndIncrement();

        // 如果更新后值>=10 的 (length)幂次方则重置为1
        var maxSeq = (int) Math.pow(10, length);
        if (atomicInt.get() >= maxSeq) {
            atomicInt.set(1);
        }

        // 转字符串，用0左补齐
        return StringUtils.padl(value, length);
    }
}
