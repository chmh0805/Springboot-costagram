package com.hyuk.costagram.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyuk.costagram.domain.likes.LikesRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikesService {
	private final LikesRepository likeRepository;
	
	@Transactional
	public void 좋아요(int imageId, int userId) {
		likeRepository.mSave(imageId, userId);
	}
	
	@Transactional
	public void 싫어요(int imageId, int userId) {
		likeRepository.mDelete(imageId, userId);
	}
}
