package snook.spring.mvc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import snook.spring.mvc.vo.BoardVO;

@Repository("bdao")
public class BoardDAOImpl implements BoardDAO {
	
	@Autowired   // bean 태그에 정의한 경우 생략 가능
	private JdbcTemplate jdbcTemplate;		
	private SimpleJdbcInsert simpleInsert;
	private NamedParameterJdbcTemplate jdbcNamedTemplate;
	
	private RowMapper<BoardVO> boardMapper = BeanPropertyRowMapper.newInstance(BoardVO.class);
		
	public BoardDAOImpl(DataSource dataSource) {
		simpleInsert = new SimpleJdbcInsert(dataSource).withTableName("board").usingColumns("title", "userid", "contents");
		jdbcNamedTemplate = new NamedParameterJdbcTemplate(dataSource);
		
	}
	
	@Override
	public int insertBoard(BoardVO bvo) {
		
		SqlParameterSource params = new BeanPropertySqlParameterSource(bvo);		
		return simpleInsert.execute(params);
		
	}
	
	// 동적 질의문
	// 조건에 따라 실행할 질의문의 형태가 바뀌는 것
	// 제목으로 검색 : select * from board where title = ?
	// 작성자로 검색 : select * from board where userid = ?
	// 본문으로 검색 : select * from board where contents = ?
	// --> select * from ? board where ? = ?  ---> 이렇게 하면 실행 안됨...
	// 테이블명, 컬럼명은 매개변수화할 수 없음
	
	
	@Override
	public List<BoardVO> selectBoard(String fkey, String fval, int snum) {
		
		StringBuilder sql = new StringBuilder();	
		
		sql.append("select bno,title,userid,regdate,views from board ");
		if (fkey.equals("title")) sql.append(" where title like :fval ");
		else if (fkey.equals("userid")) sql.append(" where userid like :fval ");
		else if (fkey.equals("contents")) sql.append(" where contents like :fval ");
		
		sql.append(" order by bno desc limit :snum, 25");		
		
		Map<String, Object> params = new HashMap<>();		
		
		params.put("snum", snum);		
		params.put("fval", "%" + fval + "%");
		
		return jdbcNamedTemplate.query(sql.toString(), params, boardMapper);
		
		}

	@Override
	   public BoardVO selectOneBoard(String bno) {
  
		// 본문 글의 조회수 증가시키기. 
		String sql = " update board set views = views + 1 " + " where bno = ? ";
        Object[] param = {bno};        
        jdbcTemplate.update(sql, param);
               
        //본문글 가져오기 
        sql = "select bno,title,userid,regdate,views,contents from board where bno=?";
        return jdbcTemplate.queryForObject(sql, param, boardMapper);
    }

	@Override
	public int readCountBoard(String fkey, String fval) {
		
		StringBuilder sql = new StringBuilder();
				
		sql.append("select ceil(count(bno)/25) pages from board");

		if (fkey.equals("title")) sql.append(" where title like :fval");
		else if (fkey.equals("title")) sql.append(" where userid like :fval");
		else if (fkey.equals("title")) sql.append(" where contents like :fval");
		
		Map<String, Object> params = new HashMap<>();		

		params.put("fval", "%" + fval + "%");
		
		return jdbcNamedTemplate.queryForObject(sql.toString(), params, Integer.class);
              
	}

	@Override
	public int deleteBoard(String bno) {
		
		String sql = " delete from board where bno = ? ";
        Object[] param = new Object[] {bno};        
        return jdbcTemplate.update(sql, param);
		
	}

    @Override
    public int updateBoard(BoardVO bvo) {
        // 제목, 본문, 수정한 날짜/시간을 수정함
        String sql = "update board set title= :title, contents= :contents, regdate= current_timestamp() where bno= :bno";

        Map<String, Object> params = new HashMap<>();
        params.put("title", bvo.getTitle());
        params.put("contents", bvo.getContents());
        params.put("bno", bvo.getBno());
                
        return jdbcNamedTemplate.update(sql, params);
    }

}
