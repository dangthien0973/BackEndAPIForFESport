package com.example.ApiDoAn.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ApiDoAn.entity.CategoryEntity;
import com.example.ApiDoAn.entity.ProductEntity;

import java.util.Optional;

public interface CategoryRepository  extends JpaRepository<CategoryEntity,Long> {
    Optional<CategoryEntity> findById(Long id);
    CategoryEntity findByProductEntitys(ProductEntity productEntity);
    
}
