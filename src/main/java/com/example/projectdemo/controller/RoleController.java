package com.example.projectdemo.controller;

import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.controller.model.ResponseModel;
import com.example.projectdemo.entity.RoleEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Api(value = "后台管理-角色管理接口")
@RestController
@RequestMapping("/v1/roles")
public class RoleController extends BaseController {
  @Autowired
  private RoleService roleService;

  // /**
  // * 查询一个指定的角色信息，只查询这个角色的基本信息
  // */
  // @ApiOperation(value = "查询一个指定的角色信息，只查询这个角色的基本信息")
  // @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
  // public ResponseModel findById(@PathVariable("roleId") String roleId) {
  // try {
  // Validate.notBlank(roleId, "角色roleId不能为空");
  // RoleEntity currentRole = this.roleService.findRoleById(roleId);
  // if (currentRole == null) {
  // throw new IllegalArgumentException(roleId + "的角色不存在!");
  // }
  //
  // return this.buildHttpReslut(currentRole, "competences", "ldapusers");
  // } catch (Exception e) {
  // return this.buildHttpReslutForException(e);
  // }
  // }

  /**
   * 查询一个指定的角色信息，只查询这个角色的基本信息
   */
  @ApiOperation(value = "查询一个指定的角色信息，只查询这个角色的基本信息")
  @RequestMapping(value = "/getById", method = RequestMethod.GET)
  public ResponseModel findById(String roleId) {
    try {
      Validate.notBlank(roleId, "角色roleId不能为空");
      RoleEntity currentRole = this.roleService.findRoleById(roleId);
      if (currentRole == null) {
        throw new IllegalArgumentException(roleId + "的角色不存在!");
      }

      return this.buildHttpReslut(currentRole, "competences", "users", "createUser", "modifyUser");
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  @ApiOperation(value = "获取当前用户拥有的角色")
  @RequestMapping(value = "/findCurrentPrincipalRoles", method = RequestMethod.GET)
  public ResponseModel findCurrentPrincipalRoles(Principal opUser) {

    try {
      UserEntity operator = verifyLdapNodeLogin(opUser);
      List<RoleEntity> roles;
//      if(operator.getAccount().equals("test")) {
//        roles = roleService.findAll();
//      }else {
//        roles =
//            roleService.findByOperatorIdAndStatus(operator.getId(), UseStatus.STATUS_NORMAL);
//      }
//      if (roles == null || roles.isEmpty()) {
//        return this.buildHttpReslut(roles);
//      }
      /**
       * 当前用户是系统管理员角色，获取所有可用角色<br>.
       * 否则，获取当前用户可用角色.
       */
      roles = new ArrayList<RoleEntity>(operator.getRoles());
      for (RoleEntity r : roles) {
        if (StringUtils.equals(r.getName(), "ADMIN")) {
          roles = roleService.findByStatus(UseStatus.STATUS_NORMAL);
          break;
        }
      }
      return this.buildHttpReslut(roles, "competences", "createUser", "modifyUser", "users");
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  /**
   * 条件分页查询角色列表信息，只查询基本信息。
   * 
   * @param roleName 角色名称
   * @param status   状态
   * @param pageable 分页
   * @return Page<RoleEntity>
   */
  @ApiOperation(value = "条件分页查询角色列表", notes = "根据查询条件分页查询角色列表信息")
  @RequestMapping(value = "/findByConditions", method = {RequestMethod.GET})
  public ResponseModel findByConditions(
      @ApiParam(value = "角色名称（模糊查询）") @RequestParam(required = false) String roleName,
      @ApiParam(value = "状态（正常/禁用）") @RequestParam(required = false) UseStatus status,
      @ApiParam(value = "自动注入分页对象") @PageableDefault(value = 20, sort = {
          "createDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
    try {
      Page<RoleEntity> list = roleService.findByConditions(roleName, status, pageable);
      levelOneAssociation(list.getContent());
      return this.buildHttpReslut(list, "competences", "users");
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  /**
   * 条件分页查询角色列表信息，只查询基本信息。
   * 
   * @param roleName 角色名称
   * @param status   状态
   * @param pageable 分页
   * @return Page<RoleEntity>
   */
  @ApiOperation(value = "条件分页查询后台角色列表", notes = "根据查询条件分页查询角色列表信息")
  @RequestMapping(value = "/getByConditions", method = {RequestMethod.GET})
  public ResponseModel getByConditions(@ApiParam(value = "角色名称（模糊查询）") String roleName,
      @ApiParam(value = "状态（正常/禁用）") UseStatus status,
      @ApiParam(value = "自动注入分页对象") @PageableDefault(value = 15, sort = {
          "createDate"}, direction = Sort.Direction.DESC) Pageable pageable) {

    Page<RoleEntity> list = roleService.getByConditions(roleName, status, pageable);
    // 去除关联关系
    // list.map(new Converter<RoleEntity, RoleEntity>() {
    //   @Override
    //   public RoleEntity convert(RoleEntity source) {
    //     return source;
    //   }
    // });
    return this.buildHttpReslut(list, "competences", "users");
  }

  /**
   * 添加一个角色信息
   * 
   * @param role
   * @return 创建后的角色基本信息将会被返回
   */
  @ApiOperation(value = "添加一个角色信息")
  @RequestMapping(value = "/", method = RequestMethod.POST)
  public ResponseModel addRole(@RequestBody RoleEntity role, Principal principal) {
    try {
      if (role == null) {
        throw new IllegalArgumentException("新增角色时，角色信息不能为空!");
      }
      // 求得当前操作的操作者
      UserEntity creatUser = this.verifyLdapNodeLogin(principal);
      Validate.notNull(creatUser,"操作者不能为空！");
      RoleEntity currentRole = roleService.addRole(role,creatUser);

      return this.buildHttpReslut(currentRole, "competences", "users","createUser","modifyUser");
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  /**
   * 修改一个指定的角色信息，注意配置在roles.deleteDeny属性的信息不能进行修改操作<br>
   * 且指定的一个角色只能修改角色的comment信息
   * 
   * @param role 指定的修改信息
   */
  @ApiOperation(value = "修改一个指定的角色信息，注意配置在roles.deleteDeny属性的信息不能进行修改操作。且指定的一个角色只能修改角色的comment信息")
  @RequestMapping(value = "/", method = RequestMethod.PATCH)
  public ResponseModel updateRole(@RequestBody RoleEntity role, Principal opUser) {
    try {
      if (role == null) {
        throw new IllegalArgumentException("更新角色时，角色信息不能为空!");
      }
      UserEntity createUser = this.verifyLdapNodeLogin(opUser);
      Validate.notNull(createUser,"操作者不能为空！");
      RoleEntity currentRole = this.roleService.updateRole(role,createUser);
      if (currentRole == null) {
        throw new IllegalArgumentException("角色不存在!");
      }
      return this.buildHttpReslut();
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  /**
   * 禁用某一个指定的角色信息（相当于删除）<br>
   * 只是系统中不能真正的删除某一个角色，只能是将这个角色作废掉或者恢复正常状态
   * 
   * @param roleId
   * @return
   */
  @ApiOperation(value = "禁用某一个指定的角色信息（相当于删除）<br>" + "只是系统中不能真正的删除某一个角色，只能是将这个角色作废掉或者恢复正常状态")
  @RequestMapping(value = "/disable", method = RequestMethod.POST)
  public ResponseModel disableRole(@ApiParam(value = "角色id") String roleId, Principal opUser) {
    try {
      // 求得当前操作的操作者
      this.verifyLdapNodeLogin(opUser);
      RoleEntity currentRole = this.roleService.disableRole(roleId);

      return this.buildHttpReslut(currentRole, "competences", "users");
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  /**
   * 重新启用某一个指定的角色信息
   * 
   * @param roleId
   * @return
   */
  @ApiOperation(value = "重新启用某一个指定的角色信息")
  @RequestMapping(value = "/enable", method = RequestMethod.POST)
  public ResponseModel enableRole(@ApiParam(value = "角色id") String roleId, Principal opUser) {
    try {
      // 求得当前操作的操作者
      this.verifyLdapNodeLogin(opUser);
      RoleEntity currentRole = roleService.enableRole(roleId);

      return this.buildHttpReslut(currentRole, "competences", "users");
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  /**
   * 通过ldap id查询他所拥有的角色信息 .
   * 
   * @param id ldap id
   * @return
   */
  @ApiOperation(value = "通过ldap id查询他所拥有的角色信息", notes = "通过ldap id查询他所拥有的角色信息")
  @RequestMapping(value = "/findRolesById", method = {RequestMethod.GET})
  public ResponseModel findRolesById(@ApiParam(value = "后台用户id") @RequestParam String id) {
    try {
      Set<RoleEntity> nowList = roleService.findLdapRolesById(id);
      return this.buildHttpReslut(nowList, "competences", "users","createUser","modifyUser");
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  /**
   * 形成角色和功能url的绑定关系<br>
   * （可以将指定的一个角色一次绑定多个功能）
   * 
   * @param roleId
   * @param competenceIds
   */
  @ApiOperation(value = "形成角色和功能url的绑定关系（可以将指定的一个角色一次绑定多个功能）")
  @RequestMapping(value = "/bindRoleForCompetences", method = RequestMethod.POST)
  public ResponseModel bindRoleAndCompetences(String roleId, String[] competenceIds) {
    try {
      this.roleService.bindRoleForCompetences(roleId, competenceIds);
      return this.buildHttpReslut();
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  /**
   * 解绑角色和功能url的绑定关系<br>
   * （可以将指定的一个角色一次解绑多个功能）
   * 
   * @param roleId
   * @param competenceIds
   */
  @ApiOperation(value = "解绑角色和功能url的绑定关系（可以将指定的一个角色一次解绑多个功能）")
  @RequestMapping(value = "/unbindRoleForCompetences", method = RequestMethod.POST)
  public ResponseModel unbindRoleAndCompetences(String roleId, String[] competenceIds) {
    try {
      this.roleService.unbindRoleForCompetences(roleId, competenceIds);
      return this.buildHttpReslut();
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  /**
   * 形成角色和用户的绑定关系（注意，不能重复绑定） （可以将指定的多个角色一次绑定一个ldap）
   */
  @ApiOperation(value = "形成角色和ldapNode的绑定关系（注意，不能重复绑定）（可以将指定的多个角色一次绑定一个ldap）")
  @RequestMapping(value = "/bindRolesForLdapNode", method = RequestMethod.POST)
  public ResponseModel bindRolesForLdapNode(String[] roleIds, String ldapNodeId) {
    try {
      this.roleService.bindRolesForLdapNode(roleIds, ldapNodeId);
      return this.buildHttpReslut();
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }
  // /**
  // * 解绑角色和用户的绑定关系（注意，不能重复）
  // * （可以将指定的ldapNode的多个角色解绑）
  // */
  // @ApiOperation(value="形成角色和ldapNode的绑定关系（注意，不能重复绑定），（可以将指定的ldapNode的多个角色解绑）")
  // @RequestMapping(value="/unbindRolesForLdapNode" , method=RequestMethod.POST)
  // public ResponseModel unbindRolesForLdapNode(String[] roleIds , String ldapNodeId) {
  // try {
  // this.roleService.unbindRolesForLdapNode(roleIds, ldapNodeId);
  // return this.buildHttpReslut();
  // } catch (Exception e) {
  // return this.buildHttpReslutForException(e);
  // }
  // }

  /**
   * 解绑角色和用户的绑定关系（注意，不能重复） （可以将指定的ldapNode的多个角色解绑）
   */
  @ApiOperation(value = "形成角色和ldapNode的绑定关系（注意，不能重复绑定），（可以将指定的ldapNode的多个角色解绑）")
  @RequestMapping(value = "/unbindRolesForLdapNode", method = RequestMethod.POST)
  public ResponseModel unbindRolesForLdapNode(String[] roleIds, String ldapNodeId) {
    try {
      this.roleService.unbindRolesForLdapNode(roleIds, ldapNodeId);
      return this.buildHttpReslut();
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  /**
   * 该接口方法用于查询符合指定状态的角色信息，只返回角色的基本信息，没有任何的关联信息但是包括了可能存在的修改者信息
   * 
   * @param status
   * @return
   */
  @ApiOperation(value = "该接口方法用于查询符合指定状态的角色信息，只返回角色的基本信息，没有任何的关联信息但是包括了可能存在的修改者信息。")
  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseModel findRoles(Integer status) {
    try {
      UseStatus statusType = null;
      if (status == 1) {
        statusType = UseStatus.STATUS_NORMAL;
      } else {
        statusType = UseStatus.STATUS_DISABLE;
      }
      List<RoleEntity> roles = this.roleService.findByStatus(statusType);
      if (roles == null || roles.isEmpty()) {
        roles = Collections.emptyList();
        return this.buildHttpReslut(roles);
      }
      return this.buildHttpReslut(roles, "competences", "users");
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  /**
   * 该接口方法用于查询所有角色信息，无论它们的状态如何。<br>
   * 只返回角色的基本信息，没有任何的关联信息但是包括了可能的修改者信息"
   * 
   * @param status
   * @return
   */
  @ApiOperation(value = "该接口方法用于查询所有角色信息，无论它们的状态如何。<br>" + "只返回角色的基本信息，没有任何的关联信息但是包括了可能的修改者信息")
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public ResponseModel findAllRoles() {
    try {
      List<RoleEntity> roles = this.roleService.findAll();
      if (roles == null || roles.isEmpty()) {
        roles = Collections.emptyList();
        return this.buildHttpReslut(roles);
      }
      return this.buildHttpReslut(roles, "competences", "users","createUser","modifyUser");
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  private void levelOneAssociation(List<RoleEntity> list) {
    if (null != list && list.size() > 0) {
      for (RoleEntity role : list) {
        vetViewDate(role);
      }
    }
  }

  private void vetViewDate(RoleEntity role) {

    UserEntity createUser = new UserEntity();
    if (role.getCreateUser() == null) {
      role.setCreateUser(null);
    } else {
      createUser.setId(role.getCreateUser().getId());
      createUser.setUserName(role.getCreateUser().getUserName());
      role.setCreateUser(createUser);
    }

    UserEntity modifyUser = new UserEntity();
    if (role.getModifyUser() == null) {
      role.setModifyUser(null);
    } else {
      modifyUser.setId(role.getModifyUser().getId());
      modifyUser.setUserName(role.getModifyUser().getUserName());
      role.setModifyUser(modifyUser);
    }

  }

}
