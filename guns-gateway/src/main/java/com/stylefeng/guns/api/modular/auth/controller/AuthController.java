package com.stylefeng.guns.api.modular.auth.controller;

import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.api.common.exception.BizExceptionEnum;
import com.stylefeng.guns.api.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.api.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.api.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.api.modular.auth.validator.IReqValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Resource(name = "simpleValidator")
    private IReqValidator reqValidator;

    @RequestMapping(value = "${jwt.auth-path}")
    public ResponseEntity<?> createAuthenticationToken(AuthRequest authRequest) {

        //boolean validate = reqValidator.validate(authRequest);
        Boolean validate = userService.selectByUsernameAndPassword(authRequest.getUsername(), authRequest.getPassword());
        if (validate) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(authRequest.getUsername(), randomKey);
            return ResponseEntity.ok(new AuthResponse(token, randomKey));
        } else {
            throw new GunsException(BizExceptionEnum.AUTH_REQUEST_ERROR);
        }
    }
}