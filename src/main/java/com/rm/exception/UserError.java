package com.rm.exception;
import org.springframework.http.HttpStatus;

public enum UserError implements ErrorCode{
    USER_NOTFOUND(HttpStatus.NOT_FOUND,"E101","로그인이 필요합니다."),
	INVALID_IDPASSWORD(HttpStatus.FORBIDDEN,"E102","아이디나 비밀번호가 틀렸습니다."),
    INVALID_AUTHPROVIDER(HttpStatus.FORBIDDEN,"AUTH-003","허용되지 않은 소셜로그인입니다."),
    EMAIL_ALREADYSIGNUP(HttpStatus.FORBIDDEN,"AUTH-004","이미 가입된 이메일입니다.");
	private final HttpStatus status;
	private final String code;
	private final String msg;
	
    UserError(HttpStatus status, String code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
