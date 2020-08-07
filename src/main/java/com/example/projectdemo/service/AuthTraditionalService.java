package com.example.projectdemo.service;

import com.example.projectdemo.entity.AuthTraditionalEntity;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * 授权作品_传统知识存证和保护
 */
public interface AuthTraditionalService {
    /**
     * 新增
     */
    AuthTraditionalEntity create(AuthTraditionalEntity authTraditionalEntity, UserEntity userEntity);

    /**
     * 更新
     */
    AuthTraditionalEntity update(AuthTraditionalEntity authTraditionalEntity, UserEntity userEntity);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据id查询
     */
    AuthTraditionalEntity findById(String id);

    /**
     * 根据条件分页查询
     */
    Page<AuthTraditionalEntity> findByConditions(Map<String, Object> map, Pageable pageable);
}
