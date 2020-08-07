package com.example.projectdemo.service;

import com.example.projectdemo.entity.OriginalSourceAreaEntity;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface OriginalSourceAreaService {

    /**
     * 新增
     */
    OriginalSourceAreaEntity create(OriginalSourceAreaEntity originalSourceAreaEntity, UserEntity userEntity);

    /**
     * 更新
     */
    OriginalSourceAreaEntity update(OriginalSourceAreaEntity originalSourceAreaEntity, UserEntity userEntity);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据id查询
     */
    OriginalSourceAreaEntity findById(String id);

    /**
     * 根据条件分页查询
     */
    Page<OriginalSourceAreaEntity> findByConditions(Map<String, Object> map, Pageable pageable);
}
