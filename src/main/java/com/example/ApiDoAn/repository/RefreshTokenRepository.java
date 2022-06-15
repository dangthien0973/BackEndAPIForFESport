package com.example.ApiDoAn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ApiDoAn.entity.RefreshTokenEntity;
import com.example.ApiDoAn.entity.UserEntity;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findById(Long id);
    @Query("SELECT r FROM RefreshTokenEntity r where  r.userEntity.id = ?1")
    RefreshTokenEntity findByIdUserEntity(Long id);
    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByUserEntity(UserEntity user);
}
