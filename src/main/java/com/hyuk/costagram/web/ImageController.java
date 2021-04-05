package com.hyuk.costagram.web;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyuk.costagram.config.auth.PrincipalDetails;
import com.hyuk.costagram.service.ImageService;
import com.hyuk.costagram.service.LikesService;
import com.hyuk.costagram.web.dto.CommonRespDto;
import com.hyuk.costagram.web.dto.explore.ExploreRespDto;
import com.hyuk.costagram.web.dto.image.ImageReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {

	private final ImageService imageService;
	private final LikesService likesService;
	
	@GetMapping({"/", "/image/feed"})
	public String feed() {
		return "image/feed";
	}
	
	// 주소 : /image?page=0
	@GetMapping({"/image"})
	public @ResponseBody CommonRespDto<?> image(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails, 
								@PageableDefault(size = 3, sort="id", direction=Sort.Direction.DESC) Pageable pageable) {

		return new CommonRespDto<>(1, imageService.피드이미지(principalDetails.getUser().getId(), pageable));
	}
	
	@GetMapping("/image/explore")
	public String explore(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		List<ExploreRespDto> dtos = imageService.탐색이미지(principalDetails.getUser().getId());
		model.addAttribute("dtos", dtos);
		return "image/explore";
	}
	
	@GetMapping("/image/upload")
	public String upload() {
		return "image/upload";
	}
	
	@PostMapping("/image")
	public String image(ImageReqDto imageReqDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		imageService.사진업로드(imageReqDto, principalDetails);
		
		return "redirect:/user/" + principalDetails.getUser().getId();
	}
	
	@PostMapping("/image/{imageId}/likes")
	public @ResponseBody CommonRespDto<?> like(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		likesService.좋아요(imageId, principalDetails.getUser().getId());
		return new CommonRespDto<>(1, null);
	}
	
	@DeleteMapping("/image/{imageId}/likes")
	public @ResponseBody CommonRespDto<?> unLike(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		likesService.싫어요(imageId, principalDetails.getUser().getId());
		return new CommonRespDto<>(1, null);
	}
}
