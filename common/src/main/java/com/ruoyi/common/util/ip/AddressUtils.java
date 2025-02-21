package com.ruoyi.common.util.ip;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.ruoyi.common.config.GlobalInstance;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * 获取地址类
 *
 * @author ruoyi
 */
@Slf4j
public class AddressUtils {

    // IP地址查询
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    // 未知地址
    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip) {
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (RuoYiConfig.isAddressEnabled()) {
            try {
                String rspStr = HttpUtil.get(IP_URL + "?ip=" + ip + "&json=true", Constants.GBK);
                if (StrUtil.isEmpty(rspStr)) {
                    log.error("获取地理位置异常 {}", ip);
                    return UNKNOWN;
                }
                val jsonNode = GlobalInstance.fromJson(rspStr, JsonNode.class);
                String region = jsonNode.get("pro").asText();
                String city = jsonNode.get("city").asText();
                return String.format("%s %s", region, city);
            } catch (Exception e) {
                log.error("获取地理位置异常 {}", ip);
            }
        }
        return UNKNOWN;
    }
}
