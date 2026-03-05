package com.rm.exception;

public class PasswordNotMatchException extends BusinessException{
	public PasswordNotMatchException() {
		super(UserError.INVALID_IDPASSWORD);
	}
}
