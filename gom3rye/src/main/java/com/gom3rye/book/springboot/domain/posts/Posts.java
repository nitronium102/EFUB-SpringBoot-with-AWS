package com.gom3rye.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// Entity 클래스에서는 절대 Setter 메소드를 만들지 않는다.

@Getter // 클래스 내 모든 필드의 Getter 메소드를 자동생성
@NoArgsConstructor // 기본 생성자 자동 추가
@Entity // 테이블과 링크될 클래스임을 나타냄

public class Posts {
    @Id // 해당 테이블의 PK 필드 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙 나타냄, GenerationType.IDENTITY 옵션으로 auto_increment 됌
    private Long id;

    @Column(length = 500, nullable = false) //
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
