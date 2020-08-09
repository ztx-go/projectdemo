package com.example.projectdemo.service.internal;

import com.example.projectdemo.entity.IdentificationEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.IdentificationRepository;
import com.example.projectdemo.service.IdentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service("identificationService")
public class IdentificationServiceImpl implements IdentificationService {

    @Autowired
    IdentificationRepository identificationRepository;

    @Override
    public IdentificationEntity create(IdentificationEntity identificationEntity, UserEntity userEntity) {
        identificationEntity.setCreateUser(userEntity);
        identificationEntity.setCreateDate(new Date());
        IdentificationEntity entity = identificationRepository.saveAndFlush(identificationEntity);
        return entity;
    }

    @Override
    public IdentificationEntity update(IdentificationEntity identificationEntity, UserEntity userEntity) {
        identificationEntity.setModifyUser(userEntity);
        identificationEntity.setModifyDate(new Date());
        IdentificationEntity entity = identificationRepository.saveAndFlush(identificationEntity);
        return entity;
    }

    @Override
    public void delete(String id) {
        identificationRepository.deleteById(id);
    }

    @Override
    public IdentificationEntity findById(String id) {
        IdentificationEntity entity = identificationRepository.findById(id);
        return entity;
    }

    @Override
    public Page<IdentificationEntity> findByConditions(Map<String, Object> map, Pageable pageable) {
        return null;
    }
}
