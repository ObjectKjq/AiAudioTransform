package com.kjq.mapper;

import com.kjq.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author kongdefang
 * @since 2025-03-08
 */
public interface UserMapper extends BaseMapper<User> {

    User selectUserById(Integer id);
}
