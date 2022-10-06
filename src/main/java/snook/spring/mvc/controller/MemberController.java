package snook.spring.mvc.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String join(HttpSession sess) {  // url만 찍으면 호출할 수 있게 하기 때문에 get 방식
		String returnPage = "join/join";
		
		if(sess.getAttribute("m") != null) 
			returnPage = "redirect:/myinfo";
		
		return returnPage;
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
	
	
	//로그인 상태가 아니라면 -> redirect:/login
	//로그인 상태이면 -> "join/myinfo
	@GetMapping("/myinfo")
	public String myinfo(Model m, HttpSession sess) {

		String returnPage = "join/myinfo";
		
		if(sess.getAttribute("m") != null) {
			MemberVO mvo = (MemberVO) sess.getAttribute("m");
			m.addAttribute("mbr", msrv.readOneMember(mvo.getUserid()));
		} else {
			returnPage = "redirect:/login";
		}
		return returnPage;
	}		
		
	//아이디 중복 검사 - REST api 이용
	@ResponseBody
	@GetMapping("/checkuid")
	public String checkuid(String uid) {
	
		String result = "잘못된 방법으로 호출하였습니다!";
		
		if (uid != null || !uid.equals("")) {
			result = msrv.checkUid(uid);
		}
		
		return result;
		
	}
	
	
	

}
