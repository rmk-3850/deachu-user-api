package com.rm.socialAccount.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.socialAccount.AuthProvider;
import com.rm.socialAccount.entity.SocialAccount;

public interface SocialAccountRepository extends JpaRepository<SocialAccount,Long>{
    Optional<SocialAccount> findByProviderAndProviderUserId(AuthProvider provider,String providerUserId);
}
