package com.example.projectdemo.repository;

import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.entity.CompetenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yinwenjie
 */
@Repository("competenceRepository")
public interface CompetenceRepository
    extends
        JpaRepository<CompetenceEntity, String>,
        JpaSpecificationExecutor<CompetenceEntity> {
  /**
   * 查询为当前URL设置的角色信息，注意，这里没有通过method进行过滤
   * 
   * @param resource
   * @return
   */
  List<CompetenceEntity> findByResource(String resource);

  /**
   * 根据方法（methods）和路径（resource）查询.
   * 
   * @param methods 方法
   * @param resource 路径
   * @return int 查询的总条数
   */
  @Query(value = "SELECT count(*) FROM eb_competence WHERE methods = :methods AND resource = :resource", nativeQuery = true)
  int findByMethodsAndResource(@Param("methods") String methods,
                               @Param("resource") String resource);

  /**
   * 根据方法（methods）和路径（resource）以及权限id查询（排除自身，即不等于该id）.
   *
   * @param methods 方法
   * @param resource 路径
   * @param id 权限id
   * @return int 查询的总条数
   */
  @Query(value = "SELECT count(*) FROM eb_competence WHERE methods = :methods AND resource = :resource AND id != :id", nativeQuery = true)
  int findByMethodsAndResourceAndId(@Param("methods") String methods,
                                    @Param("resource") String resource, @Param("id") String id);

  /**
   * 按状态搜索 权限
   *
   * @param status
   * @return
   */
  List<CompetenceEntity> findByStatus(UseStatus status);

  /**
   * 按角色id查询 权限
   * @param roleId
   * @return
   */
  @Query("from CompetenceEntity c left join fetch c.roles r where r.id = :roleId")
  List<CompetenceEntity> findByRoleId(@Param("roleId") String roleId);

  /**
   * 查询指定的角色已绑定的功能信息，且这些功能状态符合查询的要求
   *
   * @param roleId
   * @return
   */
  @Query("from CompetenceEntity c left join fetch c.roles r where r.id = :roleId and c.status = :status")
  List<CompetenceEntity> findByRoleId(@Param("roleId") String roleId,
                                      @Param("status") UseStatus status);

  /**
   * 该方法可以确定指定的角色和指定的系统功能是否已有绑定关系
   * @param roleId 指定的角色
   * @param competenceId 指定的系统功能
   */
  @Query("from CompetenceEntity c left join fetch c.roles r where c.id = :competenceId and r.id = :roleId")
  public CompetenceEntity findCompetenceByRoleIdAndCompetenceId(@Param("roleId") String roleId, @Param("competenceId") String competenceId);
}
