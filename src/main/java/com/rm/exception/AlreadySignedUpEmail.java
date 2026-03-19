package com.rm.exception;

public class AlreadySignedUpEmail extends BusinessException{
    public AlreadySignedUpEmail() {
        super(UserError.EMAIL_ALREADYSIGNUP);
    }
}
