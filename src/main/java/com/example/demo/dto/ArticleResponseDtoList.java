package com.example.demo.dto;

import com.example.demo.dto.ArticleResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleResponseDtoList {

    private String LoginUserName;

    private List<ArticleResponseDto> articleResponseDtos;

    public ArticleResponseDtoList(String LoginUserName, List<ArticleResponseDto> articleResponseDtos) {
        this.LoginUserName = LoginUserName;
        this.articleResponseDtos = articleResponseDtos;
    }
}
