package com.example.projectdemo.service.internal;

import com.example.projectdemo.entity.OriginalCopyrightEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.OriginalCopyrightRepository;
import com.example.projectdemo.service.OriginalCopyrightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;

@Service("originalCopyrightService")
public class OriginalCopyrightServiceImpl implements OriginalCopyrightService {


    @Autowired
    OriginalCopyrightRepository originalCopyrightRepository;

    @Transactional
    @Override
    public OriginalCopyrightEntity create(OriginalCopyrightEntity originalCopyrightEntity, UserEntity userEntity) {
        originalCopyrightEntity.setCreateUser(userEntity);
        originalCopyrightEntity.setCreateDate(new Date());
        OriginalCopyrightEntity entity = originalCopyrightRepository.saveAndFlush(originalCopyrightEntity);
        return entity;
    }

    @Transactional
    @Override
    public OriginalCopyrightEntity update(OriginalCopyrightEntity originalCopyrightEntity, UserEntity userEntity) {
        originalCopyrightEntity.setModifyUser(userEntity);
        originalCopyrightEntity.setModifyDate(new Date());
        OriginalCopyrightEntity entity = originalCopyrightRepository.saveAndFlush(originalCopyrightEntity);
        return entity;
    }

    @Transactional
    @Override
    public void delete(String id) {
        originalCopyrightRepository.deleteById(id);
    }

    @Override
    public OriginalCopyrightEntity findById(String id) {
        OriginalCopyrightEntity entity = originalCopyrightRepository.findById(id);
        return entity;
    }

    @Override
    public Page<OriginalCopyrightEntity> findByConditions(Map<String, Object> map, Pageable pageable) {
        return null;
    }

}
