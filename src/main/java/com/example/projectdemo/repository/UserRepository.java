package com.example.projectdemo.repository;

import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 后台用户
 */
@Repository("userRepository")
public interface UserRepository
    extends
        JpaRepository<UserEntity, String>,
        JpaSpecificationExecutor<UserEntity> {

  @Query("FROM UserEntity u WHERE u.id=:id")
  UserEntity findId(@Param("id") String id);


  @Query("FROM UserEntity u WHERE u.userName=:name")
  UserEntity findName(@Param("name") String name);

//  @Query("FROM UserEntity u WHERE u.userName=:name and u.status=:status")
//  UserEntity findNameStatus(@Param("name") String name, @Param("status") UseStatus status);

  /**
   * 根据用户账号和状态查询用户基本信息.
   * 
   * @param account 登录账号
   * @param status 状态
   * @return UserEntity
   */
  UserEntity findByAccountAndStatus(String account, UseStatus status);

  /**
   * 更新某个用户的登录时间，到指定时间
   * 
   * @param account 指定的用户账号
   * @param loginTime 指定的最新的登录时间
   */
  @Modifying
  @Query(value = "update eb_user set last_login_date = :loginTime where user_account = :account ", nativeQuery = true)
  void updateLastLoginDate(@Param("account") String account, @Param("loginTime") Date loginTime);
  
  /**
   * 通过account查找后台用户
   * @param account
   * @return
   */
  UserEntity findByAccount(String account);
  
  /**
   * 根据账号和用户名查询用户
   * @Title: findByAccountAndUserName
   * @date:  2019年3月27日 下午6:32:59
   * @param: @param account
   * @param: @param userName
   * @param: @return
   * @return: UserEntity  
   * @author: fanda
   */
  UserEntity findByAccountAndUserName(String account, String userName);
}
