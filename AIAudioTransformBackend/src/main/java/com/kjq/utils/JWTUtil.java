package com.kjq.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kjq.model.entity.User;

import java.util.Calendar;

public class JWTUtil {

    /**
     * 生成token  header.payload.singature
     */
    private static final String SING = "XIAOSHUANG"; // 密钥要修改
    private static final String USER_ID = "userId";
    private static final String USER_ROLE = "userRole";

    public static String getToken(User user) {

        Calendar instance = Calendar.getInstance();
        // 默认1年过期
        instance.add(Calendar.YEAR, 1);

        //创建jwt builder
        JWTCreator.Builder builder = JWT.create()
        // payload
        .withClaim(USER_ID, user.getId())
        .withClaim(USER_ROLE, user.getRole());

        return builder.withExpiresAt(instance.getTime())  //指定令牌过期时间
                .sign(Algorithm.HMAC256(SING));
    }

    /**
     * 验证token  合法性
     */
    public static User verify(String token) {
        User user = null;
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
            user = new User();
            user.setId(decodedJWT.getClaim(USER_ID).asInt());
            user.setRole(decodedJWT.getClaim(USER_ROLE).asInt());
        } catch (Exception ignored) {}
        return user;
    }

}
