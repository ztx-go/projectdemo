package com.example.projectdemo.repository;

import com.example.projectdemo.entity.OriginalTechEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("originalTechRepository")
public interface OriginalTechRepository extends
        JpaRepository<OriginalTechEntity, String>,
        JpaSpecificationExecutor<OriginalTechEntity> {

    void deleteById(String id);

    OriginalTechEntity findById(String id);
}
