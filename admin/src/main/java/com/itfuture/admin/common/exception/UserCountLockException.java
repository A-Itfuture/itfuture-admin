package com.itfuture.admin.common.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2023/01/11 13:31
 */
public class UserCountLockException extends AuthenticationException {
    public UserCountLockException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserCountLockException(String msg) {
        super(msg);
    }
}
