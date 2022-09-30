package snook.spring.mvc.dao;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import snook.spring.mvc.vo.BoardVO;

@Repository("bdao")
public class BoardDAOImpl implements BoardDAO {

	
	private JdbcTemplate jdbcTemplate;	
	

	
	@Override
	public int insertBoard(BoardVO bvo) {
		
		String sql = "insert into board (title, userid, contents)" + "values (?,?,?)";
		
		Object[] params = new Object[] {
				bvo.getTitle(), bvo.getUserid(), bvo.getContents()
		};
		
		return jdbcTemplate.update(sql, params);
	}
	

}
