package com.example.demo.entity;

import com.example.demo.dto.CommentDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private Long articleId;

    @JsonIgnore
    private LocalDateTime now1 = LocalDateTime.now();

    @Column(name = "CREATED_TIME", nullable = false)
    private String createAt = now1.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));

//    @Column(nullable = false)
//    private Long articleId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ARTICLE_COMMENT_ID")
    private ArticleEntity articleEntity;

    public CommentEntity(CommentDto dto, String userName, ArticleEntity articleEntity, Long articleId) {
        this.comment = dto.getComment();
        this.articleEntity = articleEntity;
        this.userName = userName;
        this.articleId = articleId;
    }

}
