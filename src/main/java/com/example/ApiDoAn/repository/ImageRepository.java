package com.example.ApiDoAn.repository;

	import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ApiDoAn.entity.CategoryEntity;
import com.example.ApiDoAn.entity.ImageEntity;
import com.example.ApiDoAn.entity.ProductEntity;
import com.example.ApiDoAn.entity.UserEntity;

import java.util.List;
import java.util.Optional;

	public interface ImageRepository  extends JpaRepository<ImageEntity,Long> {
		@Query("SELECT u FROM ImageEntity  u  WHERE u.productEntity.id  = ?1  ")
		 List<ImageEntity> listImageByProduct(Long id);
	    
	}


