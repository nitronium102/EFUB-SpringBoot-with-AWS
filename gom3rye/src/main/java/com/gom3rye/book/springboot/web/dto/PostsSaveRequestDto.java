package com.gom3rye.book.springboot.web.dto;

import com.gom3rye.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class PostsSaveRequestDto { // View를 위한 클래스로 자주 변경됨 (엔티티 클래스는 DB와 맞닿아 있는 핵심 클래스로 dto를 따로 둔다)
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author)
    {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity()
    {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}