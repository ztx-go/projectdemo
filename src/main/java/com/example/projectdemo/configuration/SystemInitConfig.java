package com.example.projectdemo.configuration;

import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.entity.RoleEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.service.RoleService;
import com.example.projectdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SystemInitConfig implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    private void initRole() {
        List<RoleEntity> roles = this.roleService.findByConditions("ADMIN", null);
        if (roles == null || roles.size() == 0) {
            RoleEntity role = new RoleEntity();
            role.setComment("管理员角色");
            role.setCreateDate(new Date());
            role.setName("ADMIN");
            role.setStatus(UseStatus.STATUS_NORMAL);
            this.roleService.addRole(role,null);
        }
    }

    private void initAdmin() {
        /*
         * 保证系统中至少有一个系统管理员
         */
        UserEntity admin = this.userService.findByAccount("admin");
        if (admin == null) {
            this.userService.createByInit();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        this.initRole();
        this.initAdmin();
    }
}
