package com.example.projectdemo.repository;

import com.example.projectdemo.entity.IdentificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("identificationRepository")
public interface IdentificationRepository extends
        JpaRepository<IdentificationEntity, String>,
        JpaSpecificationExecutor<IdentificationEntity> {

    void deleteById(String id);

    IdentificationEntity findById(String id);
}
