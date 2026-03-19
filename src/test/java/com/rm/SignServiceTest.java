package com.rm;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rm.exception.CommonResponse;
import com.rm.exception.PasswordNotMatchException;
import com.rm.exception.UserNotFoundException;
import com.rm.user.dto.SignRequestEssence;
import com.rm.user.dto.SignResponse;
import com.rm.user.entity.User;
import com.rm.user.infra.JwtTokenProvider;
import com.rm.user.repository.UserRepository;
import com.rm.user.service.SignService;

@ExtendWith(MockitoExtension.class)
public class SignServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
	JwtTokenProvider tokenProvider;
    @Mock
	PasswordEncoder passwordEncoder;
    @InjectMocks
    SignService service;

    @Test
    void pw_일치하면_true(){
        //given
        User user = User.builder()
            .password("encoded")
            .build();
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(passwordEncoder.matches("raw", "encoded")).willReturn(true);
        //when&then
        boolean result = service.passwordIsMatch(1L, "raw");
        assertThat(result).isTrue();
    }
    @Test
    void 유저가_없으면_로그인_실패(){
        //given
        given(userRepository.getByUid("rm")).willReturn(null);
        //when&then
        assertThatThrownBy(()->service.signIn(new SignRequestEssence("rm", "1234")))
            .isInstanceOf(UserNotFoundException.class);        
    }
    @Test
    void 비밀번호_틀리면_로그인_실패(){
        //given
        User user = User.builder()
            .uid("rm")
            .password("encoded")
            .roles(List.of("ROLE_USER"))
            .build();
        given(userRepository.getByUid("rm")).willReturn(user);
        given(passwordEncoder.matches("1234","encoded")).willReturn(false);
        //when&then
        assertThatThrownBy(()->service.signIn(new SignRequestEssence("rm", "1234")))
            .isInstanceOf(PasswordNotMatchException.class);
    }
}
