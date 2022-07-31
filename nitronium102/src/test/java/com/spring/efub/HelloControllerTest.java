package com.spring.efub;

import com.spring.efub.web.HelloController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class) // 스프링 부트 테스트와 JUnit 사이에 연결자
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

	@Autowired // Bean 주입
	private MockMvc mvc; // 웹 API 테스트(GET, POST 등 API 테스트)

	@Test
	public void hello가_리턴된다() throws Exception {
		String hello = "hello";

		mvc.perform(get("/hello"))// mockMVC를 통해 /hello 주소로 GET 요청을 보냄
			.andExpect(status().isOk())		// perform의 결과를 검증 -> HTTP header의 status를 검증
			.andExpect(content().string(hello)); // 응답 본문의 내용을 검증
	}

	@Test
	public void helloDto가_리턴된다() throws Exception {
		String name = "hello";
		int amount = 1000;

		mvc.perform(
			get("/hello/dto")
				.param("name", name) // parameter test : string값만 허용
				.param("amount", String.valueOf(amount)
				)
		).andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is(name))) // jsonPath : json 응답값을 필드별로 검증
			.andExpect(jsonPath("$.amount", is(amount)));
	}
}
