package com.gom3rye.book.springboot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/posts/save") // 머스테치로 /posts/save 페이지 주소 만들어주었으니까 이 주소에 해당하는 컨트롤러 생성
    // 페이지에 관련된 컨트롤러는 모두 IndexController를 사용한다.
    public String postsSave(){
        return "posts-save";
    }

}
