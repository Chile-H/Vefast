package com.github.chileh.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.chileh.model.commom.dtos.ResponseResult;
import com.github.chileh.model.users.dtos.LoginDto;
import com.github.chileh.model.users.pojos.ApUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ApUserService extends IService<ApUser> {

    /**
     * app登录功能
     * @param dto 登录请求的数据传输对象，包含用户名、密码等登录信息
     * @return 登录结果，包含是否成功、错误信息等
     */
    ResponseResult<Map<String,Object>> login(LoginDto dto);
}
