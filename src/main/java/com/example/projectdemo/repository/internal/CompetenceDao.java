/**
 * 
 */
package com.example.projectdemo.repository.internal;

import com.example.projectdemo.entity.CompetenceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * 权限dao接口.
 * 
 * @author ly
 */
public interface CompetenceDao {

  /**
   * 条件分页查询.
   * 
   * @param params 条件
   * @param pageable 分页
   * @return Page<CompetenceEntity>
   */
  Page<CompetenceEntity> findByConditions(Map<String, Object> params, Pageable pageable);
}
