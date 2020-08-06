package com.example.projectdemo.service;

import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.entity.MapTreeEntity;
import com.example.projectdemo.entity.OperatorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface OperatorService {
  
  
  /**
   * 创建管理员
   * @param operator 管理员
   */
  void create(OperatorEntity operator);
  
  /**
   * 初始创建admin
   */
  void createByInit();
  
  /**
   * 修改管理员信息
   * @param operator 管理员
   */
  void update(OperatorEntity operator);
  
  /**
   * 修改管理员的密码
   * @param id 要修改密码的管理员的id
   * @param oldPassword 原密码
   * @param newPassword 新密码
   * @param operator 修改人
   */
  void updatePassword(String id, String oldPassword, String newPassword, OperatorEntity operator);
  
  /**
   * 禁用管理员
   * @param id 管理员id
   * @param operator 修改人
   */
  void disable(String id, OperatorEntity operator);
  
  /**
   * 启用管理员
   * @param id 管理员id
   * @param operator 修改人
   */
  void enable(String id, OperatorEntity operator);
  
  /**
   * 根据id获取该后台管理员
   * @param id 管理员id
   * @return OperatorEntity
   */
  OperatorEntity getById(String id);
  
  /**
   * 根据账号及状态查找后台管理员.
   * 
   * @param account 账号
   * @param status 账号状态，请参见枚举
   * @return OperatorEntity
   */
  OperatorEntity findByAccountAndStatus(String account, UseStatus status);
  
  /**
   * 根据账号查找后台用户，只有状态正常的才能查询.
   * 
   * @param account 账号
   * @return MerchantEntity
   */
  OperatorEntity findByAccount(String account);
  
  /**
   * 根据条件查询后台用户
   * @param params 条件集
   * @param pageable 翻页对象
   * @return Page<OperatorEntity>
   */
  Page<OperatorEntity> findByParams(Map<String, Object> params, Pageable pageable);
  
  /**
   * 根据管理员id重置其密码为123456
   * @param resetId
   * @param operator
   */
  void resetPassword(String resetId, OperatorEntity operator);
  
  /**
   * 修改登录时间
   * @param operator
   */
  void updateLoginDate(OperatorEntity operator);
  
  /**
   * 修改所有属于该地区的用户的状态
   * @param position
   * @param status
   */
  void updateStatusByMaptree(MapTreeEntity position, UseStatus status);


}
