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

    /**
     * FUNCTION :: 게시판 등록
     * @param boardDTO
     */
    @Override
    public void insertBoard(BoardDTO boardDTO) {
        sqlSession.selectOne("BoardDAO.insertBoard");
    }

    /**
     * FUNCTION :: 게시판 전체 목록 조회
     * @return
     */
    @Override
    public List<BoardDTO> selectBoardList() {
        return sqlSession.selectOne("BoardDAO.selectBoardList");
    }

    /**
     * FUNCTION :: 게시판 상세 조회
     * @param boardDTO
     * @return
     */
    @Override
    public BoardDTO selectBoardDetail(BoardDTO boardDTO) {
        return sqlSession.selectOne("BoardDAO.selectBoardDetail");
    }
}