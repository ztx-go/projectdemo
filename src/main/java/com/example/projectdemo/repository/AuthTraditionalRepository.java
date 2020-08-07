package com.example.projectdemo.repository;

import com.example.projectdemo.entity.AuthTraditionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("authTraditionalRepository")
public interface AuthTraditionalRepository
        extends
        JpaRepository<AuthTraditionalEntity, String>,
        JpaSpecificationExecutor<AuthTraditionalEntity> {
}
