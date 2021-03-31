package com.hyuk.costagram.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyuk.costagram.config.auth.PrincipalDetails;
import com.hyuk.costagram.service.FollowService;
import com.hyuk.costagram.service.UserService;
import com.hyuk.costagram.web.dto.CommonRespDto;
import com.hyuk.costagram.web.dto.follow.FollowRespDto;
import com.hyuk.costagram.web.dto.user.UserProfileRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	private final FollowService followService;
	
	@GetMapping("/user/{id}")
	public String profile(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int id, Model model) {
		UserProfileRespDto userProfileRespDto = userService.회원프로필(principalDetails.getUser().getId(), id);
		model.addAttribute("dto", userProfileRespDto);
		return "user/profile";
	}

	@GetMapping("/user/{id}/profileSetting")
	public String profileSetting(@PathVariable int id) {
		return "user/profileSetting";
	}
	
	@CrossOrigin
	@GetMapping("/user/{pageUserId}/follow")
	public @ResponseBody CommonRespDto<?> followUsers(@AuthenticationPrincipal PrincipalDetails principalDetails, 
			@PathVariable int pageUserId) {
		
		List<FollowRespDto> follows = followService.팔로우리스트(principalDetails.getUser().getId(), pageUserId);
		
		return new CommonRespDto<>(1, follows);
	}
}
