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
   * @Title: create
   * @date: 2019年3月27日 下午5:03:36
   * @param: @param user
   * @return: void
   * @author: fanda
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
   * @Title: update
   * @date: 2019年3月27日 下午5:20:07
   * @param: @param user
   * @return: void
   * @author: fanda
   */
  public void update(UserEntity user, UserEntity modefyUser, EntityType enty);

  /**
   * 通过id查询user
   *
   * @param id
   * @return
   */
  UserEntity getById(String id, UserEntity user, EntityType enty);

  /**
   * 启用/禁用
   * 
   * @Title: disableOrUsable
   * @date: 2019年3月27日 下午5:42:11
   * @param: @param id
   * @param: @param flag=ture,启用；false,禁用
   * @param: @param user
   * @param: @return
   * @return: UserEntity
   * @author: fanda
   */
  UserEntity disableOrUsable(String id, boolean flag, UserEntity user);

  /**
   * 分页条件查询
   * 
   * @Title: findByUser
   * @date: 2019年3月27日 下午5:54:23
   * @param: @param params
   * @param: @param pageable
   * @param: @return
   * @return: Page<UserEntity>
   * @author: fanda
   */
  Page<UserEntity> findByUser(Map<String, Object> params, Pageable pageable);

  /**
   * 根据用户账号和状态查询用户基本信息.
   * 
   * @param account 登录账号
   * @param status 状态
   * @return UserEntity
   */
  UserEntity findByAccountAndStatus(String account, UseStatus status);

  /**
   * 根据账号查找后台用户，只有状态正常的才能查询.
   * 
   * @param account 账号
   * @return MerchantEntity
   */
  UserEntity findByAccount(String account);

  /**
   * 更新当前用户的登录时间
   * 
   * @param account 指定的用户
   * @param loginTime 指定的最新的登录时间
   */
  void updateLoginTime(String account, Date loginTime);

  /**
   * 申请重置密码
   * 
   * @Title: applyRestPsd
   * @date: 2019年3月27日 下午6:06:18
   * @param: @param account 账号
   * @param: @param name 用户名
   * @param: @param telephone 手机号
   * @return: void
   * @author: fanda
   */
  void applyRestPsd(String account, String name, String telephone);

  /**
   * 重置密码
   * 
   * @Title: resetPsd
   * @date: 2019年3月27日 下午6:42:57
   * @param: @param id
   * @param: @param user
   * @param: @return
   * @return: UserEntity
   * @author: fanda
   */
  UserEntity resetPsd(String id, UserEntity user);
}
