package com.hyuk.costagram.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateReqDto {
	private String name; // 이름
	private String password;
	private String website; // 자기 홈페이지
	private String bio; // 자기 소개
	private String phone;
	private String gender;
}
