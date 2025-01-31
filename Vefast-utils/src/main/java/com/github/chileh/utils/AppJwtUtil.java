package com.github.chileh.utils;

import io.jsonwebtoken.*;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class AppJwtUtil {

    // TOKEN的有效期一天（S）
    private static final int TOKEN_TIME_OUT = 3_600;
    // 加密KEY
    private static final String TOKEN_ENCRYPT_KEY = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY";
    // 最小刷新间隔(S)
    private static final int REFRESH_TIME = 300;

    // 生成JWT令牌
    public static String getToken(Long id){
        Map<String, Object> claimMaps = new HashMap<>();
        claimMaps.put("id",id);
        claimMaps.put("jti", UUID.randomUUID().toString());  // 自定义唯一标识符
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .issuedAt(new Date(currentTime))  //签发时间
                .subject("system")  //设置主题
                .issuer("laoli") //设置签发者
                .audience().add("app").and()    //设置接收者
                .compressWith(Jwts.ZIP.GZIP)  //数据压缩方式
                .signWith(generalKey()) //设置签名，自动推断算法
                .expiration(new Date(currentTime + TOKEN_TIME_OUT * 1000))  //过期时间戳
                .claims(claimMaps) //将自定义的声明（Claims）添加到 JWT 的有效载荷中
                .compact();
    }

    /**
     * 获取token中的claims信息
     *
     * @param token 要解析的JWT令牌字符串
     * @return 解析后的Jws<Claims>对象，包含JWT的头部和有效载荷信息
     */
    private static Jws<Claims> getJws(String token) {
        return Jwts.parser()
                .verifyWith(generalKey())
                .build()
                .parseSignedClaims(token);
    }

    /**
     * 获取payload body信息
     *
     * @param token JWT令牌字符串
     * @return 解析后的Claims对象，包含JWT的有效载荷信息；如果JWT过期，则返回null
     */
    public static Claims getClaimsBody(String token) {
        try {
            return getJws(token).getPayload();
        }catch (ExpiredJwtException e){
            return null;
        }
    }

    /**
     * 获取header body信息
     *
     * @param token JWT令牌字符串，用于解析JWT的头部信息
     * @return 解析后的JwsHeader对象，包含JWT的头部信息；如果JWT过期或解析失败，则返回null
     */
    public static JwsHeader getHeaderBody(String token) {
        return getJws(token).getHeader();
    }

    /**
     * 是否过期
     *
     * @param claims JWT的Claims对象，包含有效载荷信息
     * @return -1：有效但需要自动刷新TOKEN，0：有效且不需要自动刷新 TOKEN，1：过期，2：过期
     */
    public static int verifyToken(Claims claims) {
        if(claims==null){
            return 1;
        }
        try {
            if(claims.getExpiration().before(new Date())){
                return 1;
            }
            // 检查是否需要自动刷新TOKEN
            if((claims.getExpiration().getTime()-System.currentTimeMillis())>REFRESH_TIME*1000){
                return -1;
            }else {
                return 0;
            }
        } catch (ExpiredJwtException ex) {
            return 1;
        }catch (Exception e){
            return 2;
        }
    }

    /**
     * 由字符串生成加密key
     *
     * @return 生成的SecretKey对象，用于AES加密
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(TOKEN_ENCRYPT_KEY.getBytes());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }
}
