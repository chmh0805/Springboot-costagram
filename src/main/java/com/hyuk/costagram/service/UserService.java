package com.hyuk.costagram.service;

import com.hyuk.costagram.domain.user.User;
import com.hyuk.costagram.domain.user.UserRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	
	public void 회원프로필(int userId) {
		User userEntity = userRepository.findById(userId).orElseThrow(() -> {throw new IllegalArgumentException();});
	}
}
