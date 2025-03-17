package com.kjq;

import cn.hutool.crypto.digest.DigestUtil;
import com.kjq.model.entity.User;
import com.kjq.utils.JWTUtil;
import org.junit.jupiter.api.Test;

public class CommonTest {

    @Test
    public void test02() {
        System.out.println(DigestUtil.md5Hex("admin"));
    }

    @Test
    public void test01() {
        User user = new User();
        user.setId(1);
        user.setRole(12);
        String token = JWTUtil.getToken(user);
        System.out.println(token);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user1 = JWTUtil.verify(token);
        System.out.println(user1);
    }

}
