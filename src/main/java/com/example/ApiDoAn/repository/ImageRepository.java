package com.example.ApiDoAn.repository;

	import org.springframework.data.jpa.repository.JpaRepository;

	import com.example.ApiDoAn.entity.CategoryEntity;
import com.example.ApiDoAn.entity.ImageEntity;
import com.example.ApiDoAn.entity.ProductEntity;

	import java.util.Optional;

	public interface ImageRepository  extends JpaRepository<ImageEntity,Long> {
	  
	   
	    
	}


