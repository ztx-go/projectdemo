package com.example.projectdemo.service.internal;

import com.example.projectdemo.common.enums.EntityType;
import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.common.enums.UserType;
import com.example.projectdemo.entity.RoleEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.UserRepository;
import com.example.projectdemo.repository.internal.UserDao;
import com.example.projectdemo.service.MapTreeService;
import com.example.projectdemo.service.RoleService;
import com.example.projectdemo.service.SecurityService;
import com.example.projectdemo.service.UserService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 用户业务逻辑实现.
 * 
 */
@Service("userService")
public class UserServiceImpl implements UserService {

  // 重置密码
  private static final String RESETPSD = "123456";
  @Autowired
  private UserRepository userRepository;

  @Autowired
  MapTreeService mapTreeService;

  @Autowired
  private UserDao userDao;

  @Autowired
  Md5PasswordEncoder passwordEncoder;

  @Autowired
  RoleService roleService;

  @Autowired
  SecurityService securityService;

  @Override
  public void create(UserEntity user, UserEntity opter, EntityType enty) {
    Validate.notNull(user, "user对象不能为空!");
    Validate.notBlank(user.getAccount(), "账号不能为空！");
    String pwd = user.getPassword();
    // 验证密码格式是否输入正确
    // Validate.isTrue(StringValidateUtils.validStrByPattern(pwd, "[a-zA-Z\\d]{6,12}"),
    // "密码长度必须为6-12个字符，大小写英文字符或数字！");
    user.setPassword(passwordEncoder.encodePassword(pwd,null));
    if (user.getUserType() == null) {
      throw new IllegalAccessError("用户类型不能为空");
    }
    user.setIsResetPsd(false);
    userRepository.save(user);
  }

  @Override
  public UserEntity getById(String id, UserEntity user, EntityType enty) {
    Validate.notNull(id, "id不能为空!");
    // return userRepository.findOne(id);
    return userRepository.getOne(id);
  }

  @Override
  @Transactional
  public UserEntity disableOrUsable(String id, boolean flag, UserEntity user) {
    Validate.notBlank(id, "用户的逻辑主键不能为空");
    // UserEntity entity = userRepository.findOne(id);
    UserEntity entity = userRepository.getOne(id);
    Validate.notNull(entity, "id为%s的ldapNode不存在", id);
    entity.setModifyUser(user);
    entity.setModifyDate(new Date());
    if (flag) {
      entity.setStatus(UseStatus.STATUS_NORMAL);
    } else {
      entity.setStatus(UseStatus.STATUS_DISABLE);
    }
    return userRepository.saveAndFlush(entity);
  }

  @Override
  public Page<UserEntity> findByUser(Map<String, Object> params, Pageable pageable) {
    return userDao.findByUser(params, pageable);
  }

  @Override
  public void update(UserEntity user, UserEntity modefyUser, EntityType enty) {
    Validate.notNull(user, "用户不能为空");
    // 验证对象
    UserEntity oldUser = getById(user.getId(), null, EntityType.USERENTITY);
    Validate.notNull(oldUser, "id为" + user.getId() + "的对象不存在！");
    String oldPassword = oldUser.getPassword();
    oldUser.setUserName(user.getUserName());
    oldUser.setPassword(user.getPassword());
    // 账号
    oldUser.setAccount(user.getAccount());
    // 电话
    oldUser.setTelephone(user.getTelephone());
    // 修改人和修改时间
    oldUser.setModifyUser(user.getModifyUser());
    oldUser.setModifyDate(new Date());
    // 判断用户类型
    UserType userType = user.getUserType();
    Validate.notNull(userType, "用户类型不能为空！");
    oldUser.setUserType(userType);
    /**
     * 1.用户类型为部门人员，所属部门不为空，街道和楼宇置空.<br>
     * 2.用户类型为街道人员，所属街道不为空，部门和楼宇置空.<br>
     * 3.用户类型为楼宇人员，楼宇名称不为空，部门和街道置空.
     */
    if (userType == UserType.DEPART_STAFF) {
      Validate.notNull(user.getDepart(), "所属部门不能为空！");
      Validate.notBlank(user.getDepart().getId(), "所属部门不能为空");
      oldUser.setDepart(mapTreeService.findById(user.getDepart().getId()));
      oldUser.setStreetType(null);
      oldUser.setBuildName(null);
    }
    if (userType == UserType.STREET_STAFF) {
      Validate.notNull(user.getStreetType(), "所属街道不能为空！");
      oldUser.setStreetType(user.getStreetType());
      oldUser.setDepart(null);
      oldUser.setBuildName(null);
    }
    if (userType == UserType.BUILD_STAFF) {
      Validate.notBlank(user.getBuildName(), "楼宇名称不能为空！");
      oldUser.setBuildName(user.getBuildName());
      oldUser.setDepart(null);
      oldUser.setStreetType(null);
    }
    // 验证密码格式是否输入正确(密码可以不修改)
    if (null != user.getPassword() && user.getPassword().length() != 0) {
      // Validate.isTrue(
      // StringValidateUtils.validStrByPattern(user.getPassword(), "/^[a-zA-Z]{1,30}$/"),
      // "密码长度必须为6-12个字符，大小写英文字符或数字！");
      oldUser.setPassword(passwordEncoder.encodePassword(user.getPassword(),null));
    } else {
      oldUser.setPassword(oldPassword);
    }

    userRepository.saveAndFlush(oldUser);
  }

  @Override
  public UserEntity findByAccountAndStatus(String account, UseStatus status) {
    return userRepository.findByAccountAndStatus(account, status);
  }

  @Override
  @Transactional
  public void updateLoginTime(String account, Date loginTime) {
    Validate.notBlank(account, "account must not be empty!!");
    Validate.notNull(loginTime, "login time not be empty!!");
    this.userRepository.updateLastLoginDate(account, loginTime);
  }

  @Override
  public void updatePassword(String id, String oldPassword, String newPassword,
      UserEntity modifyUser) {
    // 判断用户提交信息完整
    Validate.notBlank(id, "id不能为空");
    Validate.notBlank(oldPassword, "原密码不能为空");
    Validate.notBlank(newPassword, "新密码不能为空");
    Validate.notNull(modifyUser, "修改人不能为空");
    UserEntity user = getById(id, modifyUser, EntityType.USERENTITY);
    Validate.notNull(user, "无法获取该管理员");
    // 获取加密后用户输入的原密码
    // String oldpwd = passwordEncoder.encodePassword(oldPassword, null);
    String oldpwd = passwordEncoder.encodePassword(oldPassword,null);
    // 验证原密码是否输入正确
    Validate.isTrue(user.getPassword().equals(oldpwd), "原密码输入错误，请重新输入");
    // Validate.isTrue(StringValidateUtils.validStrByPattern(newPassword, "[a-zA-Z\\d]{6,12}"),
    //     "新密码长度必须为6-12个字符，大小写英文字符或数字！");
    // user.setPassword(passwordEncoder.encodePassword(newPassword, null));
    user.setPassword(passwordEncoder.encodePassword(newPassword,null));
    user.setModifyUser(modifyUser);
    user.setModifyDate(new Date());
    userRepository.saveAndFlush(user);
  }

  @Override
  public UserEntity findByAccount(String account) {
    Validate.notBlank(account, "参数错误！");
    // UserEntity op = userRepository.findByAccountAndStatus(account, UseStatus.STATUS_NORMAL);
    UserEntity op = userRepository.findByAccount(account);
    if (op == null) {
      return null;
    }
    return op;
  }

  @Override
  public void applyRestPsd(String account, String name, String telephone) {
    Validate.notBlank(account, "账号不能为空！");
    Validate.notBlank(name, "用户名不能为空！");
    Validate.notBlank(telephone, "手机号码不能为空！");
    UserEntity user = userRepository.findByAccountAndUserName(account, name);
    Validate.notNull(user, "该用户不存在！");
    user.setTelephone(telephone);
    user.setIsResetPsd(true);
    userRepository.saveAndFlush(user);
  }

  @Override
  public UserEntity resetPsd(String id, UserEntity user) {
    Validate.notBlank(id, "用户id不能为空！");
    // UserEntity opUser = userRepository.findOne(id);
    UserEntity opUser = userRepository.getOne(id);
    Validate.notNull(opUser, "用户不存在！");
    // opUser.setPassword(passwordEncoder.encodePassword(RESETPSD, null));
    opUser.setPassword(passwordEncoder.encodePassword(RESETPSD,null));
    opUser.setIsResetPsd(false);
    opUser.setModifyUser(user);
    opUser.setModifyDate(new Date());
    return userRepository.saveAndFlush(opUser);
  }

  @Override
  public void createByInit() {
    UserEntity operator = new UserEntity();
    operator.setUserName("admin");
    operator.setAccount("admin");
    operator.setPassword(passwordEncoder.encodePassword("123456",null));
    Set<RoleEntity> exsitRoles = new HashSet<>();
    RoleEntity currentRole = this.roleService.findByName("ADMIN");
    Validate.isTrue(currentRole != null && currentRole.getStatus() == UseStatus.STATUS_NORMAL,
            "错误的角色信息，请检查!!");
    exsitRoles.add(currentRole);
    operator.setRoles(exsitRoles);
    UserEntity newOperator = userRepository.saveAndFlush(operator);
    securityService.bindRoleForOperator(currentRole.getId(), newOperator.getId());
  }

}
