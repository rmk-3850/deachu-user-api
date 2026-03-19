package com.rm.socialAccount.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.rm.user.entity.User;
import com.rm.user.infra.JwtTokenProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler{
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
        ) throws IOException, ServletException {
        CustomOAuth2User oAuthUser =(CustomOAuth2User)authentication.getPrincipal();
        User user = oAuthUser.getUser();
        String token = jwtTokenProvider.createToken(user.getId(), user.getRoles());
        ResponseCookie cookie = ResponseCookie.from("accessToken", token)
            .httpOnly(true)
			.secure(true)
			.sameSite("None")
            .path("/")
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        String redirectUrl = "https://dx4a7vlkmjql7.cloudfront.net";
        response.sendRedirect(redirectUrl);
    }
}
