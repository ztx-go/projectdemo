package com.example.projectdemo.service;

import com.example.projectdemo.entity.OriginalBrandEntity;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * 原创作品_商标和商号存证
 */
public interface OriginalBrandService {

    /**
     * 新增
     */
    OriginalBrandEntity create(OriginalBrandEntity originalBrandEntity, UserEntity userEntity);

    /**
     * 更新
     */
    OriginalBrandEntity update(OriginalBrandEntity originalBrandEntity, UserEntity userEntity);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据id查询
     */
    OriginalBrandEntity findById(String id);

    /**
     * 根据条件分页查询
     */
    Page<OriginalBrandEntity> findByConditions(Map<String, Object> map, Pageable pageable);
}
