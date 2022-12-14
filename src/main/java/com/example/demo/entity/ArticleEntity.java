package com.example.demo.entity;

import com.example.demo.dto.ArticleDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Article")
@Data
public class ArticleEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = 30, min = 1)
    private String title;

    @Column(nullable = false)
    @Size(max = 400, min = 1)
    private String content;

    @Column(nullable = false)
    private String userName;


    @Column
    private Boolean isMyArticles = true;

    @Column
    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "commentId")
    @JsonManagedReference
    private List<CommentEntity> CommentEntity;

//    @Column
//    private Category category;

    @JsonIgnore
    @Transient
    private LocalDateTime now1 = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    @Column(name = "createAt", nullable = false)
    private String createAt = now1.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));


    public ArticleEntity(ArticleDto articleDto, String userName) {
        this.title = articleDto.getTitle();
        this.content = articleDto.getContent();
        this.category = Category.valueOf(articleDto.getCategory());
        this.userName = userName;
    }

//   수정시 게시글만 수정
    public void patch(ArticleDto dto, String time) {
        this.content = dto.getContent();
        this.createAt = time;
    }
}
