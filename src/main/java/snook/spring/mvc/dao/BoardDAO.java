package snook.spring.mvc.dao;

import java.util.List;

import snook.spring.mvc.vo.BoardVO;


public interface BoardDAO {

	int insertBoard(BoardVO bvo);

	List<BoardVO> selectBoard(String fkey, String fval, int snum);

	BoardVO selectOneBoard(String bno);

	int readCountBoard(String fkey, String fval);

	int deleteBoard(String bno);

	int updateBoard(BoardVO bvo);

}
