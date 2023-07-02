package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.SearchCondition;

import java.util.List;
import java.util.Map;

public interface BoardDao {
    // region Select
    int count() throws Exception;

    BoardDto select(Integer bno) throws Exception;

    List<BoardDto> selectAll() throws Exception;

    List<BoardDto> selectPage(Map map) throws Exception;

    // region delete
    int deleteAll() throws Exception;

    int delete(Integer bno, String writer) throws Exception;

    // region insert
    int insert(BoardDto dto);

    // region update
    int update(BoardDto dto);

    int updateCommentCnt(Integer bno, int cnt);

    int increaseViewCnt(Integer bno);

    List<BoardDto> searchSelectPage(SearchCondition sc);

    int searchResultCount(SearchCondition sc);
}
