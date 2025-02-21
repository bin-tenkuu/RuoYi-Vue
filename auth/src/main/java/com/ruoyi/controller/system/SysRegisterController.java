package com.ruoyi.controller.system;

import com.ruoyi.common.model.R;
import com.ruoyi.common.model.login.RegisterBody;
import com.ruoyi.common.service.ISysConfigService;
import com.ruoyi.common.service.SysRegisterService;
import com.ruoyi.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册验证
 *
 * @author ruoyi
 */
@RestController
@RequiredArgsConstructor
public class SysRegisterController {
    private final SysRegisterService registerService;
    private final ISysConfigService configService;

    @PostMapping("/register")
    public R<String> register(@RequestBody RegisterBody user) {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            return R.fail("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        if (StringUtils.isEmpty(msg)) {
            return R.ok();
        } else {
            return R.fail(msg);
        }
    }
}
