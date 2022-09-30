package snook.spring.mvc.dao;

import java.util.List;

import snook.spring.mvc.vo.BoardVO;


public interface BoardDAO {

	int insertBoard(BoardVO bvo);

	List<BoardVO> selectBoard();

}
