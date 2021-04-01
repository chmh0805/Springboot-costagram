package com.hyuk.costagram.config.oauth;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.hyuk.costagram.config.auth.PrincipalDetails;
import com.hyuk.costagram.domain.user.User;
import com.hyuk.costagram.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService{
	private final UserRepository userRepository; 
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		return processOAuth2User(userRequest, oAuth2User);
	}
	
	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		String clientName = userRequest.getClientRegistration().getClientName();
		OAuth2UserInfo oAuth2UserInfo = null;
		
		switch (clientName) {
		case "Facebook":
			oAuth2UserInfo = new FacebookInfo(oAuth2User.getAttributes());
			break;
		default:
			break;
		}
		
		User userEntity = userRepository.findByUsername(oAuth2UserInfo.getUsername());
		if (userEntity == null) {
			UUID uuid = UUID.randomUUID();
			String encPassword = new BCryptPasswordEncoder().encode(uuid.toString());
			
			User user = User.builder()
					.username(oAuth2UserInfo.getUsername())
					.password(encPassword)
					.email(oAuth2UserInfo.email())
					.role("USER")
					.build();
			userEntity = userRepository.save(user);
			return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
		} else { // 최초 로그인이 아니면 (원래는 구글 정보가 변경될 수 있기 때문에 update를 해야됨)
			return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
		}
	}
}
