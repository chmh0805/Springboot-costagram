package com.hyuk.costagram.service;

import com.hyuk.costagram.domain.follow.FollowRepository;
import com.hyuk.costagram.domain.image.Image;
import com.hyuk.costagram.domain.user.User;
import com.hyuk.costagram.domain.user.UserRepository;
import com.hyuk.costagram.web.dto.user.UserProfileRespDto;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final FollowRepository followRepository;
	
	@Transactional(readOnly = true)
	public UserProfileRespDto 회원프로필(int principalId, int userId) {
		User userEntity = userRepository.findById(userId).orElseThrow(() -> {
			throw new IllegalArgumentException();});
		boolean mFollowState = false;
		
		if (followRepository.isFollowing(principalId, userId) == 1) {
			mFollowState = true;
		}
		
		List<Image> images = userEntity.getImages();
		
		UserProfileRespDto userProfileRespDto = UserProfileRespDto.builder()
				.user(userEntity)
				.followState(mFollowState)
				.followCount(followRepository.mFollowCount(userId))
				.imageCount(images.size())
				.build();
		
		return userProfileRespDto;
	}
}
