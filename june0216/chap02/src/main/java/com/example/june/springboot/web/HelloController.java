package com.example.june.springboot.web;

import com.example.june.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController//컨트롤러를 JSON을 반환하는 컨트롤러로 만들어준다.
public class HelloController {

    @GetMapping("/hello") //HTTP Method인 GET의 요청을 받을 수 있는 API를 만들어준다. , "/hello"로 요청이 오면 문자열 hello를 반환하는 기능
    public String hello()
    {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloResponseDto(@RequestParam("name") String name, @RequestParam("amount") int amount)
    {
        //외부에서 [@RequestParam("name") ]name으로  넘긴 파라미터를 메소드 파라미터 name에 저장한다.
        return new HelloResponseDto(name, amount);
    }
}
