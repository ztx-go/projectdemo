package com.example.projectdemo.service.internal;

import com.example.projectdemo.entity.AuthTraditionalEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.AuthTraditionalRepository;
import com.example.projectdemo.service.AuthTraditionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service("authTraditionalService")
public class AuthTraditionalServiceImpl implements AuthTraditionalService {

    @Autowired
    AuthTraditionalRepository authTraditionalRepository;

    @Override
    public AuthTraditionalEntity create(AuthTraditionalEntity authTraditionalEntity, UserEntity userEntity) {
        authTraditionalEntity.setCreateUser(userEntity);
        authTraditionalEntity.setCreateDate(new Date());
        AuthTraditionalEntity entity = authTraditionalRepository.saveAndFlush(authTraditionalEntity);
        return entity;
    }

    @Override
    public AuthTraditionalEntity update(AuthTraditionalEntity authTraditionalEntity, UserEntity userEntity) {
        authTraditionalEntity.setModifyUser(userEntity);
        authTraditionalEntity.setModifyDate(new Date());
        AuthTraditionalEntity entity = authTraditionalRepository.saveAndFlush(authTraditionalEntity);
        return entity;
    }

    @Override
    public void delete(String id) {
        authTraditionalRepository.deleteById(id);
    }

    @Override
    public AuthTraditionalEntity findById(String id) {
        AuthTraditionalEntity entity = authTraditionalRepository.findById(id).orElse(null);
        return entity;
    }

    @Override
    public Page<AuthTraditionalEntity> findByConditions(Map<String, Object> map, Pageable pageable) {
        return null;
    }
}
