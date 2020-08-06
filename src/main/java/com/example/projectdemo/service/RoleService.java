package com.example.projectdemo.service;

import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.entity.RoleEntity;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface RoleService {

  /**
   * 查询指定的功能描述所绑定的角色信息(只包括角色基本信息)
   * 
   * @param competenceId 功能描述信息
   * @return
   */
  List<RoleEntity> findByCompetenceId(String competenceId);

  /**
   * 查询指定的部门所绑定的角色信息(指定的用户)(未删除，正常的用户)
   * 
   * @param userAccount 注意用户名必须完成包括域信息，例如yinwenjie@vanda.com、user@zkx.com
   * @return
   */
  Set<RoleEntity> findByUserAccount(@Param("userAccount") String userAccount);

  /**
   * 查询指定的部门所绑定的角色信息(指定的部门信息)
   * 
   * @param userAccount 注意部门信息必须完整，例如ou=jiaodao,ou=nianji1
   * @return
   */
  Set<RoleEntity> findByOrgs(@Param("orgs") String orgs);

  /**
   * 查询符合角色状态的信息
   * 
   * @param useStatus
   * @return
   */
  List<RoleEntity> findByStatus(UseStatus useStatus);

  /**
   * 查询指定的角色信息，按照角色的数据层编号查询
   * 
   * @param roleId 角色编号
   * @return
   */
  RoleEntity findRoleById(String roleId);
  
  /**
   * 通过后台用户id和角色状态查询 角色
   * @param id
   * @param statusNormal
   * @return
   */
  List<RoleEntity> findByOperatorIdAndStatus(String id, UseStatus statusNormal);

  /**
   * 按条件分页搜索
   * 
   * @param params
   * @param pageable
   * @return
   */
  public Page<RoleEntity> findByConditions(String roleName, UseStatus status, Pageable pageable);

  /**
   * 增加角色信息
   * 
   * @param role
   * @return
   */
  RoleEntity addRole(RoleEntity role, UserEntity creatUser);

  /**
   * 修改指定角色信息
   *
   * @param role
   * @return
   */
  RoleEntity updateRole(RoleEntity role, UserEntity modifyUser);

  /**
   * 禁用指定角色
   * 
   * @param roleId
   */
  RoleEntity disableRole(String roleId);

  /**
   * 启用指定角色
   * 
   * @param roleId
   */
  RoleEntity enableRole(String roleId);

  /**
   * 通过ldap id查询他所拥有的角色信息
   * 
   * @param id
   * @return
   */
  Set<RoleEntity> findLdapRolesById(String id);

  /**
   * 形成角色和功能url的绑定关系
   * 
   * @param roleId
   * @param competenceIds
   */
  void bindRoleForCompetences(String roleId, String[] competenceIds);

  /**
   * 解除角色和功能url的绑定关系<br>
   * 该方法将指定的角色，一次解绑多个功能
   */
  public void unbindRoleForCompetences(String roleId, String[] competenceIds);

  /**
   * 形成角色和 ldap的绑定关系
   * 
   * @param roleIds
   * @param ldapNodeId
   */
  void bindRolesForLdapNode(String[] roleIds, String ldapNodeId);

  /**
   * 解绑ldap和角色的绑定关系
   * 
   * @param roleIds
   * @param ldapNodeId
   */
  void unbindRolesForLdapNode(String[] roleIds, String ldapNodeId);

  /**
   * 查询目前系统中所有的角色信息，无论这些角色信息是否可用（但是只包括角色的基本信息）
   * 
   * @return
   */
  List<RoleEntity> findAll();

  /**
   * 条件分页查询
   * 
   * @param name
   * @param status
   * @param pageable
   * @return
   */
  Page<RoleEntity> getByConditions(String name, UseStatus status, Pageable pageable);

  /**
   * 条件查询
   * 
   * @param name
   * @param status
   * @return
   */
  List<RoleEntity> findByConditions(String name, UseStatus status);

  RoleEntity findByName(String name);
}
