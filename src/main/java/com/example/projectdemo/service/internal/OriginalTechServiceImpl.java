package com.example.projectdemo.service.internal;

import com.example.projectdemo.entity.OriginalTechEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.OriginalTechRepository;
import com.example.projectdemo.service.OriginalTechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;

@Service("originalTechService")
public class OriginalTechServiceImpl implements OriginalTechService {


    @Autowired
    OriginalTechRepository originalTechRepository;

    @Transactional
    @Override
    public OriginalTechEntity create(OriginalTechEntity originalTechEntity, UserEntity userEntity) {
        originalTechEntity.setCreateUser(userEntity);
        originalTechEntity.setCreateDate(new Date());
        OriginalTechEntity entity = originalTechRepository.saveAndFlush(originalTechEntity);
        return entity;
    }

    @Transactional
    @Override
    public OriginalTechEntity update(OriginalTechEntity originalSourceAreaEntity, UserEntity userEntity) {
        originalSourceAreaEntity.setModifyUser(userEntity);
        originalSourceAreaEntity.setModifyDate(new Date());
        OriginalTechEntity entity = originalTechRepository.saveAndFlush(originalSourceAreaEntity);
        return entity;
    }

    @Transactional
    @Override
    public void delete(String id) {
        originalTechRepository.deleteById(id);
    }

    @Override
    public OriginalTechEntity findById(String id) {
        OriginalTechEntity entity = originalTechRepository.findById(id).orElse(null);
        return entity;
    }

    @Override
    public Page<OriginalTechEntity> findByConditions(Map<String, Object> map, Pageable pageable) {
        return null;
    }

}
