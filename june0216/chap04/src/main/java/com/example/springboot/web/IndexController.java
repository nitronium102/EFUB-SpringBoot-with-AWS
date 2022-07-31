package com.example.springboot.web;


import com.example.springboot.config.LoginUser;
import com.example.springboot.config.auth.dto.SessionUser;
import com.example.springboot.service.posts.PostsService;
import com.example.springboot.web.dto.PostsResponseDto;
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
	public String index(Model model, @LoginUser SessionUser user)
	{
		model.addAttribute("posts", postsService.findAllDesc());
		//SessionUser user = (SessionUser) httpSession.getAttribute("user");//로그인 성공 시 user값을 가져올 수 있다.
		if(user != null)//세션에 저장된 값이 없으면 model에 아무런 값이 없는 형태이니 로그인 버튼이 보이게 될 것임
		{
			model.addAttribute("userName", user.getName());
		}
		return "index";
	}


	@GetMapping("posts/save")
	public String postsSave()
	{
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
