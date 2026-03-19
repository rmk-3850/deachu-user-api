package com.rm.user.dto;

import java.util.List;

import com.rm.user.entity.User;

public record SignResponse(
    Long id,
    String uid,
    String name,
    String phoneNumber,
    String email,
    List<String> roles
    ) {
    public static SignResponse from(User user){
        return new SignResponse(
            user.getId(),
            user.getUid(),
            user.getName(),
            user.getPhoneNumber(),
            user.getEmail(),
            user.getRoles());
    }
}
