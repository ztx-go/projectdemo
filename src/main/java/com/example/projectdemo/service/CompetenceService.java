package com.example.projectdemo.service;

import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.entity.CompetenceEntity;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 权限service接口.
 *
 */
public interface CompetenceService {
	/**
	 * 查询为当前URL设置的功能信息，注意，这里没有通过method进行过滤.
	 * 
	 * @param resource 权限串
	 * @return List<CompetenceEntity>
	 */
	List<CompetenceEntity> findByResource(String resource);

	/**
	 * 新增功能信息
	 * 
	 * @param competence
	 * @return
	 */
	CompetenceEntity addCompetence(CompetenceEntity competence, UserEntity user);

	/**
	 * 修改功能信息
	 *
	 * @param competence
	 * @return
	 */
	CompetenceEntity updateCompetence(CompetenceEntity competence, UserEntity user);

	/**
	 * 启用或者禁用功能 true 启用 false 禁用
	 *
	 * @param id
	 * @param flag
	 * @return
	 */
	CompetenceEntity diableOrUndisable(String id, Boolean flag);

	/**
	 * 查询是否已存在功能<br>
	 * 当id不为空时 根据方法（methods）和路径（resource）以及权限id查询（排除自身，即不等于该id）.<br>
	 * 当id为空时 根据方法（methods）和路径（resource）
	 *
	 * @param methods
	 * @param resource
	 * @param id
	 * @return
	 */
	boolean isExit(String methods, String resource, String id);

	/**
	 * 通过id查询指定功能
	 *
	 * @param id
	 * @return
	 */
	CompetenceEntity findById(String id);

	/**
	 * 分页 按条件搜索
	 *
	 * @param methods
	 * @param comment
	 * @param resource
	 * @param useStatus
	 * @param pageable
	 * @return
	 */
	Page<CompetenceEntity> findByConditions(String methods, String comment, String resource, UseStatus useStatus,
											Pageable pageable);

	/**
	 * 
	 * 查询所有正常的权限
	 * 
	 * @return
	 */
	List<CompetenceEntity> findAllNormal();

	/**
	 * 按角色id查询权限
	 * 
	 * @param roleId
	 * @return
	 */
	List<CompetenceEntity> findByRoleId(String roleId);

	/**
	 * 该方法可以确定指定的角色和指定的系统功能是否已有绑定关系
	 * 
	 * @param roleId
	 * @param competenceId
	 * @return
	 */
	CompetenceEntity findCompetenceByRoleIdAndCompetenceId(String roleId, String competenceId);

	/**
	 * 基于功能的中文说明信息，确定当前登录者针对这个功能有没有被授权
	 * 
	 * @param account    当前登录的用户账户信息
	 * @param comment 功能中文名
	 * @return 如果被授权则返回true；其它情况返回false
	 */
	Boolean findCompetencePermissionByComment(String account, String comment);

	/**
	 * 基于功能的URL信息，确定当前登录者针对这个功能有没有被授权
	 * 
	 * @param account 当前登录的用户账户信息
	 * @param url     功能的url信息，权限URL串。注意如果URL信息中有参数信息。则使用“{param}”代替。例如：<br>
	 *                /vz/param4/{param}
	 * @param methods POST或者POST|GET|DELETE|PATCH等，传入的大小写没有关系，反正都会转为大写
	 * @return 如果被授权则返回true；其它情况返回false
	 */
	Boolean findCompetencePermissionByUrl(String account, String url, String methods);

	/**
	 * 基于功能的唯一编号信息，确定当前登录者针对这个功能有没有被授权
	 * 
	 * @param account         当前登录的用户账户信息
	 * @param competenceId 指定的功能编号信息
	 * @return 如果被授权则返回true；其它情况返回false
	 */
	Boolean findCompetencePermissionById(String account, String competenceId);
}
