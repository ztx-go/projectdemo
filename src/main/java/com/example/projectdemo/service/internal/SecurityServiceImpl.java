package com.example.projectdemo.service.internal;

import com.example.projectdemo.entity.CompetenceEntity;
import com.example.projectdemo.entity.RoleEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.CompetenceRepository;
import com.example.projectdemo.repository.RoleRepository;
import com.example.projectdemo.repository.SecurityRepository;
import com.example.projectdemo.repository.UserRepository;
import com.example.projectdemo.service.SecurityService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 和权限绑定相关的接口实现在这里
 * 
 * @author yinwenjie
 */
@Service("SecurityServiceImpl")
public class SecurityServiceImpl implements SecurityService {

  @Autowired
  private SecurityRepository securityRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CompetenceRepository competenceRepository;

  @Override
  @Transactional
  public void bindRoleForOperator(String roleId, String userId) {
    Validate.notEmpty(roleId, "roleId not be empty!");
    Validate.notEmpty(userId, "userId not be empty!");
    this.checkRoleExist(roleId);
    this.checkOperatorExist(userId);

    // 如果已经有绑定信息，则不予许再进行绑定
    UserEntity oldBind = this.securityRepository.findUserByRoleIdAndUserId(roleId, userId);
    if (oldBind != null) {
      throw new IllegalArgumentException("该后台用户和角色已经有绑定关系，不能重复绑定!");
    }

    // 进行绑定
    this.securityRepository.bindRoleForUser(roleId, userId);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.vanda.fourthPayment.manager.service.SecurityService#bindRoleForCompetences(java.lang.String
   * , java.lang.String[])
   */
  @Override
  @Transactional
  public void bindRoleForCompetences(String roleId, String[] competenceIds) {
    Validate.notEmpty(roleId, "roleId not be empty!");
    Validate.notEmpty(competenceIds, "competenceId not be empty!");
    this.checkRoleExist(roleId);

    // 依次进行角色和功能的绑定
    for (String competenceId : competenceIds) {
      this.checkCompetenceExist(competenceId);
      // 如果已经有绑定信息，则不予许再进行绑定
      CompetenceEntity oldBind =
          this.securityRepository.findCompetenceByRoleIdAndCompetenceId(roleId, competenceId);
      if (oldBind != null) {
        throw new IllegalArgumentException("该系统功能和角色已经有绑定关系，不能重复绑定!");
      }
      // 进行绑定
      this.securityRepository.bindRoleAndCompetence(roleId, competenceId);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.vanda.fourthPayment.manager.service.SecurityService#unbindRoleForSystemUser(java.lang.String
   * , java.lang.String)
   */
  @Override
  @Transactional
  public void unbindRoleForOperator(String roleId, String userId) {
    Validate.notEmpty(roleId, "roleId not be empty!");
    Validate.notEmpty(userId, "userId not be empty!");
    this.checkRoleExist(roleId);
    UserEntity user = this.checkOperatorExist(userId);
    Validate.isTrue(!"admin".equals(user.getAccount()), "超级管理员的角色不能被解除");

    // 解除绑定
    this.securityRepository.unbindRoleForUser(roleId, userId);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.vanda.fourthPayment.manager.service.SecurityService#unbindRoleForCompetences(java.lang.
   * String, java.lang.String[])
   */
  @Override
  @Transactional
  public void unbindRoleForCompetences(String roleId, String[] competenceIds) {
    Validate.notEmpty(roleId, "roleId not be empty!");
    Validate.notEmpty(competenceIds, "competenceId not be empty!");
    this.checkRoleExist(roleId);
    // 依次解除绑定
    for (String competenceId : competenceIds) {
      this.checkCompetenceExist(competenceId);
      // 解除绑定
      this.securityRepository.unbindRoleAndCompetence(roleId, competenceId);
    }
  }

  /**
   * 确定有这样一个角色
   * 
   * @param roleId
   */
  private void checkRoleExist(String roleId) {
    // RoleEntity currentRole = this.roleRepository.findOne(roleId);
    RoleEntity currentRole = this.roleRepository.getOne(roleId);
    if (currentRole == null) {
      throw new IllegalArgumentException("没有发现指定的角色信息,请检查！");
    }
  }

  /**
   * 确定有这样一个后台用户
   * 
   * @param operatorId
   */
  private UserEntity checkOperatorExist(String operatorId) {
    // UserEntity operatorUser = this.userRepository.findOne(operatorId);
    UserEntity operatorUser = this.userRepository.getOne(operatorId);
    if (operatorUser == null) {
      throw new IllegalArgumentException("没有发现指定的后台用户信息,请检查！");
    }
    return operatorUser;
  }

  /**
   * 确定有这样一个功能
   * 
   * @param competenceId
   */
  private void checkCompetenceExist(String competenceId) {
    // CompetenceEntity competence = this.competenceRepository.findOne(competenceId);
    CompetenceEntity competence = this.competenceRepository.getOne(competenceId);
    if (competence == null) {
      throw new IllegalArgumentException("没有发现指定的功能信息,请检查！");
    }
  }

}
