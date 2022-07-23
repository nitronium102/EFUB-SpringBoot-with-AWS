package com.efub.minju.springboot;

import com.efub.minju.springboot.web.HelloController;
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
@WebMvcTest(controllers = HelloController.class) // Controller 사용 가능(Service, Component, Repository 사용 불가)
public class HelloControllerTest {

    @Autowired // Bean 주입
    private MockMvc mvc; // 웹 API 테스트 시 사용(HTTP GET, POST 등)

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) //MockMvc 통해서 /hello 주소로 HTTP GET 요청 보냄
                .andExpect(status().isOk())	 //mvc.perform 검증. HTTP Status 검증. (여기선 200 ok 인지 검증)
                .andExpect(content().string(hello)); // mvc.perform 검증. 응답 본문의 내용 검증. (Controller의 리턴값이 "hello"가 맞는지)
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                .param("name", name) //param : 요청 파라미터 설정. 단 String 값만 허용되므로 다른 값 등록 할 때는 String 으로 변환 필요
                .param("amount", String.valueOf(amount))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) //jsonPath : JSON응답값을 필드별로 검증. $ 기준으로 필드명 명시
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}