package com.example.projectdemo.service.internal;

import com.example.projectdemo.entity.AuthTechEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.AuthTechRepository;
import com.example.projectdemo.service.AuthTechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;

@Service("authTechService")
public class AuthTechServiceImpl implements AuthTechService {

    @Autowired
    AuthTechRepository authTechRepository;

    @Transactional
    @Override
    public AuthTechEntity create(AuthTechEntity authTechEntity, UserEntity userEntity) {
        authTechEntity.setCreateUser(userEntity);
        authTechEntity.setCreateDate(new Date());
        AuthTechEntity entity = authTechRepository.saveAndFlush(authTechEntity);
        return entity;
    }

    @Transactional
    @Override
    public AuthTechEntity update(AuthTechEntity authTechEntity, UserEntity userEntity) {
        authTechEntity.setModifyUser(userEntity);
        authTechEntity.setModifyDate(new Date());
        AuthTechEntity entity = authTechRepository.saveAndFlush(authTechEntity);
        return entity;
    }

    @Transactional
    @Override
    public void delete(String id) {
        authTechRepository.deleteById(id);
    }

    @Override
    public AuthTechEntity findById(String id) {
        AuthTechEntity entity = authTechRepository.findById(id).orElse(null);
        return entity;
    }

    @Override
    public Page<AuthTechEntity> findByConditions(Map<String, Object> map, Pageable pageable) {
        return null;
    }
}
