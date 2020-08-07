package com.example.projectdemo.service;

import com.example.projectdemo.entity.AuthCopyrightEntity;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface AuthCopyrightService {
    /**
     * 新增
     */
    AuthCopyrightEntity create(AuthCopyrightEntity authCopyrightEntity, UserEntity userEntity);

    /**
     * 更新
     */
    AuthCopyrightEntity update(AuthCopyrightEntity authCopyrightEntity, UserEntity userEntity);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据id查询
     */
    AuthCopyrightEntity findById(String id);

    /**
     * 根据条件分页查询
     */
    Page<AuthCopyrightEntity> findByConditions(Map<String, Object> map, Pageable pageable);
}
