package com.example.projectdemo.service.internal;

import com.example.projectdemo.entity.AuthSourceAreaEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.AuthSourceAreaRepository;
import com.example.projectdemo.service.AuthSourceAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;

@Service("authSourceAreaService")
public class AuthSourceAreaServiceImpl implements AuthSourceAreaService {

    @Autowired
    AuthSourceAreaRepository authSourceAreaRepository;

    @Transactional
    @Override
    public AuthSourceAreaEntity create(AuthSourceAreaEntity authSourceAreaEntity, UserEntity userEntity) {
        authSourceAreaEntity.setCreateUser(userEntity);
        authSourceAreaEntity.setCreateDate(new Date());
        AuthSourceAreaEntity entity = authSourceAreaRepository.saveAndFlush(authSourceAreaEntity);
        return entity;
    }

    @Transactional
    @Override
    public AuthSourceAreaEntity update(AuthSourceAreaEntity authSourceAreaEntity, UserEntity userEntity) {
        authSourceAreaEntity.setModifyUser(userEntity);
        authSourceAreaEntity.setModifyDate(new Date());
        AuthSourceAreaEntity entity = authSourceAreaRepository.saveAndFlush(authSourceAreaEntity);
        return entity;
    }

    @Transactional
    @Override
    public void delete(String id) {
        authSourceAreaRepository.deleteById(id);
    }

    @Override
    public AuthSourceAreaEntity findById(String id) {
        AuthSourceAreaEntity entity = authSourceAreaRepository.findById(id);
        return entity;
    }

    @Override
    public Page<AuthSourceAreaEntity> findByConditions(Map<String, Object> map, Pageable pageable) {
        return null;
    }
}
