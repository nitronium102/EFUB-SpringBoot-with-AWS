package com.efub.minju.springboot.web;

import com.efub.minju.springboot.config.auth.dto.SessionUser;
import com.efub.minju.springboot.service.posts.PostsService;
import com.efub.minju.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) { // Model : 서버 템플릿 엔진에서 사용할 수 있는 객체 저장
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");// 로그인 성공시 값 가져오게

        if(user != null) { // 세션에 저장된 값이 없으면 로그인 버튼이 보임(model에 userName이 등록되지 않아서 값이 없는 상태라)
            model.addAttribute("userName", user.getName());
        }

        return "index";

    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }



}
