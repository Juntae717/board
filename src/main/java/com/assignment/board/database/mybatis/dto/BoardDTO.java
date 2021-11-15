package com.assignment.board.database.mybatis.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class BoardDTO {
    private int idx; // 게시판 고유식별자
    private String boardTitle; // 게시판 제목
    private String boardContent; // 게시판 내용
    private Date createdAt; // 게시판 생성일
    private Date updatedAt; // 게시판 수정일
}
