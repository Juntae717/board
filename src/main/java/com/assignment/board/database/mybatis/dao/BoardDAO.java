package com.assignment.board.database.mybatis.dao;

import com.assignment.board.database.mybatis.dto.BoardDTO;

import java.util.List;

public interface BoardDAO {
    void insertBoard(BoardDTO boardDTO);
    List<BoardDTO> selectBoardList();
    BoardDTO selectBoardDetail(BoardDTO boardDTO);
}
