package snook.spring.mvc.dao;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import snook.spring.mvc.vo.BoardVO;

@Repository("bdao")
public class BoardDAOImpl implements BoardDAO {
	
	//@AutoWired   // bean 태그에 정의한 경우 생략 가능
	private JdbcTemplate jdbcTemplate;	
	
	private SimpleJdbcInsert simpleInsert;
	
	public BoardDAOImpl(DataSource dataSource) {
		simpleInsert = new SimpleJdbcInsert(dataSource).withTableName("board").usingColumns("title", "userid", "contents");
	}
	
	@Override
	public int insertBoard(BoardVO bvo) {
		
		SqlParameterSource params = new BeanPropertySqlParameterSource(bvo);		
		return simpleInsert.execute(params);
		
	}
	

}
