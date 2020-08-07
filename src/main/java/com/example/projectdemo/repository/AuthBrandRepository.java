package com.example.projectdemo.repository;

import com.example.projectdemo.entity.AuthBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("authBrandRepository")
public interface AuthBrandRepository extends
        JpaRepository<AuthBrandEntity, String>,
        JpaSpecificationExecutor<AuthBrandEntity> {
}
