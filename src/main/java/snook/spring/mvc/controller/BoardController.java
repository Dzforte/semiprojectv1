package snook.spring.mvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import snook.spring.mvc.service.BoardService;
import snook.spring.mvc.utils.RecaptchaUtils;
import snook.spring.mvc.vo.BoardVO;

@Controller
public class BoardController {
	
	protected Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	// DI받을 변수가 둘 이상이므로 생성자로 DI받도록 재정의
	//@Autowired	private BoardService bsrv;	
	//@Autowired    private RecaptchaUtils grpc;
	
	private BoardService bsrv;
	private RecaptchaUtils grcp;
	
	@Autowired
	public BoardController(BoardService bsrv, RecaptchaUtils grcp) {
		this.bsrv = bsrv;
		this.grcp = grcp;
	}
	
	/* 페이징 처리 */
	/*  페이지당 게시물 수 perPage : 25 */
	/* 	총 페이지 수 : 전체 게시글 / 페이지당 게시물 수(25)   */
	/* 	총 페이지 수 pages : ceil(getTotalPage / perPage)  무조건 올림 ex) 2 = 50 / 25, 3 = 51 / 25  */
	
	/* 페이지별 읽어올 게시글 범위  */
	/*  총 게시글이 55건이라 할 때   */
	/*  1page : 1 ~ 25번째 게시글 가져옴 */
	/*  2page : 26 ~ 50번째 게시글 가져옴 */
	/*  3page : 51 ~ 75번째 게시글 가져옴 */
	/*  'i'page : m번째 ~ n번째 게시글 */
	/*  m = (i-1)*25 + 1 ~ i+24  ///  i가 0부터 시작한다면 +1은 뻄 */
	/*   */	
	
	@GetMapping("/list")
	public String list(Model m, String cpg, String fkey, String fval) {  // url만 찍으면 호출할 수 있게 하기 때문에 get 방식
	
		int perPage = 25;
		if (cpg == null || cpg.equals("")) cpg = "1";  // cpg가 null이면 첫 페이지가 나오게 1로 정의함
		if (fkey == null) fkey = "";
		int cpage = Integer.parseInt(cpg);
		int snum = (cpage - 1) * perPage;  // localhost:8080/list가 오류나는 이유 = cpg가 null값이라서...
		int stpgn = ((cpage - 1) / 10 ) * 10 + 1;  
		
		m.addAttribute("pages", bsrv.readCountBoard(fkey, fval));		
		m.addAttribute("bdlist", bsrv.readBoard(fkey, fval, snum));		// bdlist로 넘김 - html에서 ${}로 받음
		m.addAttribute("stpgn", stpgn);
		m.addAttribute("fqry", "&fkey="+ fkey +"&fval="+ fval);
		return "board/list";
		
	}

	@GetMapping("/view")
	public ModelAndView view(ModelAndView mv, String bno) {
		
		mv.setViewName("board/view");
		mv.addObject("bd", bsrv.readOneBoard(bno));		
		
		return mv;
				
	}

	@GetMapping("/write")
	public String write(HttpSession sess) {
		
		String returnPage = "board/write";

		if(sess.getAttribute("m") == null)
			returnPage = "redirect:/login";
		return returnPage;
	}
	
	//
	//# captcha 사용시 클라이언트가 생성한 키와
	//# 서버에 설정해 둔 (비밀)키등을
	//# google의 siteverify에서 비교해서 인증에 성공하면
	//# list로 redirect하고, 그렇치 않으면 다시 writhe로 return함
	//
	//# 질의를 위한 질의문자열을 작성
	//?secret=비밀키&response=클라이언트응답키
	//https://www.google.com/recaptcha/api/siteverify?secret=비밀키&response=콘솔에 있던 긴 문자열(info에 찍혔던 거)
	
	
	@PostMapping("/write")
	public String writeok(BoardVO bvo, String gcaptcha, RedirectAttributes rda) throws ParseException, IOException {
		
		// LOGGER.info(gcaptcha);

		String returnPage = "redirect:/write";
		
		if (grcp.checkCaptcha(gcaptcha)) {
			bsrv.newBoard(bvo);		
			returnPage = "redirect:/list?cpg=1";	
		} else {			
			rda.addFlashAttribute("bvo", bvo);
			rda.addFlashAttribute("msg", "자동가입방지 확인이 실패했어요!");		
		}		
		return returnPage;
	}

	@GetMapping("/del")
	public String delete(String bno) {
		
		bsrv.deleteBoard(bno);
		return "redirect:/list?cpg=1";
	}
	
	@GetMapping("/upd")
	public String modify(HttpSession sess, String bno, Model m) {
		
		String returnPage = "board/update";

		if(sess.getAttribute("m") == null)
			returnPage = "redirect:/login";
		else
			m.addAttribute("bd", bsrv.readOneBoard(bno));
		
		return returnPage;
	}
	
	@PostMapping("/upd")
	public String modifyOk(HttpSession sess, BoardVO bvo) {
		
		String returnPage = "redirect:/view?bno=" + bvo.getBno();

		if(sess.getAttribute("m") == null)
			returnPage = "redirect:/login";
		else
			bsrv.modifyBoard(bvo);
		
		return returnPage;
	}	
	
}
