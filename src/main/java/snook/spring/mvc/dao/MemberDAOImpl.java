package snook.spring.mvc.dao;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleInsert;	
	private NamedParameterJdbcTemplate jdbcNameTemplate;

	// private RowMapper<MemberVO> memberMapper = BeanPropertyRowMapper.newInstance(MemberVO.class);
		
	
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
		
//		return jdbcNameTemplate.queryForObject(sql, Collections.emptyMap(), memberMapper);
		
		RowMapper<MemberVO> memberMapper = (rs, num) -> {
			MemberVO m = new MemberVO();
			
			m.setUserid(rs.getString("userid"));
			m.setName(rs.getString("name"));
			m.setEmail(rs.getString("email"));
			m.setRegdate(rs.getString("regdate"));
			return m;
			
		};
		
		return jdbcTemplate.queryForObject(sql, null, memberMapper);
				
	}
	
	/*
	// 콜백 메서드 정의 : mapRow
	private class MemberRowMapper implements RowMapper<MemberVO> {

		@Override
		public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			MemberVO m = new MemberVO();
			
			m.setUserid(rs.getString("userid"));
			m.setName(rs.getString("name"));
			m.setEmail(rs.getString("email"));
			m.setRegdate(rs.getString("regdate"));
			return m;
		}
		
	} */	

	
	@Override
	public int selectOneMember(MemberVO m) {
		String sql = "select count(mno) cnt from member where userid = ? and passwd = ? ";
		
		Object[] params = {m.getUserid(), m.getPasswd()};
		
		return jdbcTemplate.queryForObject(sql, params, Integer.class); // 실행한 값이 정수로 넘어옴 (mapper 필요 없음), *객체로 넘길때는 mapper가 필요함
	}
	
	
}
