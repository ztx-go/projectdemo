package com.example.projectdemo.service.internal;

import com.example.projectdemo.entity.AuthBrandEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.AuthBrandRepository;
import com.example.projectdemo.service.AuthBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;

@Service("authBrandService")
public class AuthBrandServiceImpl implements AuthBrandService {

    @Autowired
    AuthBrandRepository authBrandRepository;

    @Transactional
    @Override
    public AuthBrandEntity create(AuthBrandEntity authBrandEntity, UserEntity userEntity) {
        authBrandEntity.setCreateUser(userEntity);
        authBrandEntity.setCreateDate(new Date());
        AuthBrandEntity entity = authBrandRepository.saveAndFlush(authBrandEntity);
        return entity;
    }

    @Transactional
    @Override
    public AuthBrandEntity update(AuthBrandEntity authBrandEntity, UserEntity userEntity) {
        authBrandEntity.setModifyUser(userEntity);
        authBrandEntity.setModifyDate(new Date());
        AuthBrandEntity entity = authBrandRepository.saveAndFlush(authBrandEntity);
        return entity;
    }

    @Transactional
    @Override
    public void delete(String id) {
        authBrandRepository.deleteById(id);
    }

    @Override
    public AuthBrandEntity findById(String id) {
        AuthBrandEntity entity = authBrandRepository.findById(id).orElse(null);
        return entity;
    }

    @Override
    public Page<AuthBrandEntity> findByConditions(Map<String, Object> map, Pageable pageable) {
        return null;
    }
}
