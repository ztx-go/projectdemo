package com.example.projectdemo.configuration;

import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.entity.OperatorEntity;
import com.example.projectdemo.entity.RoleEntity;
import com.example.projectdemo.service.OperatorService;
import com.example.projectdemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 
 * @Description:数据初始化配置，以保证整个系统在保证最小化数据完成性的基础上能够使用
 *
 */
@Component
public class SystemInitConfig implements CommandLineRunner {
  @Autowired
  private RoleService roleService;
  @Autowired
  private OperatorService operatorService;

  /**
   * 初始化角色
   */
  private void initRole() {
    // RoleEntity roles = this.roleService.findByName("ADMIN");
    RoleEntity roles = this.roleService.findByName("ADMIN");
    if (roles == null) {
      RoleEntity role = new RoleEntity();
      role.setComment("管理员角色");
      role.setCreateDate(new Date());
      role.setName("ADMIN");
      // role.setStatus(1);
      role.setStatus(UseStatus.STATUS_NORMAL);
      this.roleService.addRole(role,null);
    }

  }

  /**
   * 初始化系统管理员
   */
  private void initAdmin() {
    /*
     * 保证系统中至少有一个系统管理员
     */
    OperatorEntity admin = this.operatorService.findByAccount("admin");
    if (admin == null) {
      this.operatorService.createByInit();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
   */
  @Override
  public void run(String... args) throws Exception {
    this.initRole();
    this.initAdmin();
  }

}
