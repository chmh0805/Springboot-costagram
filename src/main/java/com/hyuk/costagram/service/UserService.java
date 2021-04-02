package com.hyuk.costagram.service;

import com.hyuk.costagram.config.auth.PrincipalDetails;
import com.hyuk.costagram.domain.follow.FollowRepository;
import com.hyuk.costagram.domain.image.Image;
import com.hyuk.costagram.domain.user.User;
import com.hyuk.costagram.domain.user.UserRepository;
import com.hyuk.costagram.handler.exception.NotFoundUserException;
import com.hyuk.costagram.web.dto.user.UserProfileRespDto;
import com.hyuk.costagram.web.dto.user.UserUpdateReqDto;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final FollowRepository followRepository;
	private final BCryptPasswordEncoder encoder;
	
	@Transactional(readOnly = true)
	public UserProfileRespDto 회원프로필(int principalId, int userId) {
		
		User userEntity = userRepository.findById(userId).orElseThrow(() -> {
			throw new NotFoundUserException("유저 정보를 찾을 수 없습니다.");});
		
		boolean followState = false;	
		if (followRepository.mFollowState(principalId, userId) == 1) {
			followState = true;
		}
		
		List<Image> images = userEntity.getImages();
		images.forEach((image) -> {
			image.setLikeCount(image.getLikes().size());
		});
		
		UserProfileRespDto userProfileRespDto = UserProfileRespDto.builder()
				.user(userEntity)
				.followState(followState)
				.followCount(followRepository.mFollowCount(userId))
				.imageCount(images.size())
				.build();
		
		return userProfileRespDto;
	}
	
	@Transactional
	public User 회원정보변경(PrincipalDetails principalDetails, UserUpdateReqDto userUpdateReqDto) {
		User userEntity = userRepository.findById(principalDetails.getUser().getId()).orElseThrow(() -> {
			throw new NotFoundUserException("유저 정보를 찾을 수 없습니다.");});
		
		if (!userUpdateReqDto.getPassword().equals("")) {
			userEntity.setPassword(encoder.encode(userUpdateReqDto.getPassword()));
		}
		
		userEntity.setName(userUpdateReqDto.getName());
		userEntity.setWebsite(userUpdateReqDto.getWebsite());
		userEntity.setBio(userUpdateReqDto.getBio());
		userEntity.setPhone(userUpdateReqDto.getPhone());
		userEntity.setGender(userUpdateReqDto.getGender());
		
		return userEntity;
	}
}
