package com.fastcampus.ch4.dao;


import com.fastcampus.ch4.domain.CommentDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentDaoImpl implements CommentDao {

    @Autowired
    private SqlSession session;
    private static String namespace = "com.fastcampus.ch4.dao.CommentMapper.";


    // #region delete
    @Override
    public int deleteAll(Integer bno) throws Exception {
        return session.delete(namespace+"deleteAll",bno);
    }

    @Override
    public int delete(Integer cno, String commenter) throws Exception {
        Map map = new HashMap();
        map.put("cno", cno);
        map.put("commenter",commenter);
        return session.delete(namespace+"delete",map);
    }
    // #endregion

    @Override
    public
        // #region select
    int count(Integer bno) throws Exception {
        return session.selectOne(namespace+"count",bno);
    }

    @Override
    public List<CommentDto> selectAll(Integer bno) throws Exception {
        return session.selectList(namespace+ "selectAll",bno);
    }

    @Override
    public CommentDto select(Integer cno) throws Exception {
        return session.selectOne(namespace+"select",cno);
    }

    // #endregion

    @Override
    public int insert(CommentDto commentDto) throws Exception {
        return session.insert(namespace+"insert",commentDto);
    }
    @Override
    public int update(CommentDto commentDto) throws Exception {
        return session.update(namespace+"update",commentDto);
    }

    @Override
    public int updateCommentCnt(Integer bno, int cnt){
        Map map = new HashMap();
        map.put("bno", bno);
        map.put("cnt",cnt);
        return session.update("updateCommentCnt"+namespace,map);
    }



}
