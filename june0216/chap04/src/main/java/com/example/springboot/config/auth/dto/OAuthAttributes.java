package com.example.springboot.config.auth.dto;


import com.example.springboot.domain.user.Role;
import com.example.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;
	private String picture;

	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture)
	{
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
		this.picture = picture;
	}

	//OAuthUser에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환해야만 한다.
	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes)
	{
		return ofGoogle(userNameAttributeName, attributes);
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes)
	{
		return OAuthAttributes.builder()
				.name((String) attributes.get("name"))
				.email((String) attributes.get("email"))
				.picture((String) attributes.get("picture"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
	}

	private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
		Map<String,Object> response = (Map<String,Object>)attributes.get("response");

		return OAuthAttributes.builder()
				.name((String)response.get("name"))
				.email((String)response.get("email"))
				.picture((String)response.get("profile_image"))
				.attributes(response)
				.nameAttributeKey(userNameAttributeName)
				.build();
	}

	public User toEntity()//처음 가입할 때
	{
		return User.builder()
				.name(name)
				.email(email)
				.picture(picture)
				.role(Role.GUEST) //기본 권한을 GUEST로 두기
				.build();
	}


}
