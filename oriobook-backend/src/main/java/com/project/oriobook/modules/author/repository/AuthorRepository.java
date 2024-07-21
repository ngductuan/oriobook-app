package com.project.oriobook.modules.author.repository;

import com.project.oriobook.modules.author.dto.FindAllAuthorQueryDTO;
import com.project.oriobook.modules.author.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthorRepository extends JpaRepository<Author, String>{
    @Query("SELECT p FROM Author p WHERE " +
            // authorName
            "(:#{#query.authorName} IS NULL OR :#{#query.authorName} = '' OR p.name LIKE %:#{#query.authorName}%) " +
            // gender
            "AND (:#{#query.gender} IS NULL OR :#{#query.gender == null ? '' : #query.gender.name()} = '' " +
            "OR p.gender = :#{#query.gender})" +
            // publishedBook
            "AND (:#{#query.publishedBook} IS NULL OR p.publishedBook = :#{#query.publishedBook})")
    Page<Author> findAll(@Param("query") FindAllAuthorQueryDTO query, Pageable pageable);

    @Modifying
    @Query("UPDATE Author p SET p.publishedBook = :publishedBooks WHERE p.id = :id")
    int setPublishedBooks(@Param("id") String id, @Param("publishedBooks") int publishedBooks);
}
