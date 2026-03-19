package com.rm.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.user.entity.User;



public interface UserRepository extends JpaRepository<User, Long>{
	User getByUid(String uid);
	Optional<User> findByEmail(String email);
	boolean existsByUid(String uid);
	boolean existsByEmail(String email);
}
