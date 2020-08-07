package com.example.projectdemo.repository;

import com.example.projectdemo.entity.OriginalCopyrightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("originalCopyrightRepository")
public interface OriginalCopyrightRepository extends
        JpaRepository<OriginalCopyrightEntity, String>,
        JpaSpecificationExecutor<OriginalCopyrightEntity> {
}
