package com.kjq.aop;

import cn.hutool.core.util.ObjUtil;
import com.kjq.annotation.AuthCheck;
import com.kjq.model.enums.UserRoleEnum;
import com.kjq.model.entity.User;
import com.kjq.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.kjq.common.ErrorCode.NOT_LOGIN_ERROR;
import static com.kjq.common.ErrorCode.NO_AUTH_ERROR;
import static com.kjq.common.ResultUtils.exception;

/**
 * 权限校验 AOP
 */
@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    /**
     * 执行拦截校验权限
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String role = authCheck.value();
        // 不为空才校验
        if (StringUtils.isNotBlank(role)) {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            // 当前登录用户
            User user = userService.getLoginUser(request);
            if (ObjUtil.isEmpty(user)){
                throw exception(NOT_LOGIN_ERROR);
            }
            UserRoleEnum mustUserRoleEnum = UserRoleEnum.getEnumByValue(user.getRole());
            if (mustUserRoleEnum == null) {
                throw exception(NO_AUTH_ERROR);
            }
            String userRole = mustUserRoleEnum.getType();
            // 必须有管理员权限
            if (!role.equals(userRole)) {
                throw exception(NO_AUTH_ERROR);
            }
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}

