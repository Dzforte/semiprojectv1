package snook.spring.mvc.service;

import java.util.List;

import snook.spring.mvc.vo.BoardVO;

public interface BoardService {

	boolean newBoard(BoardVO bvo);
	
	List<BoardVO> readBoard();

	BoardVO readOneBoard(String bno);
	
}
