package com.example.projectdemo.repository;

import com.example.projectdemo.entity.CompetenceEntity;
import com.example.projectdemo.entity.RoleEntity;
import com.example.projectdemo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("securityRepository")
public interface SecurityRepository extends JpaRepository<RoleEntity, String> {

    /**
     * 形成角色和后台用户的绑定关系（注意，不能重复绑定）
     */
    @Modifying
    @Query(value = "insert into role_user (user_id,role_id) values (:userId , :roleId)", nativeQuery = true)
    void bindRoleForUser(@Param("roleId") String roleId, @Param("userId") String userId);

    /**
     * 形成角色和功能url的绑定关系
     */
    @Modifying
    @Query(value = "insert into role_competence (competence_id,role_id) values (:competenceId , :roleId)", nativeQuery = true)
    void bindRoleAndCompetence(@Param("roleId") String roleId, @Param("competenceId") String competenceId);

    /**
     * 该方法可以确定指定的角色和指定的系统功能是否已有绑定关系
     */
    @Query("from CompetenceEntity c left join fetch c.roles r where c.id = :competenceId and r.id = :roleId")
    CompetenceEntity findCompetenceByRoleIdAndCompetenceId(@Param("roleId") String roleId, @Param("competenceId") String competenceId);

    /**
     * 该方法可以确定指定的角色和指定的后台管理员是否已有绑定关系
     */
    @Query("from UserEntity u left join fetch u.roles r where u.id = :userId and r.id = :roleId")
    UserEntity findUserByRoleIdAndUserId(@Param("roleId") String roleId, @Param("userId") String userId);

    /**
     * 解除角色和后台用户的绑定关系
     */
    @Modifying
    @Query(value = "delete from role_user where role_id = :roleId and user_id = :userId", nativeQuery = true)
    void unbindRoleForUser(@Param("roleId") String roleId, @Param("userId") String userId);

    /**
     * 解除角色和功能url的绑定关系
     */
    @Modifying
    @Query(value = "delete from role_competence where role_id = :roleId and competence_id = :competenceId", nativeQuery = true)
    void unbindRoleAndCompetence(@Param("roleId") String roleId, @Param("competenceId") String competenceId);
}