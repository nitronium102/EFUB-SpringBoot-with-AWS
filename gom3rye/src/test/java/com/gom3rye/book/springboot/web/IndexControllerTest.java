package com.gom3rye.book.springboot.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void 메인페이지_로딩()
    {
        // when
        String body = this.restTemplate.getForObject("/", String.class);
        // HTML도 결국은 규칙이 있는 문자열이다. index.mustache에 해당 문자열이 포함되어 있는지 확인하면 된다.

        // then
        assertThat(body).contains("스프링 부트로 시작하는 웹 서비스");
    }
}

