package com.example.demo.dto;

import com.example.demo.entity.ArticleEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class ArticleResponseDto {

    private Long id;

    private String title;

    private String content;

    private String username;

    private String category;

    private Boolean isMyArticles= true ;

    private String now;


    public ArticleResponseDto(ArticleEntity articleEntity) {
        this.id = articleEntity.getId();
        this.title = articleEntity.getTitle();
        this.content = articleEntity.getContent();
        this.username = articleEntity.getUserName();
        this.category = articleEntity.getUserName();
        this.isMyArticles = Boolean.TRUE;
        this.now = articleEntity.getCreateAt();
    }
}
