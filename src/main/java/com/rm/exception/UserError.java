package com.rm.exception;
import org.springframework.http.HttpStatus;

public enum UserError implements ErrorCode{
    USER_NOTFOUND(HttpStatus.NOT_FOUND,"E101","로그인이 필요합니다."),
	INVALID_IDPASSWORD(HttpStatus.FORBIDDEN,"E102","아이디나 비밀번호가 틀렸습니다.");
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
