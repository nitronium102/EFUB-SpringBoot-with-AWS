package com.efub.minju.springboot.domain.posts;

import com.efub.minju.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//Entity에서는 Setter 지양 -> 필드 값 변경이 필요한 경우 명확히 목적과 의도를 나타낼 수 있는 메소드를 추가해야 함.
// -> 생성자를 통해 최종값을 채운 후 DB에 삽입 Or 빌더 사용(빌더 사용시 어느 필드에 어떤 값을 채워야할지 명확히 인지할 수 있다.
//값 변경이 필요한 경우 해당 이벤트에 맞는 public 메소드를 호출하여 변경한다.
@Getter //클래스내 모든 필드의 Getter 메소드 생성
@NoArgsConstructor //기본 생성자 자동 추가
@Entity //테이블과 링크될 클래스임을 나타냄
public class Posts extends BaseTimeEntity {

    @Id //pk 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) //pk의 생성 규칙. GenerationType.IDENTITY 추가해야 auto_increment됨(in 스프링부트2.0)
    private Long id;

    @Column(length = 500, nullable = false) //optional(없어도 컬럼됨), 기본값 외에 추가로 변경이 필요한 경우에 사용
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder //빌더 패턴 클래스 생성
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
