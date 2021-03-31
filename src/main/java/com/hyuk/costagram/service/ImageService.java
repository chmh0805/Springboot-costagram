package com.hyuk.costagram.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyuk.costagram.config.auth.PrincipalDetails;
import com.hyuk.costagram.domain.image.Image;
import com.hyuk.costagram.domain.image.ImageRepository;
import com.hyuk.costagram.domain.tag.Tag;
import com.hyuk.costagram.domain.tag.TagRepository;
import com.hyuk.costagram.utils.TagUtils;
import com.hyuk.costagram.web.dto.image.ImageReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	private final TagRepository tagRepository;
	
	@Value("${file.path}") // @Value("$()") 라고 하면 application.yml 파일에 접근할 수 있다. 
	private String uploadFolder;
	
	@Transactional(readOnly = true)
	public List<Image> 피드이미지(int principalId) {
		return imageRepository.mFeed(principalId);
	}
	
	@Transactional
	public void 사진업로드(ImageReqDto imageReqDto, PrincipalDetails principalDetails) {
		
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid + "_" + imageReqDto.getFile().getOriginalFilename();
		System.out.println("파일명 : " + imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder + imageFileName);
		System.out.println("파일패스 : " + imageFilePath);
		try {
			Files.write(imageFilePath, imageReqDto.getFile().getBytes()); // 폴더명+파일명에 파일 생성
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// image Entity의 Tag는 주인이 아니다. Image Entity를 통해서 Tag를 save 할 수 없다.
		
		// 1. image 저장
		Image image = imageReqDto.toEntity(imageFileName, principalDetails.getUser());
		Image imageEntity = imageRepository.save(image);
		
		// 2. Tag 저장
		List<Tag> tags = TagUtils.parsingToTagObject(imageReqDto.getTags(), imageEntity);
		tagRepository.saveAll(tags);
	}
}
