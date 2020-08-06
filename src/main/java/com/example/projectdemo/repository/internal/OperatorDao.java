package com.example.projectdemo.repository.internal;

import com.example.projectdemo.entity.OperatorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * 管理員dao
 * @author hzh
 *
 */
public interface OperatorDao {
  
  /**
   * 根据条件查询后台用户
   * @param params 条件集
   * @param pageable 翻页对象
   * @return Page<OperatorEntity>
   */
  Page<OperatorEntity> findByParams(Map<String, Object> params, Pageable pageable);

}
