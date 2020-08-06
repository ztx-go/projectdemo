package com.example.projectdemo.repository;

import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.entity.MapTreeEntity;
import com.example.projectdemo.entity.OperatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("operatorRepository")
public interface OperatorRepository
    extends
        JpaRepository<OperatorEntity, String>,
        JpaSpecificationExecutor<OperatorEntity> {
  /**
   * 根据账号，禁用/启用状态查找商家.
   * 
   * @param account 账号
   * @param status 禁用/启用状态
   * @return MerchantEntity
   */
  OperatorEntity findByAccountAndStatus(String account, UseStatus status);
  
  /**
   * 通过id查询后台管理员信息
   * @param id
   * @return
   */
  OperatorEntity getById(String id);
  /**
   * 通过account查找后台用户
   * @param account
   * @return
   */
  OperatorEntity findByAccount(String account);
  
  /**
   * 修改所有属于该地区的用户的状态
   * @param maptree
   * @param status
   */
  @Modifying
  @Query("update OperatorEntity o set o.status = :status where o.area = :maptree")
  void updateStatusByMaptree(@Param("maptree") MapTreeEntity maptree, @Param("status") UseStatus status);
  
}
