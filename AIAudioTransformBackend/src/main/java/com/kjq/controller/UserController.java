package com.kjq.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kjq.annotation.AuthCheck;
import com.kjq.common.BaseResponse;
import com.kjq.constant.CommonConstant;
import com.kjq.constant.UserConstant;
import com.kjq.enums.IsDisableEnum;
import com.kjq.enums.UserRoleEnum;
import com.kjq.model.entity.User;
import com.kjq.model.vo.user.*;
import com.kjq.service.UserService;
import com.kjq.utils.JWTUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.kjq.common.ErrorCode.*;
import static com.kjq.common.ResultUtils.exception;
import static com.kjq.common.ResultUtils.success;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author kongdefang
 * @since 2025-03-08
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param userRegisterReqVO
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<String> userRegister(@RequestBody UserRegisterReqVO userRegisterReqVO) {
        String password = userRegisterReqVO.getPassword();
        String checkPassword = userRegisterReqVO.getCheckPassword();
        if (!StrUtil.equals(password, checkPassword)){
            throw exception(PARAMS_INCONSISTENCY);
        }
        userRegisterReqVO.setPassword(DigestUtil.md5Hex(password));
        User user = new User();
        BeanUtils.copyProperties(userRegisterReqVO, user);
        user.setRole(UserRoleEnum.USER.getValue());
        user.setIsDisable(IsDisableEnum.NO.getStatus());
        userService.save(user);
        String token = JWTUtil.getToken(user);
        return success(token);
    }

    /**
     * 用户登录
     *
     * @param userLoginReqVO
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<String> userLogin(@RequestBody UserLoginReqVO userLoginReqVO) {
        String username = userLoginReqVO.getUsername();
        String password = userLoginReqVO.getPassword();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = userService.getOne(wrapper);
        if (ObjectUtils.isEmpty(user) || !StrUtil.equals(user.getPassword(), DigestUtil.md5Hex(password))){
            throw exception(USERNAME_OR_PARAMS_ERROR);
        }
        String token = JWTUtil.getToken(user);
        return success(token);
    }

    /**
     * 添加用户
     *
     * @param userCreateReqVO
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> addUser(@RequestBody UserCreateReqVO userCreateReqVO) {
        User user = new User();
        BeanUtils.copyProperties(userCreateReqVO, user);
        userService.save(user);
        return success(true);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    @AuthCheck(UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(@RequestParam("id") Integer id) {
        userService.removeById(id);
        return success(true);
    }

    /**
     * 修改用户
     *
     * @param userUpdateReqVO
     * @return
     */
    @PutMapping("/update")
    @AuthCheck(UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateReqVO userUpdateReqVO) {
        User user = new User();
        BeanUtils.copyProperties(userUpdateReqVO, user);
        userService.updateById(user);
        return success(true);
    }

    /**
     * 根据Authorization获取用户
     *
     * @return
     */
    @GetMapping("/info/get")
    public BaseResponse<UserRespVO> getUserInfo(HttpServletRequest request) {
        String token = request.getHeader(CommonConstant.AUTHORIZATION);
        User user = JWTUtil.verify(token);
        if (ObjectUtils.isEmpty(user)) {
            throw exception(TOKEN_ERROR);
        }
        Integer id = user.getId();
        user = userService.getById(id);
        UserRespVO userRespVO = new UserRespVO();
        BeanUtils.copyProperties(user, userRespVO);
        return success(userRespVO);
    }

    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    @AuthCheck(UserConstant.ADMIN_ROLE)
    public BaseResponse<UserRespVO> getUser(@RequestParam("id") Integer id) {
        User user = userService.getById(id);
        UserRespVO userRespVO = new UserRespVO();
        BeanUtils.copyProperties(user, userRespVO);
        return success(userRespVO);
    }

    /**
     * 分页获取用户列表
     *
     * @param userPageReqVO
     * @return
     */
    @PostMapping("/page")
    @AuthCheck(UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserRespVO>> getPage(@RequestBody UserPageReqVO userPageReqVO) {
        // 获取结果
        Integer pageNo = userPageReqVO.getPageNo();
        Integer pageSize = userPageReqVO.getPageSize();
        Page<User> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        Page<User> userPage = userService.page(page, wrapper);
        // 生成返回值
        List<User> users = userPage.getRecords();
        List<UserRespVO> userRespVOS = users.stream()
                .map(user -> {
                    UserRespVO userRespVO = new UserRespVO();
                    BeanUtils.copyProperties(user, userRespVO);
                    return userRespVO;
                }).collect(Collectors.toList());
        Page<UserRespVO> result = new Page<>(pageNo, pageSize, userPage.getTotal());
        result.setRecords(userRespVOS);
        return success(result);
    }

    /**
     * 获取用户列表
     *
     * @return
     */
    @GetMapping("/list")
    @AuthCheck(UserConstant.ADMIN_ROLE)
    public BaseResponse<List<UserRespVO>> getList() {
        List<User> users = userService.getUsers();
        List<UserRespVO> userRespVOS = users.stream()
                .map(user -> {
                    UserRespVO userRespVO = new UserRespVO();
                    BeanUtils.copyProperties(user, userRespVO);
                    return userRespVO;
                }).collect(Collectors.toList());
        return success(userRespVOS);
    }

}

