package com.stylefeng.guns.api.modular.auth.validator.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.modular.auth.validator.IReqValidator;
import com.stylefeng.guns.api.modular.auth.validator.dto.Credence;
import org.springframework.stereotype.Component;

/**
 * 直接验证账号密码是不是admin
 *
 * @author fengshuonan
 * @date 2017-08-23 12:34
 */
@Service
@Component
public class SimpleValidator implements IReqValidator {

    private static String USER_NAME = "admin";

    private static String PASSWORD = "admin";

    @Override
    public boolean validate(Credence credence) {

        String userName = credence.getCredenceName();
        String password = credence.getCredenceCode();

        if (USER_NAME.equals(userName) && PASSWORD.equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}
