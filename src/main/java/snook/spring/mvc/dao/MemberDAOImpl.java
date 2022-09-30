package snook.spring.mvc.dao;

import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import snook.spring.mvc.vo.MemberVO;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO {

	private SimpleJdbcInsert simpleInsert;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate jdbcNameTemplate;

	private RowMapper<MemberVO> memberMapper = BeanPropertyRowMapper.newInstance(MemberVO.class);
		
	
	public MemberDAOImpl(DataSource dataSource) {
		simpleInsert = new SimpleJdbcInsert(dataSource).withTableName("member").usingColumns("userid", "passwd", "name", "email");
	
		jdbcNameTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public int insertMember(MemberVO mvo) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(mvo);		
		return simpleInsert.execute(params);
	}

	@Override
	public MemberVO selectOneMember() {

		String sql = " select userid, name, email, regdate from member " + " where mno = 1 ";
		
		return jdbcNameTemplate.queryForObject(sql, Collections.emptyMap(), memberMapper);
	}
}
