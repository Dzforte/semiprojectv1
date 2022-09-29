package snook.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

	@GetMapping("/join")
	public String join() {  // url만 찍으면 호출할 수 있게 하기 때문에 get 방식
		return "join/join";
	}

	@PostMapping("/join")
	public String joinok() {  // 회원가입이라서 post 방식 추가
		return "redirect:/login"; // 페이지가 다시 바뀌어야되니까 redirect
	}
	
	
	@GetMapping("/login")
	public String login() {
		return "join/login";
	}

	@PostMapping("/login")
	public String loginok() {
		return "redirect:/myinfo";
	}
	
	@GetMapping("/myinfo")
	public String myinfo() {
		return "join/myinfo";
	}
}
