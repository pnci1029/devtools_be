package com.example.demo.dto;

import com.example.demo.entity.ArticleEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class ArticleResponseDto {

    private Long articleId;

    private String title;

    private String content;

    private String username;

    private Enum category;

    private Boolean isMyArticles= true ;

    private String createAt;

    public ArticleResponseDto(ArticleEntity articleEntity) {
        this.articleId = articleEntity.getId();
        this.title = articleEntity.getTitle();
        this.content = articleEntity.getContent();
        this.username = articleEntity.getUserName();
        this.category = articleEntity.getCategory();
        this.isMyArticles = Boolean.TRUE;
        this.createAt = articleEntity.getCreateAt();
    }
}
