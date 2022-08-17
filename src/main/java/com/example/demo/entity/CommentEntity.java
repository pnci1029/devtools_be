package com.example.demo.entity;

import com.example.demo.dto.CommentDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private String username;

    @Column(nullable = false)
    private Long articleId;

    @JsonIgnore
    @Transient
    private LocalDateTime now1 = LocalDateTime.now(ZoneId.of("Asia/Seoul"));;

    @Column(name = "CREATED_TIME", nullable = false)
    private String createAt = now1.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));

//    @Column(nullable = false)
//    private Long articleId;

    @JsonBackReference
    @Transient
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action= OnDeleteAction.CASCADE)
    private ArticleEntity articleEntity;

    public CommentEntity(CommentDto dto, String username, ArticleEntity articleEntity, Long articleId) {
        this.comment = dto.getComment();
        this.articleEntity = articleEntity;
        this.username = username;
        this.articleId = articleId;
    }

}
