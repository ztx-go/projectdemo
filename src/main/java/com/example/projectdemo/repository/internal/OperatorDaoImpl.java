/**
 * 
 */
package com.example.projectdemo.repository.internal;

import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.entity.OperatorEntity;
import com.example.projectdemo.repository.OperatorRepository;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 管理员dao实现.
 * 
 * @author hzh
 * @date 2018年2月8日17:56:00
 * @version V1.0
 */
@Repository(value = "operatorDao")
public class OperatorDaoImpl implements OperatorDao {

  /**
   * 管理员repository自动注入.
   */
  @Autowired
  private OperatorRepository operatorRepository;

  @Override
  public Page<OperatorEntity> findByParams(Map<String, Object> params, Pageable pageable) {
    Page<OperatorEntity> list =
        operatorRepository.findAll(new Specification<OperatorEntity>() {

          @Override
          public Predicate toPredicate(Root<OperatorEntity> root, CriteriaQuery<?> query,
                                       CriteriaBuilder cb) {
            List<Predicate> predicates = new ArrayList<Predicate>();
            // 账号查询
            if (params.get("account") != null) {
              predicates.add(cb.like(root.get("account").as(String.class),
                  "%"+String.valueOf(params.get("account"))+"%"));
            }
            // 名字查询
            if (params.get("name") != null) {
              predicates.add(cb.like(root.get("name").as(String.class),
                  "%"+String.valueOf(params.get("name"))+"%"));
            }
            // 状态查询
            if (params.get("status") != null) {
              predicates.add(cb.equal(root.get("status").as(UseStatus.class),
                  (UseStatus) params.get("status")));
            }
            // 地区id查询
            if (params.get("area") != null) {
              predicates.add(cb.equal(root.get("area").get("id").as(String.class),
                  (String) params.get("area")));
            }
            // 根据地区id集合查询
            if (params.get("areas") != null) {
              predicates.add(root.get("area").get("id").in(params.get("areas")));
            }
            // 根据单位id集合查询
            if (params.get("userDeparts") != null) {
              predicates.add(root.get("depart").get("id").in(params.get("userDeparts")));
            }
            // 时间
            Date beginCreateDate = (Date) params.get("beginDate");
            Date endCreateDate = (Date) params.get("endDate");
            // 根据起时间查询
            if (null != beginCreateDate) {
              predicates.add(cb.greaterThanOrEqualTo(root.get("createDate"), beginCreateDate));
            }
            // 根据止时间查询
            if (null != endCreateDate) {
              predicates.add(cb.lessThanOrEqualTo(root.get("createDate"), endCreateDate));
            }
            // 遍历查询条件，查询语句
            query.where(predicates.toArray(new Predicate[predicates.size()]));
            return null;
          }
        }, pageable);
    return list;
  }

}
