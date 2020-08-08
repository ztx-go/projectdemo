package com.example.projectdemo.service;

import com.example.projectdemo.common.enums.EntityType;
import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Map;

/**
 * 
 * ldap 用户
 *
 *
 */
public interface UserService {

  /**
   * 新增用户
   *
   * @param: @param user
   * @return: void
   */
  void create(UserEntity user, UserEntity opter, EntityType enty);

  /**
   * 修改管理员的密码
   *
   * @param id 要修改密码的管理员的id
   * @param oldPassword 原密码
   * @param newPassword 新密码
   * @param operator 修改人
   */
  void updatePassword(String id, String oldPassword, String newPassword, UserEntity operator);

  /**
   * 修改用户
   *
   */
   void update(UserEntity user, UserEntity modefyUser, EntityType enty);

  /**
   * 通过id查询user
   *
   */
  UserEntity getById(String id, UserEntity user, EntityType enty);

  /**
   * 启用/禁用
   *
   */
  UserEntity disableOrUsable(String id, boolean flag, UserEntity user);

  /**
   * 分页条件查询
   *
   */
  Page<UserEntity> findByUser(Map<String, Object> params, Pageable pageable);

  /**
   * 根据用户账号和状态查询用户基本信息.
   * 
   */
  UserEntity findByAccountAndStatus(String account, UseStatus status);

  /**
   * 根据账号查找后台用户，只有状态正常的才能查询.
   * 
   */
  UserEntity findByAccount(String account);

  /**
   * 更新当前用户的登录时间
   * 
   */
  void updateLoginTime(String account, Date loginTime);

  /**
   * 申请重置密码
   * 
   */
  void applyRestPsd(String account, String name, String telephone);

  /**
   * 重置密码
   * 
   */
  UserEntity resetPsd(String id, UserEntity user);

  void createByInit();
}
