package snook.spring.mvc.dao;

import snook.spring.mvc.vo.MemberVO;

public interface MemberDAO {

    int insertMember(MemberVO mvo);

    MemberVO selectOneMember(String userid);

    int selectOneMember(MemberVO m);
    
}
