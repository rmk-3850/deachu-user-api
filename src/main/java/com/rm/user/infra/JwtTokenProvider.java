package com.rm.user.infra;

import java.security.Key;
import java.time.Clock;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
	private final Key key;	
	private final Clock clock;	
	private final long tokenValidMillisecond;
	
	public JwtTokenProvider(
			Clock clock,
			@Value("${jwt.secret}") String base64Key,
			@Value("${jwt.token-valid-ms}") String tokenValidMillisecond) {
		byte[] keyBytes=Base64.getDecoder().decode(base64Key);
		this.key=Keys.hmacShaKeyFor(keyBytes);
		this.clock=clock;
		this.tokenValidMillisecond=Long.parseLong(tokenValidMillisecond);
	}
	
	public String createToken(Long id,List<String> roles) {
		log.info("[createToken] 토큰 생성 시작");
		Instant now=Instant.now(clock);
		Date issuedAt=Date.from(now);
		Date expiration=Date.from(now.plusMillis(tokenValidMillisecond));
		String token=Jwts.builder()
				.setSubject(String.valueOf(id))
				.claim("roles", roles)
				.setIssuedAt(issuedAt)
				.setExpiration(expiration)
				.signWith(key)
				.compact();
		log.info("[createToken] 토큰 생성 완료");
		return token;
	}
}
