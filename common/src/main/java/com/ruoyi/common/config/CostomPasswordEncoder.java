package com.ruoyi.common.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author bin
 * @version 1.0.0
 * @since 2023/6/11
 */
@Component
public class CostomPasswordEncoder  extends BCryptPasswordEncoder implements PasswordEncoder {
}
