package com.rm.socialAccount.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.exception.AlreadySignedUpEmail;
import com.rm.socialAccount.AuthProvider;
import com.rm.socialAccount.entity.SocialAccount;
import com.rm.socialAccount.repository.SocialAccountRepository;
import com.rm.user.entity.User;
import com.rm.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2Service
    implements OAuth2UserService<OAuth2UserRequest,OAuth2User>{
    private final UserRepository userRepository;
    private final SocialAccountRepository socialAccountRepository;
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuthUser = new DefaultOAuth2UserService().loadUser(request);
        String provider = request
            .getClientRegistration()
            .getRegistrationId();
        String providerUserId = provider+"_"+oAuthUser.getAttribute("sub");
        String email = oAuthUser.getAttribute("email");
        AuthProvider authProvider=AuthProvider.from(provider);
        SocialAccount account = socialAccountRepository.findByProviderAndProviderUserIdWithUser(authProvider, providerUserId)
            .orElseGet(() -> {
                User newUser = userRepository.findByEmail(email).orElseGet(() -> 
                    userRepository.save(
                        User.builder()
                            .uid(providerUserId)
                            .email(email)
                            .build()
                    )
                );
                SocialAccount newAccount = new SocialAccount(authProvider, providerUserId, newUser);
                return socialAccountRepository.save(newAccount);
            });
        User user = account.getUser();
        return new CustomOAuth2User(user, oAuthUser.getAttributes());
    }
}
