package snook.spring.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snook.spring.mvc.dao.BoardDAO;
import snook.spring.mvc.vo.BoardVO;

@Service("bsrv")
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO bdao;
	
	@Override
	public boolean newBoard(BoardVO bvo) {
		boolean isInsert = false;
		if(bdao.insertBoard(bvo)>0) isInsert = true;
		
		return isInsert;
	}

	@Override
	public List<BoardVO> readBoard(String fkey, String fval, int snum) {

		return bdao.selectBoard(fkey, fval, snum);
	}

	
	@Override
	public BoardVO readOneBoard(String bno) {
		return bdao.selectOneBoard(bno);
	}

	
	@Override
	public int readCountBoard(String fkey, String fval) {
		// TODO Auto-generated method stub
		return bdao.readCountBoard(fkey, fval);
	}

}
