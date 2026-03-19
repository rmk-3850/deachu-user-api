package com.rm.socialAccount.entity;

import com.rm.socialAccount.AuthProvider;
import com.rm.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "social_account",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_user_provider",
            columnNames = {"user_id","provider"}
        ),
        @UniqueConstraint(
            name = "uk_provider_provider_user_id",
            columnNames = {"provider","provider_user_id"}
        )
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialAccount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(name = "provider_user_id",nullable = false)
    private String providerUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    public SocialAccount(
        AuthProvider provider,
        String providerUserId,
        User user
        ) {
        this.provider=provider;
        this.providerUserId=providerUserId;
        this.user=user;
    }
}
