package com.example.ApiDoAn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ApiDoAn.entity.ProductEntity;
import com.example.ApiDoAn.entity.ScoreBoardEntity;

public interface ScoreBoardRepository extends JpaRepository<ScoreBoardEntity,Long>{
	List<ScoreBoardEntity> findByCategoryEntityId(Long id);
}
