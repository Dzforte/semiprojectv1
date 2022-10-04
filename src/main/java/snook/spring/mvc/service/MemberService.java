package snook.spring.mvc.service;

import snook.spring.mvc.vo.MemberVO;

public interface MemberService {

	boolean newMember(MemberVO mvo);

	MemberVO readOneMember();

	boolean checkLogin(MemberVO mvo);
	
	
}
