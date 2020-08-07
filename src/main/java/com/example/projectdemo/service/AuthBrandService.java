package com.example.projectdemo.service;

import com.example.projectdemo.entity.AuthBrandEntity;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface AuthBrandService {

    /**
     * 新增
     */
    AuthBrandEntity create(AuthBrandEntity authBrandEntity, UserEntity userEntity);

    /**
     * 更新
     */
    AuthBrandEntity update(AuthBrandEntity authBrandEntity, UserEntity userEntity);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据id查询
     */
    AuthBrandEntity findById(String id);

    /**
     * 根据条件分页查询
     */
    Page<AuthBrandEntity> findByConditions(Map<String, Object> map, Pageable pageable);
}
