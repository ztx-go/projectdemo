package com.example.projectdemo.service;

import com.example.projectdemo.entity.AuthTechEntity;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * 授权作品_技术成果存证方法
 */
public interface AuthTechService {

    /**
     * 新增
     */
    AuthTechEntity create(AuthTechEntity authTechEntity, UserEntity userEntity);

    /**
     * 更新
     */
    AuthTechEntity update(AuthTechEntity authTechEntity, UserEntity userEntity);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据id查询
     */
    AuthTechEntity findById(String id);

    /**
     * 根据条件分页查询
     */
    Page<AuthTechEntity> findByConditions(Map<String, Object> map, Pageable pageable);


}
