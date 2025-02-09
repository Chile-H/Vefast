package com.github.chileh.service.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chileh.model.commom.dtos.ResponseResult;
import com.github.chileh.model.commom.enums.AppHttpCodeEnum;
import com.github.chileh.model.users.dtos.LoginDto;
import com.github.chileh.model.users.pojos.ApUser;
import com.github.chileh.model.users.vo.LoginVo;
import com.github.chileh.service.user.mapper.ApUserMapper;
import com.github.chileh.service.user.service.ApUserService;
import com.github.chileh.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {

    /**
     * app登录功能
     * @param dto 登录请求的数据传输对象，包含手机号、密码
     * @return 登录结果，包含是否成功、错误信息等
     */
    @Override
    public ResponseResult<Map<String,Object>> login(LoginDto dto){
        //1. 正常登录 用户名和密码
        if(StringUtils.hasText(dto.getPhone()) && StringUtils.hasText(dto.getPassword())){
            //1.1 根据用户手机号查询用户信息
            ApUser dbUser = getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, dto.getPhone()));
            if(dbUser == null){
                return ResponseResult.error(AppHttpCodeEnum.INVALID_CREDENTIALS, "用户信息不存在");
            }

            //1.2 验证密码
            String salt = dbUser.getSalt();
            String password = dto.getPassword();
            String hashedpswd;

            try {
                //获取SHA-256的MessageDigest实例
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                //更新盐值和密码到MessageDigest实例
                digest.update((password+salt).getBytes());
                //计算哈希值
                byte[] hashedBytes = digest.digest();
                //将哈希值转换为Base64编码的字符串
                hashedpswd = Base64.getEncoder().encodeToString(hashedBytes);
            } catch (Exception e) {
                throw new RuntimeException("将密码使用SHA-256生成哈希值时出错", e);
            }

            if(!hashedpswd.equals(dbUser.getPassword())){
                return ResponseResult.error(AppHttpCodeEnum.INVALID_CREDENTIALS,"密码错误");
            }

            //1.3 返回jwt token
            LoginVo loginVo = new LoginVo();
            BeanUtils.copyProperties(dbUser, loginVo);

            String token = JwtUtil.generateToken(dbUser.getId().longValue());
            Map<String, Object> map = new HashMap<>();
            map.put("token",token);
            map.put("user",loginVo);

            return ResponseResult.success(map, "登录成功");
        }else{
            //2. 游客登录
            Map<String, Object> map = new HashMap<>();
            map.put("token",0L);

            return ResponseResult.success(map,"游客登录成功");
        }

    }

}
