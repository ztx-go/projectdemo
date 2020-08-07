package com.example.projectdemo.service.internal;

import com.example.projectdemo.entity.OriginalTraditionalEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.OriginalTraditionalRepository;
import com.example.projectdemo.service.OriginalTraditionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;

@Service("originalTraditionalService")
public class OriginalTraditionalServiceImpl implements OriginalTraditionalService {


    @Autowired
    OriginalTraditionalRepository originalTraditionalRepository;

    @Transactional
    @Override
    public OriginalTraditionalEntity create(OriginalTraditionalEntity originalTraditionalEntity, UserEntity userEntity) {
        originalTraditionalEntity.setCreateUser(userEntity);
        originalTraditionalEntity.setCreateDate(new Date());
        OriginalTraditionalEntity entity = originalTraditionalRepository.saveAndFlush(originalTraditionalEntity);
        return entity;
    }

    @Transactional
    @Override
    public OriginalTraditionalEntity update(OriginalTraditionalEntity originalTraditionalEntity, UserEntity userEntity) {
        originalTraditionalEntity.setModifyUser(userEntity);
        originalTraditionalEntity.setModifyDate(new Date());
        OriginalTraditionalEntity entity = originalTraditionalRepository.saveAndFlush(originalTraditionalEntity);
        return entity;
    }

    @Transactional
    @Override
    public void delete(String id) {
        originalTraditionalRepository.deleteById(id);
    }

    @Override
    public OriginalTraditionalEntity findById(String id) {
        OriginalTraditionalEntity entity = originalTraditionalRepository.findById(id).orElse(null);
        return entity;
    }

    @Override
    public Page<OriginalTraditionalEntity> findByConditions(Map<String, Object> map, Pageable pageable) {
        return null;
    }

}
