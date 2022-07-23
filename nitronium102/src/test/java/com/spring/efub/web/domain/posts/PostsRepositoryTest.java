package com.spring.efub.web.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest // default = H2 DB
public class PostsRepositoryTest {

	@Autowired
	PostsRepository postsRepository;

	@AfterEach // 단위 테스트가 끝날 때마다 수행되는 메소드를 지정. 보통은 배포 전 전체 테스트를 수행할 때 테스트 간 데이터 침범을 막기 위해 사용
	public void cleanup() {
		postsRepository.deleteAll();
	}

	@Test
	@DisplayName("게시글 저장 후 조회")
	public void saveAndGetPost() {
		// given
		String title = "테스트 게시글";
		String content = "테스트 본문";

		postsRepository.save(Posts.builder()
				.title(title)
				.content(content)
				.author("txt@bighit.net")
				.build());

		// when
		List<Posts> postsList = postsRepository.findAll();

		// then
		Posts posts = postsList.get(0);
		assertThat(posts.getTitle()).isEqualTo(title);
		assertThat(posts.getContent()).isEqualTo(content);
	}
}
