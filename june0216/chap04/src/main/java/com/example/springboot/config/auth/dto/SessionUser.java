package com.example.springboot.config.auth.dto;

import com.example.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
	//자바 시스템 내부에서 사용되는 Object 또는 Data를 외부의 자바 시스템에서도 사용할 수 있도록 바이트 형태로 데이터를 변환하는 기술
	//유저를 세션에 저장하기 위해서 직렬화 기능을 가진 dto를 따로 만들었다. -> User 안에서 직렬화를 한다면 다른 엔티티와의 관계에서 성능 이슈 생김
	private String name;
	private String email;
	private String picture;

	public SessionUser(User user)
	{
		this.name = user.getName();
		this.email = user.getEmail();
		this.picture = user.getPicture();
	}
}
