package snook.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

	@GetMapping("/list")
	public String list() {  // url만 찍으면 호출할 수 있게 하기 때문에 get 방식
		return "board/list";
	}

	@GetMapping("/view")
	public String view() {
		return "board/view";
	}

	@GetMapping("/write")
	public String write() {
		return "board/write";
	}
	
}
