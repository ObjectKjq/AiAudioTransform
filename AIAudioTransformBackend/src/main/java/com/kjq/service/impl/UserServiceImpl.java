package com.kjq.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kjq.constant.CommonConstant;
import com.kjq.model.entity.User;
import com.kjq.mapper.UserMapper;
import com.kjq.model.vo.user.UserRespVO;
import com.kjq.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kjq.utils.JWTUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.kjq.common.ErrorCode.TOKEN_ERROR;
import static com.kjq.common.ResultUtils.exception;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author kongdefang
 * @since 2025-03-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> getUsers() {
        return userMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        String token = request.getHeader(CommonConstant.AUTHORIZATION);
        User user = null;
        if (StrUtil.isNotBlank(token)){
            user = JWTUtil.verify(token);
        }
        return user;
    }

    @Override
    public UserRespVO getUserInfo(HttpServletRequest request) {
        String token = request.getHeader(CommonConstant.AUTHORIZATION);
        User user = JWTUtil.verify(token);
        if (ObjectUtils.isEmpty(user)) {
            throw exception(TOKEN_ERROR);
        }
        Integer id = user.getId();
        user = userMapper.selectUserById(id);
        UserRespVO userRespVO = new UserRespVO();
        BeanUtils.copyProperties(user, userRespVO);
        return userRespVO;
    }
}
