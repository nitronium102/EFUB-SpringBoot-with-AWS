package com.example.springboot.domain.posts;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest//H2 데이터베이스 자동으로 실행
public class PostsRepositoryTest {

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach//JUnit에서 단위 테스트가 끝날때마다 수행되는 메서드, 다음 테스트에 H2에 남아있는 데이터가 영향을 주지 않기 위해 삭제
    public void cleanUp()
    {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기()
    {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder() //id 값이 있다면 업데이트, 없다면 삽입
                .title(title)
                .content(content)
                .author("jiyun")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();//테이블의 모든 데이터를 조회

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }

    @Test
    public void BaseTimeEntity_등록()
    {
        //given
        LocalDateTime now = LocalDateTime.of(2022,7,24,0,0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());


        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>> createDate=" + posts.getCreatedDate() +
                ", modifiedDate="+posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }

}
