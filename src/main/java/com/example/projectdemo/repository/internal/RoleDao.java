package com.example.projectdemo.repository.internal;

import com.example.projectdemo.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author hc
 *
 */
public interface RoleDao {
  /**
   * 条件分页查询.
   * 
   * @param params 条件
   * @param pageable 分页
   * @return Page<RoleEntity>
   */
  Page<RoleEntity> findByConditions(Map<String, Object> params, Pageable pageable);
  
  /**
   * 条件查询
   * 
   * @param params
   * @param pageable
   * @return
   */
  List<RoleEntity> findByConditions(Map<String, Object> params);
  
}
