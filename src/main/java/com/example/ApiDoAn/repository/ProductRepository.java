package com.example.ApiDoAn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.ApiDoAn.entity.ProductEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, PagingAndSortingRepository<ProductEntity, Long> {
	Optional<ProductEntity> findById(Long id);
	Page<ProductEntity> findAll(Pageable pageable);
	@Query("SELECT u FROM ProductEntity  u  WHERE u.categoryEntity.id IN (:lstCateGoryID)  ")
	Page<ProductEntity> filterProduct(@Param("lstCateGoryID") List<Long> lstCateGory, Pageable pageable);
	@Query("SELECT p FROM ProductEntity p WHERE UPPER(p.name) LIKE CONCAT('%',UPPER(:keyWord),'%')")
	Page<ProductEntity> search(@Param("keyWord") String keyword, Pageable pageable);
	 Page<ProductEntity> findByCategoryEntityId(Long id, Pageable pageable);
		@Query(value = "select * from product order by id desc limit 5",nativeQuery = true)
	List<ProductEntity> getRecentNew();
	
}
