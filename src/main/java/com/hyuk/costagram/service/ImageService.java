package com.hyuk.costagram.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyuk.costagram.config.auth.PrincipalDetails;
import com.hyuk.costagram.domain.image.Image;
import com.hyuk.costagram.domain.image.ImageRepository;
import com.hyuk.costagram.domain.tag.Tag;
import com.hyuk.costagram.domain.tag.TagRepository;
import com.hyuk.costagram.utils.TagUtils;
import com.hyuk.costagram.web.dto.explore.ExploreRespDto;
import com.hyuk.costagram.web.dto.image.ImageReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	private final TagRepository tagRepository;
	private final EntityManager em;
	
	@Value("${file.path}") // @Value("$()") 라고 하면 application.yml 파일에 접근할 수 있다. 
	private String uploadFolder;
	
	@Transactional(readOnly = true)
	public List<Image> 피드이미지(int principalId) {
		
		List<Image> images = imageRepository.mFeed(principalId);
		images.forEach((image) -> {
			
			int likeCount = image.getLikes().size();
			image.setLikeCount(likeCount);
			
			image.getLikes().forEach((like) -> {
				if (like.getUser().getId() == principalId) {
					image.setLikeState(true);
				}
			});
		});
		
		return images;
	}
	
	@Transactional(readOnly = true)
	public List<ExploreRespDto> 탐색이미지(int principalId) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT i.id imageId, i.userId, i.postImageUrl, count(l.imageId) likeCount ");
		sb.append("FROM image i ");
		sb.append("INNER JOIN likes l ");
		sb.append("ON i.id = l.imageId ");
		sb.append("WHERE i.userId != ? ");
		sb.append("GROUP BY i.id ORDER BY count(l.imageId) DESC");
		
		Query query = em.createNativeQuery(sb.toString())
						.setParameter(1, principalId);
		
		JpaResultMapper mapper = new JpaResultMapper();
		List<ExploreRespDto> dtos = mapper.list(query, ExploreRespDto.class);
		return dtos;
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
