package com.example.projectdemo.repository;

import com.example.projectdemo.entity.AuthTechEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("authTechRepository")
public interface AuthTechRepository extends
        JpaRepository<AuthTechEntity, String>,
        JpaSpecificationExecutor<AuthTechEntity> {
}
