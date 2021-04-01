package com.hyuk.costagram.web.dto.explore;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExploreRespDto {
	private int imageId;
	private int userId;
	private String postImageUrl;
	private BigInteger likeCount;
}
