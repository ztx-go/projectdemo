package com.example.projectdemo.repository;

import com.example.projectdemo.entity.CopyrightVerifyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("copyrightVerifyRepository")
public interface CopyrightVerifyRepository extends
        JpaRepository<CopyrightVerifyEntity, String>,
        JpaSpecificationExecutor<CopyrightVerifyEntity> {

    void deleteById(String id);

    CopyrightVerifyEntity findById(String id);
}
