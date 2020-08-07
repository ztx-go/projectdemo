package com.example.projectdemo.service.internal;

import com.example.projectdemo.entity.OriginalBrandEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.OriginalBrandRepository;
import com.example.projectdemo.service.OriginalBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;

@Service("originalBrandService")
public class OriginalBrandServiceImpl implements OriginalBrandService {

    @Autowired
    OriginalBrandRepository originalBrandRepository;

    @Transactional
    @Override
    public OriginalBrandEntity create(OriginalBrandEntity originalBrandEntity, UserEntity userEntity) {
        originalBrandEntity.setCreateUser(userEntity);
        originalBrandEntity.setCreateDate(new Date());
        OriginalBrandEntity entity = originalBrandRepository.saveAndFlush(originalBrandEntity);
        return entity;
    }

    @Transactional
    @Override
    public OriginalBrandEntity update(OriginalBrandEntity originalBrandEntity, UserEntity userEntity) {
        originalBrandEntity.setModifyUser(userEntity);
        originalBrandEntity.setModifyDate(new Date());
        OriginalBrandEntity entity = originalBrandRepository.saveAndFlush(originalBrandEntity);
        return entity;
    }

    @Transactional
    @Override
    public void delete(String id) {
        originalBrandRepository.deleteById(id);
    }

    @Override
    public OriginalBrandEntity findById(String id) {
        OriginalBrandEntity entity = originalBrandRepository.findById(id).orElse(null);
        return entity;
    }

    @Override
    public Page<OriginalBrandEntity> findByConditions(Map<String, Object> map, Pageable pageable) {
        return null;
    }

}
