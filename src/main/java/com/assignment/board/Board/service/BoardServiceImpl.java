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

    @Override
    public String insertBoard(BoardDTO boardDTO) {
        boardDAO.insertBoard(boardDTO);
        return "Success Insert";
    }

    @Override
    public List<BoardDTO> selectBoardList() {
        return boardDAO.selectBoardList();
    }

    @Override
    public BoardDTO selectBoardDetail(BoardDTO boardDTO) {
        return boardDAO.selectBoardDetail(boardDTO);
    }
}
