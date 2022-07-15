package com.example.demo.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


public class ToKenUtils {

    /**
     * 生成token
     *
     * @param userId
     * @param passWord
     * @return
     */
    public static String getToken(String userId, String passWord) {

        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(passWord)); // 以 password 作为 token 的密钥
    }

    public static Long getUserId(HttpServletRequest request) {
        Long userId = null;
        String token = request.getHeader("token");
        if (BeanUtils.isNotEmpty(token)) {
            userId = Long.valueOf(JWT.decode(token).getAudience().get(0));
        }
        return userId;
    }

}
