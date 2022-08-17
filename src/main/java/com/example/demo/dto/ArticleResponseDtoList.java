package com.example.demo.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ArticleResponseDtoList {

    private String LoginUserName;

    private List<ArticleResponseDto> responseArticles;

    public ArticleResponseDtoList(String LoginUserName, List<ArticleResponseDto> articleResponseDtos) {
        this.LoginUserName = LoginUserName;
        this.responseArticles = articleResponseDtos;
    }
}
