package com.ruoyi.common.manager;

import cn.hutool.extra.spring.SpringUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.entity.SysLogininfor;
import com.ruoyi.common.entity.SysOperLog;
import com.ruoyi.common.service.ISysLogininforService;
import com.ruoyi.common.service.ISysOperLogService;
import com.ruoyi.common.util.ServletUtils;
import com.ruoyi.common.util.StringUtils;
import com.ruoyi.common.util.ip.AddressUtils;
import com.ruoyi.common.util.ip.IpUtils;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;

/**
 * 异步工厂（产生任务用）
 *
 * @author ruoyi
 */
@Slf4j
public class AsyncFactory {

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @return 任务task
     */
    public static Runnable recordLogininfor(final String username, final String status, final String message) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr();
        return () -> {
            String address = AddressUtils.getRealAddressByIP(ip);
            String s = "[" + ip + "]" +
                    address +
                    "[" + username + "]" +
                    "[" + status + "]" +
                    "[" + message + "]";
            // 打印信息到日志
            log.info(s);
            // 获取客户端操作系统
            String os = userAgent.getOperatingSystem().getName();
            // 获取客户端浏览器
            String browser = userAgent.getBrowser().getName();
            // 封装对象
            SysLogininfor logininfor = new SysLogininfor();
            logininfor.setUserName(username);
            logininfor.setIpaddr(ip);
            logininfor.setLoginLocation(address);
            logininfor.setBrowser(browser);
            logininfor.setOs(os);
            logininfor.setMsg(message);
            // 日志状态
            if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
                logininfor.setStatus(0);
            } else if (Constants.LOGIN_FAIL.equals(status)) {
                logininfor.setStatus(1);
            }
            // 插入数据
            SpringUtil.getBean(ISysLogininforService.class).save(logininfor);
        };
    }

    /**
     * 操作日志记录
     *
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static Runnable recordOper(final SysOperLog operLog) {
        return () -> {
            // 远程查询操作地点
            operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
            SpringUtil.getBean(ISysOperLogService.class).save(operLog);
        };
    }

}
