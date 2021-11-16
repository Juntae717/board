package com.assignment.board.Board.service;

import com.assignment.board.database.mybatis.dao.BoardDAO;
import com.assignment.board.database.mybatis.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardDAO boardDAO;

    /**
     * FUNCTION :: 게시판 등록
     * @param boardDTO
     * @return
     */
    @Override
    public String insertBoard(BoardDTO boardDTO) {
        boardDAO.insertBoard(boardDTO);
        return "Success Insert";
    }

    /**
     * FUNCTION :: 게시판 전체 목록 조회
     * @return
     */
    @Override
    public List<BoardDTO> selectBoardList() {
        return boardDAO.selectBoardList();
    }

    /**
     * FUNCTION :: 게시판 상세 조회
     * @param boardDTO
     * @return
     */
    @Override
    public BoardDTO selectBoardDetail(BoardDTO boardDTO) {
        return boardDAO.selectBoardDetail(boardDTO);
    }
}