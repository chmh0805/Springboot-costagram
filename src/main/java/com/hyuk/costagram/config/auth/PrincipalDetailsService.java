package com.hyuk.costagram.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hyuk.costagram.domain.user.User;
import com.hyuk.costagram.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("로그인 진행중 .....");
		User userEntity = userRepository.findByUsername(username);
		System.out.println("userEntity : " + userEntity);
		
		if (userEntity == null) {
			return null;
		} else {
			// SecurityContextHolder의 Authentication 객체 내부에 담긴다.
			return new PrincipalDetails(userEntity);
		}
	}
	
	

	
}
