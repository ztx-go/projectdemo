package com.example.projectdemo.service;

import com.example.projectdemo.entity.OriginalCopyrightEntity;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface OriginalCopyrightService {
    /**
     * 新增
     */
    OriginalCopyrightEntity create(OriginalCopyrightEntity originalCopyrightEntity, UserEntity userEntity);

    /**
     * 更新
     */
    OriginalCopyrightEntity update(OriginalCopyrightEntity originalCopyrightEntity, UserEntity userEntity);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据id查询
     */
    OriginalCopyrightEntity findById(String id);

    /**
     * 根据条件分页查询
     */
    Page<OriginalCopyrightEntity> findByConditions(Map<String, Object> map, Pageable pageable);
}
