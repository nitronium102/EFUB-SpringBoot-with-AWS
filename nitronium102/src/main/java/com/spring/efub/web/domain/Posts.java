package com.spring.efub.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // 절대 setter 메소드는 만들지 않음
@NoArgsConstructor // 기본 생성자 자동 추가
@Entity
public class Posts {

	@Id // pk
	@GeneratedValue(strategy = GenerationType.IDENTITY) // pk의 생성 규칙
	private Long id;

	@Column(length = 500, nullable = false) // 기본값 이외에 추가로 변경이 필요한 옵션이 있으면 사용
	private String title;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	private String author;

	@Builder
	public Posts(String title, String content, String author) {
		this.title = title;
		this.content = content;
		this.author = author;
	}

}
