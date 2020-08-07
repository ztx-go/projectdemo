package com.example.projectdemo.service;

import com.example.projectdemo.entity.OriginalTraditionalEntity;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface OriginalTraditionalService {

    /**
     * 新增
     */
    OriginalTraditionalEntity create(OriginalTraditionalEntity originalTechEntity, UserEntity userEntity);

    /**
     * 更新
     */
    OriginalTraditionalEntity update(OriginalTraditionalEntity originalTechEntity, UserEntity userEntity);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据id查询
     */
    OriginalTraditionalEntity findById(String id);

    /**
     * 根据条件分页查询
     */
    Page<OriginalTraditionalEntity> findByConditions(Map<String, Object> map, Pageable pageable);

}
