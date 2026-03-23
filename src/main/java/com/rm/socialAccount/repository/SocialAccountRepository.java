package com.rm.socialAccount.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rm.socialAccount.AuthProvider;
import com.rm.socialAccount.entity.SocialAccount;

public interface SocialAccountRepository extends JpaRepository<SocialAccount,Long>{
    @Query("""
        SELECT sa FROM SocialAccount sa
        JOIN FETCH sa.user
        WHERE sa.provider = :provider
        AND sa.providerUserId = :providerUserId
    """)
    Optional<SocialAccount> findByProviderAndProviderUserIdWithUser(AuthProvider provider,String providerUserId);
}
