package com.assignment.board.Board.controller;

import com.assignment.board.Board.service.BoardService;
import com.assignment.board.database.mybatis.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/insertBoard")
    @ResponseStatus(value = HttpStatus.OK)
    public String insertBoard(BoardDTO boardDTO) {
        return boardService.insertBoard(boardDTO);
    }

    @GetMapping("/selectBoardList")
    @ResponseStatus(value = HttpStatus.OK)
    public List<BoardDTO> selectBoardList() {
        return boardService.selectBoardList();
    }

    @GetMapping("/selectBoardDetail")
    @ResponseStatus(value = HttpStatus.OK)
    public BoardDTO selectBoardDetail(BoardDTO boardDTO) {
        return boardService.selectBoardDetail(boardDTO);
    }
}