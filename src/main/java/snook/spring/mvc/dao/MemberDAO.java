package snook.spring.mvc.dao;

import java.util.List;

import snook.spring.mvc.vo.MemberVO;
import snook.spring.mvc.vo.Zipcode;

public interface MemberDAO {

    int insertMember(MemberVO mvo);

    MemberVO selectOneMember(String userid);

    int selectOneMember(MemberVO m);

	int selectCountUserid(String uid);

	List<Zipcode> selectZipcode(String string);
    
}
