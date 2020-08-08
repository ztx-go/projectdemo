package com.example.projectdemo.repository;

import com.example.projectdemo.entity.AuthCopyrightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("authCopyrightRepository")
public interface AuthCopyrightRepository extends
        JpaRepository<AuthCopyrightEntity, String>,
        JpaSpecificationExecutor<AuthCopyrightEntity> {

    void deleteById(String id);

    AuthCopyrightEntity findById(String id);
}
