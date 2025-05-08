package com.postservice.repo;

import com.postservice.entity.Category;
import com.postservice.shared.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    List<Category> findByStatus(Status status);
}
