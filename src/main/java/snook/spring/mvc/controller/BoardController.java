package snook.spring.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import snook.spring.mvc.service.BoardService;
import snook.spring.mvc.vo.BoardVO;

@Controller
public class BoardController {
	
	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private BoardService bsrv;
	
	
	@GetMapping("/list")
	public String list(Model m) {  // url만 찍으면 호출할 수 있게 하기 때문에 get 방식
	
		m.addAttribute("bdlist", bsrv.readBoard());		// bdlist로 넘김 - html에서 ${}로 받음
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
	
	@PostMapping("/write")
	public String writeok(BoardVO bvo) {
		
		bsrv.newBoard(bvo);
		return "redirect:/list";
	}
	
}
