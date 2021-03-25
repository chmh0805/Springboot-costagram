package com.hyuk.costagram.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hyuk.costagram.service.UserService;
import com.hyuk.costagram.web.dto.user.UserProfileRespDto;
import com.hyuk.costagram.config.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	
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
}
