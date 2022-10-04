package snook.spring.mvc.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import snook.spring.mvc.service.MemberService;
import snook.spring.mvc.vo.MemberVO;

@Controller
public class MemberController {

	
	// new 연산자 만들 필요 없이 객체를 가져오겠다는 뜻 
	
	@Autowired
	private MemberService msrv;
	
	
	
	// 로그 유형 : trace, debug, info, warn, error
	protected Logger LOGGER = LoggerFactory.getLogger(getClass()); // 맨 위에 항상 작성
	
	
	@GetMapping("/join")
	public String join() {  // url만 찍으면 호출할 수 있게 하기 때문에 get 방식
		LOGGER.info("join호출!");
		return "join/join";
	}

	@PostMapping("/join")
	public String joinok(MemberVO mvo) {  // 회원가입이라서 post 방식 추가
				
		LOGGER.info("joinok 호출! {} ", mvo);
		
		//회원가입 저장
	
		if(msrv.newMember(mvo))
			LOGGER.info("회원가입 성공!!!");
			
		return "redirect:/login"; // 페이지가 다시 바뀌어야되니까 redirect
	}
	 
	
	@GetMapping("/login")
	public String login() {
		return "join/login";
	}

	@PostMapping("/login") // 로그인 처리
	public String loginok(MemberVO mvo, HttpSession sess) { 

		String returnPage = "join/lgnfail";
		
		if(msrv.checkLogin(mvo)) {
			sess.setAttribute("m", mvo); // 회원정보 세션에 저장
			returnPage = "redirect:/myinfo"; 
		}
		return returnPage;
		
		
	}

	@GetMapping("/logout")
	public String myinfo(HttpSession sess) {

		sess.invalidate(); // 모든 세션 제거
		return "redirect:/";
	}
	
	@GetMapping("/myinfo")
	public String myinfo(Model m) {

		m.addAttribute("mbr", msrv.readOneMember());
		
		return "join/myinfo";
	}
}
