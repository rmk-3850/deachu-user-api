package com.rm.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.exception.PasswordNotMatchException;
import com.rm.exception.UserNotFoundException;
import com.rm.user.dto.SignRequestEssence;
import com.rm.user.dto.SignResponse;
import com.rm.user.dto.SignUpRequestDto;
import com.rm.user.dto.UpdateRequestDto;
import com.rm.user.entity.User;
import com.rm.user.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public boolean existsByUid(String uid) {
		return userRepository.existsByUid(uid);
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public User getUserOrThrow(Long id) {
		return userRepository.findById(id).orElseThrow(()->new UserNotFoundException());
	}
	
	public boolean passwordIsMatch(Long id,String password) {
		User user=getUserOrThrow(id);
		return passwordEncoder.matches(password, user.getPassword());
	}
	
	@Transactional
	public SignResponse select(Long id){
		User user = getUserOrThrow(id);
		return SignResponse.from(user);
	}
	@Transactional
	public SignResponse signUp(SignUpRequestDto dto) {
		User savedUser = userRepository.save(
			User.builder()
				.uid(dto.e().uid())
				.password(passwordEncoder.encode(dto.e().password()))
				.name(dto.name())
				.email(dto.email())
				.phoneNumber(dto.phoneNumber())
				.roles(dto.roles())
				.build()
		);		
		return SignResponse.from(savedUser);
	}
	
	@Transactional
	public User signIn(SignRequestEssence dto){
		User user=userRepository.getByUid(dto.uid());
		if(user==null) throw new UserNotFoundException();
		if(!passwordEncoder.matches(dto.password(), user.getPassword())) throw new PasswordNotMatchException();
		return user;
	}
	
	@Transactional
	public SignResponse update(Long id,UpdateRequestDto dto){
		User user=getUserOrThrow(id);
		user.update(
			dto.name(),
			passwordEncoder.encode(dto.e().password()),
			dto.phoneNumber()
		);
		return SignResponse.from(user);
	}
	
	@Transactional
	public void delete(Long id){
		User user=getUserOrThrow(id);
		userRepository.delete(user);
		return;
	}
}
