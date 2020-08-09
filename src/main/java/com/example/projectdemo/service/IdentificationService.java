package com.example.projectdemo.service;

import com.example.projectdemo.entity.IdentificationEntity;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface IdentificationService {

    /**
     * 新增
     */
    IdentificationEntity create(IdentificationEntity identificationEntity, UserEntity userEntity);

    /**
     * 更新
     */
    IdentificationEntity update(IdentificationEntity identificationEntity, UserEntity userEntity);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据id查询
     */
    IdentificationEntity findById(String id);

    /**
     * 根据条件分页查询
     */
    Page<IdentificationEntity> findByConditions(Map<String, Object> map, Pageable pageable);
}
