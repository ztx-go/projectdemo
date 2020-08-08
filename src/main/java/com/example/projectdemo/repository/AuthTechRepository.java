package com.example.projectdemo.repository;

import com.example.projectdemo.entity.AuthTechEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("authTechRepository")
public interface AuthTechRepository extends
        JpaRepository<AuthTechEntity, String>,
        JpaSpecificationExecutor<AuthTechEntity> {

    void deleteById(String id);

    AuthTechEntity findById(String id);
}
