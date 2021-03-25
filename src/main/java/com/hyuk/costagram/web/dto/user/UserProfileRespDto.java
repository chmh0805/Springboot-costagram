package com.hyuk.costagram.web.dto.user;

import com.hyuk.costagram.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileRespDto {
	private boolean followState;
	private int followCount;
	private int imageCount;
	private User user;
}
