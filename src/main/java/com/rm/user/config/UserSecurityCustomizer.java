package com.rm.user.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

import com.rm.security.SecurityCustomizer;
import com.rm.socialAccount.service.CustomOAuth2Service;
import com.rm.socialAccount.service.OAuth2SuccessHandler;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserSecurityCustomizer implements SecurityCustomizer{
	private final CustomOAuth2Service customOAuth2Service;
	private final OAuth2SuccessHandler oAuth2SuccessHandler;
	@Override
	public void customize(HttpSecurity http) throws Exception {
		http
		.oauth2Login(oauth->oauth
			.userInfoEndpoint(user->user
				.userService(customOAuth2Service)
			)
			.successHandler(oAuth2SuccessHandler)
		)
		.authorizeHttpRequests(auth->auth
			.requestMatchers("/public/**").permitAll()
			.requestMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated()
		);
	}
}
