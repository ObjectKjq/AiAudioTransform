package com.kjq.service;

import com.kjq.model.entity.Audio;
import com.kjq.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kjq.model.vo.user.UserRespVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author kongdefang
 * @since 2025-03-08
 */
public interface UserService extends IService<User> {

    /**
     * 获取用户列表
     *
     * @return
     */
    List<User> getUsers();

    /**
     * 根据token获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 根据Authorization获取当前登录用户
     *
     * @param request
     * @return
     */
    UserRespVO getUserInfo(HttpServletRequest request);

}
