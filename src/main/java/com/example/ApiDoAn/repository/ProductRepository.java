package com.example.ApiDoAn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.ApiDoAn.entity.ProductEntity;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity,Long>, JpaSpecificationExecutor, PagingAndSortingRepository<ProductEntity, Long>  {
        Optional<ProductEntity> findById(Long id);
        Page<ProductEntity> findAll(Pageable pageable);
        Page<ProductEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
