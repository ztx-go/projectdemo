package com.example.projectdemo.service;

import com.example.projectdemo.entity.AuthSourceAreaEntity;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface AuthSourceAreaService {
    /**
     * 新增
     */
    AuthSourceAreaEntity create(AuthSourceAreaEntity authSourceAreaEntity, UserEntity userEntity);

    /**
     * 更新
     */
    AuthSourceAreaEntity update(AuthSourceAreaEntity authSourceAreaEntity, UserEntity userEntity);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据id查询
     */
    AuthSourceAreaEntity findById(String id);

    /**
     * 根据条件分页查询
     */
    Page<AuthSourceAreaEntity> findByConditions(Map<String, Object> map, Pageable pageable);
}
