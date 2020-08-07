package com.example.projectdemo.repository;

import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.entity.RoleEntity;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 *
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<RoleEntity, String>, JpaSpecificationExecutor<RoleEntity> {

	/**
	 * 查询指定的功能描述所绑定的角色信息
	 * 
	 * @param competenceId 功能描述信息
	 * @return
	 */
	@Query("from RoleEntity r left join fetch r.competences c where c.id = :competenceId")
	Set<RoleEntity> findByCompetenceId(@Param("competenceId") String competenceId);

//	/**
//	 * 查询指定的部门所绑定的角色信息(指定的用户)
//	 * 
//	 * @param userAccount 注意用户名必须完成包括域信息，例如yinwenjie@vanda.com、user@zkx.com
//	 * @return
//	 */
//	@Query("from RoleEntity r left join fetch r.ldapusers l where l.ldapName = :userAccount and l.nodeType = 1 and l.del = false")
//	Set<RoleEntity> findByUserAccount(@Param("userAccount") String userAccount);

	/**
	 * 查询指定的用户所绑定的角色信息(指定的用户)
	 * 
	 * @param userAccount 注意用户名必须完成包括域信息，例如yinwenjie@vanda.com、user@zkx.com
	 * @return
	 */
	@Query("from RoleEntity r left join fetch r.users l where l.account = :userAccount and l.status = STATUS_NORMAL")
	Set<RoleEntity> findByUserAccount(@Param("userAccount") String userAccount);

//	@Query("from RoleEntity r left join fetch r.ldapusers l where l.ldapName = :userAccount and l.nodeType = 1 and l.del = false and l.status=:status")
//	Set<RoleEntity> findByUserAccountAndStatus(@Param("userAccount") String userAccount,
//			@Param("status") UseStatus status);

	@Query("from RoleEntity r left join fetch r.users l where l.userName = :userAccount and l.status= :status ")
	Set<RoleEntity> findByUserAccountAndStatus(@Param("userAccount") String userAccount,
                                               @Param("status") UseStatus status);

//  /**
//   * 查询指定的部门所绑定的角色信息(指定的部门信息)
//   *
//   * @param userAccount 注意部门信息必须完整，例如ou=jiaodao,ou=nianji1
//   * @return
//   */
//  @Query("from RoleEntity r left join fetch r.ldapusers l where l.ldapName = :orgs and l.nodeType = 2")
//	Set<RoleEntity> findByOrgs(@Param("orgs") String orgs);

	/**
	 * 查询指定的用户绑定的角色信息
	 *
	 * @param userAccount 注意部门信息必须完整，例如ou=jiaodao,ou=nianji1
	 * @return
	 */
	@Query("from RoleEntity r left join fetch r.users l where l.userName = :orgs and l.status =1")
	Set<RoleEntity> findByOrgs(@Param("orgs") String orgs);

	/**
	 * 查询符合角色状态的信息
	 *
	 * @param useStatus
	 * @return
	 */
	@Query("from RoleEntity r  where r.status = :useStatus")
	List<RoleEntity> findByStatus(@Param("useStatus") UseStatus useStatus);

	/**
	 * 查询符合name的角色信息
	 *
	 * @param name
	 * @return
	 */
	RoleEntity findByName(String name);

	/**
	 * 通过ldap id 查询 他所拥有的角色信息
	 *
	 * @param id
	 * @return
	 */
	@Query("from RoleEntity r left join fetch r.users u where u.id = :id")
	Set<RoleEntity> findLdapRolesById(@Param("id") String id);

	/**
	 * 形成角色和功能url的绑定关系
	 */
	@Modifying
	@Query(value = "insert into role_competence (competence_id,role_id) values (:competenceId , :roleId)", nativeQuery = true)
	public void bindRoleAndCompetence(@Param("roleId") String roleId, @Param("competenceId") String competenceId);

	/**
	 * 解除角色和功能url的绑定关系
	 */
	@Modifying
	@Query(value = "delete from role_competence where role_id = :roleId and competence_id = :competenceId", nativeQuery = true)
	public void unbindRoleAndCompetence(@Param("roleId") String roleId, @Param("competenceId") String competenceId);

//	/**
//	 * 该方法可以确定指定的角色和指定的ldapNode是否已有绑定关系
//	 *
//	 * @param roleId
//	 * @param ldapNodeId
//	 * @return
//	 */
//	@Query("from LdapNodeEntity a left join fetch a.roles r where a.id = :ldapNodeId and r.id = :roleId")
//	LdapNodeEntity findLdapNodeByRoleIdAndLdapNodeId(@Param("roleId") String roleId,
//			@Param("ldapNodeId") String ldapNodeId);


	/**
	 * 该方法可以确定指定的角色和指定的ldapNode是否已有绑定关系
	 *
	 * @param roleId
	 * @param ldapNodeId
	 * @return
	 */
	@Query("from UserEntity a left join fetch a.roles r where a.id = :ldapNodeId and r.id = :roleId")
	UserEntity findLdapNodeByRoleIdAndLdapNodeId(@Param("roleId") String roleId,
												 @Param("ldapNodeId") String ldapNodeId);

//	/**
//	 * 形成角色和ldap的绑定关系（注意，不能重复绑定）
//	 */
//	@Modifying
//	@Query(value = "insert into role_ldapuser (ldapnode_id,role_id) values (:ldapNodeId , :roleId)", nativeQuery = true)
//	void bindRoleForLdapNode(@Param("roleId") String roleId, @Param("ldapNodeId") String ldapNodeId);

	/**
	 * 形成角色和ldap的绑定关系（注意，不能重复绑定）
	 */
	@Modifying
	@Query(value = "insert into role_user (role_id,user_id) values (:roleId , :userId)", nativeQuery = true)
	void bindRoleForLdapNode(@Param("roleId") String roleId, @Param("userId") String userId);

//	/**
//	 * 解绑角色和ldapNode的关系
//	 *
//	 * @param roleId
//	 * @param ldapNodeId
//	 */
//	@Modifying
//	@Query(value = "delete from role_ldapuser where role_id = :roleId and ldapnode_id = :ldapNodeId", nativeQuery = true)
//	void unbindRoleForLdapNode(@Param("roleId") String roleId, @Param("ldapNodeId") String ldapNodeId);

	/**
	 * 解绑角色和ldapNode的关系
	 *
	 * @param roleId
	 * @param ldapNodeId
	 */
	@Modifying
	@Query(value = "delete from role_user where role_id = :roleId and user_id = :ldapNodeId", nativeQuery = true)
	void unbindRoleForLdapNode(@Param("roleId") String roleId, @Param("ldapNodeId") String ldapNodeId);

  /**
   * 根据管理员id和状态查询角色
   *
   * @param id
   * @param statusNormal
   * @return
   */
  @Query("from RoleEntity r left join fetch r.users a where a.id = :operatorId and r.status =:statusNormal")
  List<RoleEntity> findByOperatorIdAndStatus(@Param("operatorId") String id,
                                             @Param("statusNormal") UseStatus statusNormal);
}
