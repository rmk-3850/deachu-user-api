package com.rm.user.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rm.exception.CommonResponse;
import com.rm.exception.UserSuccess;
import com.rm.user.dto.SignResponse;
import com.rm.user.dto.UpdateRequestDto;
import com.rm.user.service.SignService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final SignService signService;

	@Operation(description = "로그아웃")
	@PreAuthorize("isAuthenticated()")
	@PostMapping()
	public ResponseEntity<CommonResponse<Void>> logout(){
		ResponseCookie cookie = ResponseCookie.from("accessToken", "")
			.httpOnly(true)
			.secure(true)
			.sameSite("None")
			.path("/")
			.build();
		return ResponseEntity
		.status(UserSuccess.SUCCESS.getStatus())
		.header(HttpHeaders.SET_COOKIE, cookie.toString())
		.body(CommonResponse.success(UserSuccess.SUCCESS, null));
	}


	@Operation(description = "개인 정보 조회")
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public ResponseEntity<CommonResponse<SignResponse>> select(
		@AuthenticationPrincipal String id
		){
		SignResponse data = signService.select(Long.parseLong(id));
		return ResponseEntity
			.status(UserSuccess.SUCCESS.getStatus())
			.body(CommonResponse.success(UserSuccess.SUCCESS, data));
	}

	@Operation(description = "회원 정보 수정")
	@PreAuthorize("isAuthenticated()")
	@PutMapping
	public ResponseEntity<CommonResponse<SignResponse>> update(
			@AuthenticationPrincipal String id,
			@Valid@RequestBody UpdateRequestDto dto
		){
		SignResponse data=signService.update(Long.parseLong(id),dto);
		return ResponseEntity
			.status(UserSuccess.SUCCESS.getStatus())
			.body(CommonResponse.success(UserSuccess.SUCCESS, data));
	}
	
	@Operation(description = "회원 탈퇴")
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping
	public ResponseEntity<CommonResponse<Void>> delete(
		@AuthenticationPrincipal String id
		){
		signService.delete(Long.parseLong(id));
		return ResponseEntity
			.status(UserSuccess.SUCCESS.getStatus())
			.body(CommonResponse.success(UserSuccess.SUCCESS, null));
	}

}
