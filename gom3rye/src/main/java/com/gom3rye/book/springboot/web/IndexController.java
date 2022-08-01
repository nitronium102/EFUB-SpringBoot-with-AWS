package com.gom3rye.book.springboot.web;

import com.gom3rye.book.springboot.service.posts.PostsService;
import com.gom3rye.book.springboot.web.dto.PostsResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }
    @GetMapping("/posts/save") // 머스테치로 /posts/save 페이지 주소 만들어주었으니까 이 주소에 해당하는 컨트롤러 생성
    // 페이지에 관련된 컨트롤러는 모두 IndexController를 사용한다.
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update{id}")
    public String postsUpdate(@PathVariable Long id, Model model)
    {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }

}
