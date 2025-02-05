package com.github.chileh.service.user.controller.v1;

import com.github.chileh.model.commom.dtos.ResponseResult;
import com.github.chileh.model.users.dtos.LoginDto;
import com.github.chileh.service.user.service.ApUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/login")
public class ApUserLoginController {

    private final ApUserService apUserService;

    public ApUserLoginController(ApUserService apUserService){
        this.apUserService = apUserService;
    }

    @PostMapping("/login_auth")
    public ResponseResult<Map<String,Object>> login(@RequestBody LoginDto dto){
        return apUserService.login(dto);
    }
}
