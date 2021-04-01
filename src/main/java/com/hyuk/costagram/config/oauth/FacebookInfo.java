package com.hyuk.costagram.config.oauth;

import java.util.Map;

public class FacebookInfo extends OAuth2UserInfo {

	public FacebookInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getId() {
		return (String)attributes.get("id");
	}

	@Override
	public String getName() {
		return (String)attributes.get("name");
	}

	@Override
	public String email() {
		return (String)attributes.get("email");
	}

	@Override
	public String getImageUrl() {
		return null;
	}

	@Override
	public String getUsername() {
		return "Facebook_" + getId();
	}

}
