package com.example.projectdemo.repository;

import com.example.projectdemo.entity.OriginalBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("originalBrandRepository")
public interface OriginalBrandRepository extends
        JpaRepository<OriginalBrandEntity, String>,
        JpaSpecificationExecutor<OriginalBrandEntity> {

    void deleteById(String id);

    OriginalBrandEntity findById(String id);

}
