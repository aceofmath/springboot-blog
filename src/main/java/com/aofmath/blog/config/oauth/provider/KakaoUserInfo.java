package com.aofmath.blog.config.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

	private Map<String, Object> attributes;
	private Map<String, Object> properties_attributes;
	private Map<String, Object> kakao_account_attributes;
	
    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.properties_attributes = (Map)attributes.get("properties");
        this.kakao_account_attributes = (Map)attributes.get("kakao_account");
    }
	
    @Override
    public String getProviderId() {
        return (String) attributes.get("id").toString();
    }

    @Override
    public String getName() {
        return (String) properties_attributes.get("nickname");
    }

    @Override
    public String getEmail() {
        return (String) kakao_account_attributes.get("email");
    }

	@Override
	public String getProvider() {
		return "kakao";
	}

}
