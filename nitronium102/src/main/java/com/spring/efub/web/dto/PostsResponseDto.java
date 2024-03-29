package com.spring.efub.web.dto;

import com.spring.efub.web.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {
	private Long id;
	private String title;
	private String content;
	private String author;

	// 모든 필드를 가진 생성자가 필요하지 않으므로 DTO는 ENTITY를 받아 처리
	public PostsResponseDto(Posts entity){
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.author = entity.getAuthor();
	}
}
