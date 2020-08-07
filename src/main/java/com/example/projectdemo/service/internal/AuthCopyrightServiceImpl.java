package com.example.projectdemo.service.internal;

import com.example.projectdemo.entity.AuthCopyrightEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.AuthCopyrightRepository;
import com.example.projectdemo.service.AuthCopyrightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;

@Service("authCopyrightService")
public class AuthCopyrightServiceImpl implements AuthCopyrightService {

    @Autowired
    AuthCopyrightRepository authCopyrightRepository;

    @Transactional
    @Override
    public AuthCopyrightEntity create(AuthCopyrightEntity authCopyrightEntity, UserEntity userEntity) {
        authCopyrightEntity.setCreateUser(userEntity);
        authCopyrightEntity.setCreateDate(new Date());
        AuthCopyrightEntity entity = authCopyrightRepository.saveAndFlush(authCopyrightEntity);
        return entity;
    }

    @Transactional
    @Override
    public AuthCopyrightEntity update(AuthCopyrightEntity authCopyrightEntity, UserEntity userEntity) {
        authCopyrightEntity.setModifyUser(userEntity);
        authCopyrightEntity.setModifyDate(new Date());
        AuthCopyrightEntity entity = authCopyrightRepository.saveAndFlush(authCopyrightEntity);
        return entity;
    }

    @Transactional
    @Override
    public void delete(String id) {
        authCopyrightRepository.deleteById(id);
    }

    @Override
    public AuthCopyrightEntity findById(String id) {
        AuthCopyrightEntity entity = authCopyrightRepository.findById(id).orElse(null);
        return entity;
    }

    @Override
    public Page<AuthCopyrightEntity> findByConditions(Map<String, Object> map, Pageable pageable) {
        return null;
    }
}
