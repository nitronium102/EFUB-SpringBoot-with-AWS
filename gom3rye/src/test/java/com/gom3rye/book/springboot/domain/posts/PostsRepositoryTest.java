package com.gom3rye.book.springboot.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@SpringBootTest // H2 데이터베이스를 자동으로 실행해준다.
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @AfterEach // JUnit에서 단위 테스트가 끝날때마다 수행되는 메서드를 지정, 배포 전 전체 테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용한다.
    public void cleanUp()
    {
        postsRepository.deleteAll();
    }
    @Test
    public void 게시글저장_불러오기(){
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder() // 테이블 posts에 insert/update 쿼리를 실행한다.
                // id값이 있다면 update가, 없다면 insert 쿼리가 실행된다.
                .title(title)
                .content(content)
                .author("gom3rye@gmail.com")
                .build());
        // when
        List<Posts> postsList = postsRepository.findAll(); // 테이블 posts에 있는 모든 데이터를 조회해오는 메소드

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }
}