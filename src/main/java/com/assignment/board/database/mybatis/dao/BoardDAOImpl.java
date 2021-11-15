package com.assignment.board.database.mybatis.dao;

import com.assignment.board.database.mybatis.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardDAOImpl implements BoardDAO {
    private final SqlSessionTemplate sqlSession;

    @Override
    public void insertBoard(BoardDTO boardDTO) {
        sqlSession.selectOne("BoardDAO.insertBoard");
    }

    @Override
    public List<BoardDTO> selectBoardList() {
        return sqlSession.selectOne("BoardDAO.selectBoardList");
    }

    @Override
    public BoardDTO selectBoardDetail(BoardDTO boardDTO) {
        return sqlSession.selectOne("BoardDAO.selectBoardDetail");
    }
}