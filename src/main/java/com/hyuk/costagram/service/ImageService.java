package com.hyuk.costagram.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hyuk.costagram.domain.image.Image;
import com.hyuk.costagram.domain.image.ImageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	
	public List<Image> 피드이미지(int principalId) {
		return imageRepository.mFeed(principalId);
	}
	
}
