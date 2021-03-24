package com.hyuk.costagram.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyuk.costagram.config.auth.PrincipalDetails;
import com.hyuk.costagram.service.FollowService;
import com.hyuk.costagram.web.dto.CommonRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class FollowController {

	private final FollowService followService;
	
	@PostMapping("/follow/{toUserId}")
	public CommonRespDto<?> follow(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable int toUserId) {
		int result = followService.팔로우(principal.getUser().getId(), toUserId);
		return new CommonRespDto<>(1, result);
	}
	
	@DeleteMapping("/follow/{toUserId}")
	public CommonRespDto<?> unFollow(@AuthenticationPrincipal PrincipalDetails principal, @PathVariable int toUserId) {
		int result = followService.언팔로우(principal.getUser().getId(), toUserId);
		return new CommonRespDto<>(1, result);
	}
}