package com.rm.socialAccount;

import com.rm.exception.IllegalAuthProviderException;

public enum AuthProvider {
    LOCAL("local"),GOOGLE("google"),KAKAO("kakao"),GITHUB("github");

    private final String provider;

    private AuthProvider(String provider) {
        this.provider=provider;
    }
    
    public static AuthProvider from(String provider){
        for(AuthProvider p : values()){
            if(p.provider.equalsIgnoreCase(provider)) return p;
        }
        throw new IllegalAuthProviderException();
    }
}
