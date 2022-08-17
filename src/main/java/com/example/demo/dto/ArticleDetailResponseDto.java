package com.example.demo.dto;

import com.example.demo.entity.ArticleEntity;
import com.example.demo.entity.CommentEntity;
import com.example.demo.repository.CommentRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Getter
@Setter
public class ArticleDetailResponseDto {

    private Long articleId;

    private String title;

    private String content;

    private String username;

    private Enum category;

    private Boolean isMyArticles;

    private String createAt;

    private List<CommentEntity> comments;


    public ArticleDetailResponseDto(ArticleResponseDto articleResponseDto, List<CommentEntity> comments) {
        this.articleId = articleResponseDto.getArticleId();
        this.title = articleResponseDto.getTitle();
        this.content = articleResponseDto.getContent();
        this.username = articleResponseDto.getUsername();
        this.category = articleResponseDto.getCategory();
        this.isMyArticles = articleResponseDto.getIsMyArticles();
        this.createAt = articleResponseDto.getCreateAt();
        this.comments = comments;
    }
}
