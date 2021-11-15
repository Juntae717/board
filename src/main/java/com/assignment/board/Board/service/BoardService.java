package com.assignment.board.Board.service;

import com.assignment.board.database.mybatis.dto.BoardDTO;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface BoardService {
    String insertBoard(BoardDTO boardDTO);
    List<BoardDTO> selectBoardList();
    BoardDTO selectBoardDetail(BoardDTO boardDTO);
}
