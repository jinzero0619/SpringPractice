package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.CommentDto;

import java.util.List;

public interface CommentDao {
    // #region delete
    int deleteAll(Integer bno) throws Exception;

    int delete(Integer bno, String commenter) throws Exception;

    // #region select
    int count(Integer bno) throws Exception;

    List<CommentDto> selectAll(Integer cno) throws Exception;

    CommentDto select(Integer cno) throws Exception;

    int insert(CommentDto commentDto) throws Exception;

    int update(CommentDto commentDto) throws Exception;

    public int updateCommentCnt(Integer bno, int cnt) throws Exception;
}
