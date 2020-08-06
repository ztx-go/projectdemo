package com.example.projectdemo.controller;

import com.example.projectdemo.common.enums.EntityType;
import com.example.projectdemo.common.enums.StreetType;
import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.controller.model.ResponseModel;
import com.example.projectdemo.entity.MapTreeEntity;
import com.example.projectdemo.entity.RoleEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.service.UserService;
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
import java.util.*;

/**
 * 用户管理控制层
 * 
 * @ClassName: UserController
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: fanda
 * @date: 2019年3月27日 下午11:11:23
 */
@Api(value = "UserController")
@RestController
@RequestMapping("/v1/user")
public class UserController extends BaseController {

  @Autowired
  UserService userService;

  @ApiOperation(value = "通过id查询用户信息", notes = "通过id查询用户信息")
  @RequestMapping(value = "/findById", method = RequestMethod.GET)
  public ResponseModel findById(@ApiParam(value = "账号id") String id, Principal logUser) {
    try {
   // 验证操作者是否登陆
      UserEntity op = verifyLdapNodeLogin(logUser);
      Validate.notNull(op,"操作者不能为空！");
      UserEntity user = userService.getById(id,op,EntityType.USERENTITY);
      // 去除关联
      levelOneAssociation(user);
      return this.buildHttpReslut(user);
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  @ApiOperation(value = "添加用户信息 ", notes = "添加用户信息")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public ResponseModel create(@ApiParam(value = "用户对象") @RequestBody UserEntity operatortory,
      @ApiParam(value = "当前登录对象") Principal opUser) {
    try {
      UserEntity user = verifyLdapNodeLogin(opUser);
      if (operatortory == null) {
        throw new IllegalArgumentException("用户信息不能为空！");
      }
      operatortory.setCreateUser(user);
      userService.create(operatortory,user,EntityType.USERENTITY);
      return this.buildHttpReslut();
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
  @RequestMapping(value = "/update", method = RequestMethod.PATCH)
  public ResponseModel update(@ApiParam(value = "用户对象") @RequestBody UserEntity operatortory,
      @ApiParam(value = "当前登录对象") Principal opUser) {
    try {
      UserEntity user = verifyLdapNodeLogin(opUser);
      if (operatortory == null) {
        throw new IllegalArgumentException("用户不能为空！");
      }
      operatortory.setModifyUser(user);
      userService.update(operatortory,user,EntityType.USERENTITY);
      return this.buildHttpReslut();
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  @ApiOperation(value = "禁用或者启用用户", notes = "根据参数（用户id）和操作标识（flag：true为启用，false为禁用）")
  @RequestMapping(value = "/disableOrUndisable", method = RequestMethod.PATCH)
  public ResponseModel disableOrUndisable(@ApiParam(value = "用户id") @RequestParam String id,
      @ApiParam(value = "操作标识（flag：true为启用，false为禁用）") @RequestParam Boolean flag,
      @ApiParam(value = "当前登录用户") Principal opUser) {
    try {
      UserEntity user = verifyLdapNodeLogin(opUser);
      userService.disableOrUsable(id, flag, user);
      return this.buildHttpReslut();
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  @ApiOperation(value = "条件分页查询用户信息", notes = "根据查询条件分页查询用户信息")
  @RequestMapping(value = "/getByCondition", method = {RequestMethod.GET})
  public ResponseModel findByParams(@ApiParam(value = "账号") String account,
      @ApiParam(value = "名字") String name, @ApiParam(value = "状态（正常/禁用）") UseStatus status,
      @ApiParam(value = "部门机构") String depart,
      @ApiParam(value = "街道类型") StreetType streetType,
      @ApiParam(value = "是否重置密码") Boolean isResetPsd,
      @ApiParam(value = "自动注入分页对象") @PageableDefault(value = 15, sort = {
          "createDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
    try {
      Map<String, Object> params = new HashMap<String, Object>();
      if (StringUtils.isNotEmpty(account)) {
        params.put("account", account);
      }
      if (StringUtils.isNotEmpty(name)) {
        params.put("name", name);
      }
      if (null != status) {
        params.put("status", status);
      }
      if (StringUtils.isNotEmpty(depart)) {
        params.put("depart", depart);
      }
      if (null != streetType) {
        params.put("streetType", streetType);
      }
      if (null != isResetPsd) {
        params.put("isResetPsd", isResetPsd);
      }
      Page<UserEntity> users = userService.findByUser(params, pageable);
      // 去除关联
      levelOneAssociation(users.getContent());
      return this.buildHttpReslut(users, "roles");
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  @ApiOperation(value = "根据用户id重置密码", notes = "根据用户id重置密码")
  @RequestMapping(value = "/applyResetPsd", method = RequestMethod.GET)
  public ResponseModel applyResetPsd(@ApiParam(value = "账号") String account,
      @ApiParam(value = "用户名") String name, @ApiParam(value = "手机号码") String telephone) {
    try {
      userService.applyRestPsd(account, name, telephone);
      return this.buildHttpReslut();
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  @ApiOperation(value = "根据用户id重置密码", notes = "根据用户id重置密码")
  @RequestMapping(value = "/resetPsd", method = RequestMethod.GET)
  public ResponseModel resetPsd(@ApiParam(value = "账号id") String id,
      @ApiParam(value = "当前登录用户") Principal opUser) {
    try {
      UserEntity user = this.verifyLdapNodeLogin(opUser);
      userService.resetPsd(id, user);
      return this.buildHttpReslut();
    } catch (Exception e) {
      return this.buildHttpReslutForException(e);
    }
  }

  /**
   * 组装用户集合创建人、修改人
   * 
   * @Title: levelOneAssociation
   * @date: 2019年3月27日 下午10:53:20
   * @param: @param users
   * @return: void
   * @author: fanda
   */
  private void levelOneAssociation(List<UserEntity> users) {

    for (UserEntity user : users) {
      if (null != user.getDepart()) {
        MapTreeEntity map = new MapTreeEntity();
        map.setId(user.getDepart().getId());
        map.setName(user.getDepart().getName());
        user.setDepart(map);
      }

      if (null != user.getCreateUser()) {
        UserEntity creator = new UserEntity();
        creator.setId(user.getCreateUser().getId());
        creator.setUserName(user.getCreateUser().getUserName());
        user.setCreateUser(creator);
      }
      if (null != user.getModifyUser()) {
        UserEntity modifier = new UserEntity();
        modifier.setId(user.getModifyUser().getId());
        modifier.setUserName(user.getModifyUser().getUserName());
        user.setModifyUser(modifier);
      }
    }

  }

  /**
   * 组装用户创建人、修改人、关联角色
   * 
   * @Title: levelOneAssociation
   * @date: 2019年3月27日 下午10:35:39
   * @param: @param user
   * @return: void
   * @author: fanda
   */
  private void levelOneAssociation(UserEntity user) {

    if (null != user.getDepart()) {
      MapTreeEntity map = new MapTreeEntity();
      map.setId(user.getDepart().getId());
      map.setName(user.getDepart().getName());
      user.setDepart(map);
    }

    if (null != user.getCreateUser()) {
      UserEntity creator = new UserEntity();
      creator.setId(user.getCreateUser().getId());
      creator.setUserName(user.getCreateUser().getUserName());
      user.setCreateUser(creator);
    }
    if (null != user.getModifyUser()) {
      UserEntity modifier = new UserEntity();
      modifier.setId(user.getModifyUser().getId());
      modifier.setUserName(user.getModifyUser().getUserName());
      user.setModifyUser(modifier);
    }
    if (user.getRoles().size() > 0) {
      Set<RoleEntity> roles = new HashSet<RoleEntity>();
      for (RoleEntity role : user.getRoles()) {
        RoleEntity newRole = new RoleEntity();
        newRole.setId(role.getId());
        newRole.setName(role.getName());
        roles.add(newRole);
      }
      user.setRoles(roles);
    }
  }
}
