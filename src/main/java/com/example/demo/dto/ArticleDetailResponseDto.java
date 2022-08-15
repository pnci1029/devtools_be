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

    private Long id;

    private String title;

    private String content;

    private String username;

    private String category;

    private Boolean isMyArticles;

    private String now;

    private List<CommentEntity> commentEntity;


    public ArticleDetailResponseDto(ArticleEntity articleEntity, List<CommentEntity> commentEntity) {
        this.id = articleEntity.getId();
        this.title = articleEntity.getTitle();
        this.content = articleEntity.getContent();
        this.username = articleEntity.getUserName();
        this.category = articleEntity.getUserName();
        this.isMyArticles = Boolean.TRUE;
        this.now = articleEntity.getCreateAt();
        this.commentEntity = commentEntity;
    }
}
