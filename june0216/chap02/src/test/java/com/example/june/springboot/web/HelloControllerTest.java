package com.example.june.springboot.web;

import com.example.june.springboot.web.HelloController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 내부에 @ExtendWith(SpringExtension.class)가 적용되어 있기 때문에 테스트를 진행할 때 다른 실행자를 실행시키는 것이 가능하다.
@WebMvcTest(controllers = HelloController.class) // Web(Spring MVC)에 집중할 수 있는 애너테이션.
public class HelloControllerTest {
    @Autowired//스프링이 관리하는 빈을 주입받는다.
    private MockMvc mvc; // 스프링 MVC 테스트의 시작점, 웹 API를 테스트할 때 사용(HTTP GET, POST 등)

    @Test
    public void hello가_리턴된다() throws Exception
    {
        String hello = "hello";

        mvc.perform(get("/hello"))//MockMvc를 통해 /hello 주소로 HTTP GET요청
                //체이닝이 지원되어 계속 아래와 같이 여러 검증 기능을 이어서 선언 가능
                .andExpect(status().isOk()) // mvc.perform의 결과 검증, HTTP Header의 Status를 검증한다.(여기선 200인지 검증)
                .andExpect(content().string(hello));//응답 본문 내용 검증 (여기선 hello를 리턴하기 때문에 이 값이 맞는지 검증)
    }

    @Test
    public void helloDto가_리턴된다() throws Exception
    {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name)//API 테스트할 때 사용될 요청 파라미터를 설정, String만 가능
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))//jsonPath는 응답값을 필드별로 검증할 수 있는 메소드, $를 기준으로 필드명을 명시
                .andExpect(jsonPath("$.amount", is(amount)));
    }

}

//[에러 1] = JDK 빌드 도구가 Gradle로 설정되어 있는 경우 -> 이 경우 보통 JDK 빌드 도구가 Gradle로 설정되어 있는 경우이다. IntelliJ IDEA로 변경
//의존성끼리는 버전을 맞춰야 하는 문제