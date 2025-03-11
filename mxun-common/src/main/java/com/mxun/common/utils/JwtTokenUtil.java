package com.mxun.common.utils;

import com.mxun.common.resultView.BusinessException;
import com.mxun.common.enums.ErrorEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @Description: 用户token工具类
 * @Author: liuzhilin
 * @Date: 2025/3/1
 */
public class JwtTokenUtil {

    // 密钥，用于签名
    private static final String SECRET_KEY_STR = "2@$￥jio85f;黑'p;-=9()LlI /nn`njds^*j和~lk!7uffvx3#45%5Jhs邋SSx1ffvx3#45%5Jhs邋2D";

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STR.getBytes(StandardCharsets.UTF_8));;
    public static void main(String[] args) {
        String token = "";//createJwtToken("3455", "5566", "02", 86400000); // 设置过期时间为一天
        System.out.println("Generated JWT: " + token);

        try {
            Claims claims = validateToken(token);
            System.out.println("Token验证成功");
            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Name: " + claims.get("name"));
            System.out.println("Expiration Time: " + claims.getExpiration());
        } catch (Exception e) {
            System.out.println("Token验证失败: " + e.getMessage());
        }
    }

    /**
     * @Description: 验证token
     * @Author: liuzhilin
     * @Date: 2025/3/9 11:27
     */
    public static Claims validateToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token).getBody();
        }catch (Exception e){
            throw new BusinessException(ErrorEnum.USER_TOKEN_VALID_ERROR);
        }

        // 可选：检查过期时间
        Date expirationTime = claims.getExpiration();
        if (new Date().after(expirationTime)) {
            throw new BusinessException(ErrorEnum.USER_TOKEN_EXPIRE_ERROR);
        }

        return claims;
    }

    /**
     * @Description: 创建token
     * @Author: liuzhilin
     * @Date: 2025/3/9 11:28
     */
    public static String createJwtToken(String subject, String name, String email, String tel, String userType, long expirationMillis) {
        return Jwts.builder()
                .setSubject(subject) // 设置主题，通常是用户ID
                .claim("name", name) // 自定义声明
                .claim("email", email) // 自定义声明
                .claim("tel", tel) // 自定义声明
                .claim("userType", userType) // 自定义声明
                .setIssuedAt(new Date()) // 发行时间
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis)) // 过期时间
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY_STR.getBytes()) // 签名算法和密钥
                .compact();
    }
}
