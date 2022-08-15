package com.example.demo.repository;

import com.example.demo.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
//    리스트값들을 페이징해서 정렬
//    @Query("select p from ArticleEntity p ORDER BY p.id DESC")
////      페이지네이션
//    Page<ArticleEntity> findAllDesc(Pageable pageable);


//    원본
    @Query("select p from ArticleEntity p ORDER BY p.id DESC")
    List<ArticleEntity> findAllDesc();

    ArticleEntity findArticlesById(Long id);
}
