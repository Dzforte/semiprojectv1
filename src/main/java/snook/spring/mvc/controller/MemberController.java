package snook.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

	@GetMapping("/join")
	public String join() {  // url만 찍으면 호출할 수 있게 하기 때문에 get 방식
		return "join/join";
	}

	@GetMapping("/login")
	public String login() {
		return "join/login";
	}

	@GetMapping("/myinfo")
	public String myinfo() {
		return "join/myinfo";
	}
}
