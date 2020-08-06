package com.example.projectdemo.repository.internal;

import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.entity.RoleEntity;
import com.example.projectdemo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author hc
 *
 */
@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {
  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Page<RoleEntity> findByConditions(Map<String, Object> params, Pageable pageable) {
    Page<RoleEntity> list = roleRepository.findAll(new Specification<RoleEntity>() {

      @Override
      public Predicate toPredicate(Root<RoleEntity> root, CriteriaQuery<?> query,
                                   CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        // 查询角色名称
        if (params.get("roleName") != null) {
          predicates.add(cb.like(root.get("name").as(String.class),
              "%" + String.valueOf(params.get("roleName") + "%")));
        }
        // 查询状态
        if (params.get("status") != null) {
          predicates.add(
              cb.equal(root.get("status").as(UseStatus.class), (UseStatus) params.get("status")));
        }
        // 遍历查询条件，查询语句
        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return null;
      }
    }, pageable);
    return list;
  }
  
  @Override
  public List<RoleEntity> findByConditions(Map<String, Object> params) {
    List<RoleEntity> list = roleRepository.findAll(new Specification<RoleEntity>() {

      @Override
      public Predicate toPredicate(Root<RoleEntity> root, CriteriaQuery<?> query,
                                   CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        // 查询角色名称
        if (params.get("name") != null) {
          predicates.add(cb.like(root.get("name").as(String.class),
              "%" + String.valueOf(params.get("name") + "%")));
        }
        // 查询状态
        if (params.get("status") != null) {
          predicates.add(
              cb.equal(root.get("status").as(UseStatus.class), (UseStatus) params.get("status")));
        }
        // 遍历查询条件，查询语句
        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return null;
      }
    });
    return list;
  }

}
