package com.hyuk.costagram.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFollowRespDto {
	private Integer id;
	private String username;
}
