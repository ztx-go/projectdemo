package com.example.projectdemo.repository;

import com.example.projectdemo.entity.AuthSourceAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("authSourceAreaRepository")
public interface AuthSourceAreaRepository extends
        JpaRepository<AuthSourceAreaEntity, String>,
        JpaSpecificationExecutor<AuthSourceAreaEntity> {
}
