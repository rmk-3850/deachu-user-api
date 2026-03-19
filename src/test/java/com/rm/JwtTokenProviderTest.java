package com.rm;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.Key;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rm.user.infra.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@ExtendWith(MockitoExtension.class)
public class JwtTokenProviderTest {
    @Test
    void 토큰이_정상적으로_생성된다(){
        //given
        Instant fixed = Instant.parse("2025-03-06T00:00:00Z");
        Clock clock = Clock.fixed(fixed, ZoneOffset.UTC);
        String secret = Base64.getEncoder()
            .encodeToString("abcdefghijklmnopqrstuvwxyz123456".getBytes());
        JwtTokenProvider provider = new JwtTokenProvider(clock, secret, "60*60*1000");
        byte[] keyBytes=Base64.getDecoder().decode(secret);
		Key key = Keys.hmacShaKeyFor(keyBytes);
        //when
        String token = provider.createToken(1L, List.of("ROLE_USER"));
        //then
        assertThat(token).isNotNull();
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .setClock(()->Date.from(fixed))
            .build()
            .parseClaimsJws(token)
            .getBody();
        assertThat(claims.getSubject()).isEqualTo(1L);
        assertThat(claims.get("roles")).isEqualTo(List.of("ROLE_USER"));
        assertThat(claims.getExpiration().toInstant()).isEqualTo(fixed.plusMillis(60*60*1000));
    }
}
