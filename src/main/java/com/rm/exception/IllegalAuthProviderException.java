package com.rm.exception;

public class IllegalAuthProviderException extends BusinessException{
    public IllegalAuthProviderException() {
        super(UserError.INVALID_AUTHPROVIDER);
    }
}
