package com.example.springboot.web.dto;


import com.example.springboot.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {

	private Long id;
	private String title;
	private String content;
	private String author;

	public PostsResponseDto(Posts entity)//모든 필드를 가진 생성자가 필요하지 않으므로 entity를 받아서 처리
	{
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.author = entity.getAuthor();
	}

}
