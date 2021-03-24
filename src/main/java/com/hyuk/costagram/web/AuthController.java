package com.hyuk.costagram.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyuk.costagram.service.AuthService;
import com.hyuk.costagram.utils.Script;
import com.hyuk.costagram.web.dto.auth.UserJoinReqDto;

import lombok.RequiredArgsConstructor;

// 시작 주소 : /auth
@RequiredArgsConstructor
@Controller
public class AuthController {
	
	private final AuthService authService;

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "auth/loginForm";
	}
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "auth/joinForm";
	}
	
	@PostMapping("/auth/join")
	public @ResponseBody String join(UserJoinReqDto userJoinReqDto) {
		authService.회원가입(userJoinReqDto.toEntity());
		return Script.href("성공", "/auth/loginForm");
	}
}
