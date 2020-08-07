package com.example.projectdemo.service;

import com.example.projectdemo.entity.OriginalTechEntity;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface OriginalTechService {


    /**
     * 新增
     */
    OriginalTechEntity create(OriginalTechEntity originalTechEntity, UserEntity userEntity);

    /**
     * 更新
     */
    OriginalTechEntity update(OriginalTechEntity originalTechEntity, UserEntity userEntity);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据id查询
     */
    OriginalTechEntity findById(String id);

    /**
     * 根据条件分页查询
     */
    Page<OriginalTechEntity> findByConditions(Map<String, Object> map, Pageable pageable);

}
