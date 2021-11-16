package com.assignment.board.Board.service;

import com.assignment.board.database.mybatis.dto.BoardDTO;

import javax.transaction.Transactional;
import java.util.List;


public interface BoardService {
    @Transactional
    String insertBoard(BoardDTO boardDTO);

    @Transactional
    List<BoardDTO> selectBoardList();

    @Transactional
    BoardDTO selectBoardDetail(BoardDTO boardDTO);
}
