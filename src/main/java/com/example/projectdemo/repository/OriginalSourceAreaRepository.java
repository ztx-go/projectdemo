package com.example.projectdemo.repository;

import com.example.projectdemo.entity.OriginalSourceAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("originalSourceAreaRepository")
public interface OriginalSourceAreaRepository extends
        JpaRepository<OriginalSourceAreaEntity, String>,
        JpaSpecificationExecutor<OriginalSourceAreaEntity> {

    void deleteById(String id);

    OriginalSourceAreaEntity findById(String id);
}
