package com.example.projectdemo.repository.internal;

import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface UserDao {
	/**
	 * 分页查询
	 * 
	 * @param map
	 * @param pageable
	 * @return
	 */
	Page<UserEntity> findByUser(Map<String, Object> map, Pageable pageable);
}
