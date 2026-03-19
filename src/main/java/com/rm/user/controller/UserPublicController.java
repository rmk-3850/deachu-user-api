package com.rm.user.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rm.exception.CommonResponse;
import com.rm.exception.SuccessCode;
import com.rm.exception.UserSuccess;
import com.rm.user.dto.SignRequestEssence;
import com.rm.user.dto.SignResponse;
import com.rm.user.dto.SignUpRequestDto;
import com.rm.user.entity.User;
import com.rm.user.infra.JwtTokenProvider;
import com.rm.user.service.SignService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class UserPublicController {
	private final SignService signService;
	private final JwtTokenProvider tokenProvider;

	@Operation(description = "uid 중복 조회")
	@GetMapping("/uid")
	public ResponseEntity<Boolean> isDuplicationUid(
			@Parameter(description = "아이디",required = true)
			@RequestParam String uid
		){
		return ResponseEntity.ok(signService.existsByUid(uid));
	}
	
	@Operation(description = "email 중복 조회")
	@GetMapping("/email")
	public ResponseEntity<Boolean> isDuplicationEmail(
			@Parameter(description = "아이디",required = true)
			@Email @RequestParam String email
		){
		return ResponseEntity.ok(signService.existsByEmail(email));
	}
	
	@Operation(description = "로그인")
	@PostMapping("/signin")
	public ResponseEntity<CommonResponse<SignResponse>> signIn(
			@Valid@RequestBody SignRequestEssence dto
		){
		User user = signService.signIn(dto);
		String token = tokenProvider.createToken(user.getId(), user.getRoles());
		ResponseCookie cookie = ResponseCookie.from("accessToken", token)
			.httpOnly(true)
			.secure(true)
			.sameSite("None")
			.path("/")
			.build();
		return ResponseEntity
			.status(UserSuccess.SUCCESS.getStatus())
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(CommonResponse.success(UserSuccess.SUCCESS, SignResponse.from(user)));
	}
	
	@Operation(description = "회원 가입")
	@PostMapping("/signup")
	public ResponseEntity<CommonResponse<SignResponse>> signUp(
			@Valid@RequestBody SignUpRequestDto dto
		){
		SignResponse data = signService.signUp(dto);
		return ResponseEntity
			.status(UserSuccess.SUCCESS.getStatus())
			.body(CommonResponse.success(UserSuccess.SUCCESS, data));
	}
	
}
