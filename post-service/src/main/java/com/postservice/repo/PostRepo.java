package com.postservice.repo;

import com.postservice.entity.Post;
import com.postservice.shared.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

    List<Post> findByUserId(Long userId);

    List<Post> findByCategoryIdAndStatus (Long categoryId, Status status);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% ")
    List<Post>findByKeyword(String keyword);

    Page<Post> findAll(Pageable pageable);
}
