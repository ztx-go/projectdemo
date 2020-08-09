package com.example.projectdemo.repository.internal;

import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.UserRepository;
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

@Repository("userDao")
public class UserDaoImpl implements UserDao {

  @Autowired
  private UserRepository user;

  @Override
  public Page<UserEntity> findByUser(Map<String, Object> map, Pageable pageable) {
    Page<UserEntity> list = user.findAll(new Specification<UserEntity>() {

      @Override
      public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query,
                                   CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        // 查询账号
        if (map.get("account") != null) {
          predicates.add(cb.like(root.get("account").as(String.class),
              "%" + String.valueOf(map.get("account") + "%")));
        }
        // 查询名称
        if (map.get("name") != null) {
          predicates.add(cb.like(root.get("userName").as(String.class),
              "%" + String.valueOf(map.get("name") + "%")));
        }
        // 查询状态
        if (map.get("status") != null) {
          predicates
              .add(cb.equal(root.get("status").as(UseStatus.class), (UseStatus) map.get("status")));
        }
        // 查询部门
        if (map.get("depart") != null) {
          predicates.add(cb.like(root.join("depart").get("name").as(String.class),
              "%" + map.get("depart").toString() + "%"));
        }

        // 查询是否重置密码
        if (map.get("isResetPsd") != null) {
          boolean flag = (boolean) map.get("isResetPsd");
          if (flag) {
            predicates.add(cb.isTrue(root.get("isResetPsd")));
          } else {
            predicates.add(cb.isFalse(root.get("isResetPsd")));
          }
        }
        // 遍历查询条件，查询语句
        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return null;
      }

    }, pageable);
    return list;
  }

}
