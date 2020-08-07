package com.example.projectdemo.repository;

import com.example.projectdemo.entity.OriginalTraditionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("originalTraditionalRepository")
public interface OriginalTraditionalRepository extends
        JpaRepository<OriginalTraditionalEntity, String>,
        JpaSpecificationExecutor<OriginalTraditionalEntity> {
}
