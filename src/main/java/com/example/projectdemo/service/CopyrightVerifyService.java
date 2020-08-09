package com.example.projectdemo.service;

import com.example.projectdemo.entity.CopyrightVerifyEntity;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * 版权确认
 */
public interface CopyrightVerifyService {

    /**
     * 新增
     */
    CopyrightVerifyEntity create(CopyrightVerifyEntity copyrightVerifyEntity, UserEntity userEntity);

    /**
     * 更新
     */
    CopyrightVerifyEntity update(CopyrightVerifyEntity copyrightVerifyEntity, UserEntity userEntity);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据id查询
     */
    CopyrightVerifyEntity findById(String id);

    /**
     * 根据条件分页查询
     */
    Page<CopyrightVerifyEntity> findByConditions(Map<String, Object> map, Pageable pageable);
}
