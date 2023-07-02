package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.SearchCondition;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class BoardDaoImpl implements BoardDao {
    @Autowired
    SqlSession session;

    String namespace = "com.fastcampus.ch4.dao.BoardMapper.";

    // region Select
    @Override
    public int count() throws Exception {
        return session.selectOne(namespace+"count");
    }
     @Override
     public BoardDto select(Integer bno) throws Exception {
        return session.selectOne(namespace+ "select",bno);
    }

    @Override
    public List<BoardDto> selectAll() throws Exception {
        return session.selectList(namespace+"selectAll");
    }

    @Override
    public List<BoardDto> selectPage(Map map) throws Exception {
        return session.selectList(namespace+"selectPage",map);
    }

    // endregion

    // region delete
    @Override
    public int deleteAll() throws Exception {
        return session.delete(namespace+"deleteAll");
    }

    @Override
    public int delete(Integer bno, String writer) throws Exception {
        Map map = new HashMap();
        map.put("bno",bno);
        map.put("writer",writer);
        return session.delete(namespace+"delete",map);
    }

    // endregion

    // region insert
    @Override
    public int insert(BoardDto dto){
        return session.insert(namespace+"insert",dto);
    }

    // endregion

    // region update
    @Override
    public int update(BoardDto dto){
        return session.update(namespace+"update",dto);
    }
    @Override
    public int updateCommentCnt(Integer bno, int cnt){
        Map map = new HashMap();
        map.put("bno",bno);
        map.put("cnt",cnt);
        return session.update(namespace+"updateCommentCnt",map);
    }

    @Override
    public int increaseViewCnt(Integer bno) {
        return session.update(namespace+"increaseViewCnt",bno);
    }

    // endregion
    @Override
    public List<BoardDto> searchSelectPage(SearchCondition sc) {
        return session.selectList(namespace+"searchSelectPage",sc);
    }

    @Override
    public int searchResultCount(SearchCondition sc) {
        return session.selectOne(namespace+"searchResultCount",sc);
    }


}

