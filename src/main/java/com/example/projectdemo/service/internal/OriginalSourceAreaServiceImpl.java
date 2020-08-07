package com.example.projectdemo.service.internal;

import com.example.projectdemo.entity.OriginalSourceAreaEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.OriginalSourceAreaRepository;
import com.example.projectdemo.service.OriginalSourceAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;

@Service("originalSourceAreaService")
public class OriginalSourceAreaServiceImpl implements OriginalSourceAreaService {


    @Autowired
    OriginalSourceAreaRepository originalSourceAreaRepository;

    @Transactional
    @Override
    public OriginalSourceAreaEntity create(OriginalSourceAreaEntity originalSourceAreaEntity, UserEntity userEntity) {
        originalSourceAreaEntity.setCreateUser(userEntity);
        originalSourceAreaEntity.setCreateDate(new Date());
        OriginalSourceAreaEntity entity = originalSourceAreaRepository.saveAndFlush(originalSourceAreaEntity);
        return entity;
    }

    @Transactional
    @Override
    public OriginalSourceAreaEntity update(OriginalSourceAreaEntity originalSourceAreaEntity, UserEntity userEntity) {
        originalSourceAreaEntity.setModifyUser(userEntity);
        originalSourceAreaEntity.setModifyDate(new Date());
        OriginalSourceAreaEntity entity = originalSourceAreaRepository.saveAndFlush(originalSourceAreaEntity);
        return entity;
    }

    @Transactional
    @Override
    public void delete(String id) {
        originalSourceAreaRepository.deleteById(id);
    }

    @Override
    public OriginalSourceAreaEntity findById(String id) {
        OriginalSourceAreaEntity entity = originalSourceAreaRepository.findById(id).orElse(null);
        return entity;
    }

    @Override
    public Page<OriginalSourceAreaEntity> findByConditions(Map<String, Object> map, Pageable pageable) {
        return null;
    }

}
