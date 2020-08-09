package com.example.projectdemo.service.internal;

import com.example.projectdemo.entity.CopyrightVerifyEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.CopyrightVerifyRepository;
import com.example.projectdemo.service.CopyrightVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service("copyrightVerifyService")
public class CopyrightVerifyServiceImpl implements CopyrightVerifyService {

    @Autowired
    CopyrightVerifyRepository CopyrightVerifyRepository;

    @Override
    public CopyrightVerifyEntity create(CopyrightVerifyEntity copyrightVerifyEntity, UserEntity userEntity) {
        copyrightVerifyEntity.setCreateUser(userEntity);
        copyrightVerifyEntity.setCreateDate(new Date());
        CopyrightVerifyEntity entity = CopyrightVerifyRepository.saveAndFlush(copyrightVerifyEntity);
        return entity;
    }

    @Override
    public CopyrightVerifyEntity update(CopyrightVerifyEntity copyrightVerifyEntity, UserEntity userEntity) {
        copyrightVerifyEntity.setModifyUser(userEntity);
        copyrightVerifyEntity.setModifyDate(new Date());
        CopyrightVerifyEntity entity = CopyrightVerifyRepository.saveAndFlush(copyrightVerifyEntity);
        return entity;
    }

    @Override
    public void delete(String id) {
        CopyrightVerifyRepository.deleteById(id);
    }

    @Override
    public CopyrightVerifyEntity findById(String id) {
        CopyrightVerifyEntity entity = CopyrightVerifyRepository.findById(id);
        return entity;
    }

    @Override
    public Page<CopyrightVerifyEntity> findByConditions(Map<String, Object> map, Pageable pageable) {
        return null;
    }

}
