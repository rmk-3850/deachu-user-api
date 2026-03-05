package com.rm.exception;

public class UserNotFoundException extends BusinessException{
    public UserNotFoundException() {
        super(UserError.USER_NOTFOUND);
    }
}
