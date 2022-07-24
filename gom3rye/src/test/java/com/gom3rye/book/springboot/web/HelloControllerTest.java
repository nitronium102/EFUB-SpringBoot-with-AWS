package com.gom3rye.book.springboot.web;

import com.gom3rye.book.springboot.web.HelloController;
import org.junit.jupiter.api.Test; // Junit4에서 5로 바뀌면서 junit -> junit.jupiter.api
import org.junit.jupiter.api.extension.ExtendWith; // Junit4에서 5로 바뀌면서 RunWith -> ExtendWith
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension; // Junit4에서 5로 바뀌면서 junit4 -> junit.jupiter, SpringRunner -> SpringExtension
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class) // 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 한다.
@WebMvcTest(controllers = HelloController.class) // @Controller, @ControllerAdvice 사용 가능, @Service, @Component, @Repository 사용 불가
public class HelloControllerTest {

    @Autowired // 스프링이 관리하는 Bean 주입 받기
    private MockMvc mvc; // 웹 API를 테스트 할 때 사용, 스프링 MVC 테스트의 시작점, HTTP GET, POST 등 API 테스트 가능

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))          // MockMvc 통해서 /hello 주소로 HTTP GET 요청을 한다.
                .andExpect(status().isOk())	          // mvc.perform의 결과를 검증. HTTP Status를 검증. (여기선 200 ok 인지 검증)
                .andExpect(content().string(hello));  // mvc.perform의 결과를 검증. 응답 본문의 내용 검증. (Controller의 리턴값이 "hello"가 맞는지 검증)
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                        get("/hello/dto")
                                .param("name", name) // param : API 테스트할 때 사용될 요청 파라미터를 설정
                                .param("amount", String.valueOf(amount)) // 단 String 값만 허용되므로 숫자, 날짜 등의 데이터를 등록 할 때는 String으로 변환 필요
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) // jsonPath : JSON응답값을 필드별로 검증, $를 기준으로 필드명 명시한다.
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}