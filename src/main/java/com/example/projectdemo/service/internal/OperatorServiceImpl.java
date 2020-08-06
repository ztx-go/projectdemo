package com.example.projectdemo.service.internal;

import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.common.utils.DateUtils;
import com.example.projectdemo.entity.MapTreeEntity;
import com.example.projectdemo.entity.OperatorEntity;
import com.example.projectdemo.entity.RoleEntity;
import com.example.projectdemo.repository.OperatorRepository;
import com.example.projectdemo.repository.internal.OperatorDao;
import com.example.projectdemo.service.MapTreeService;
import com.example.projectdemo.service.OperatorService;
import com.example.projectdemo.service.RoleService;
import com.example.projectdemo.service.SecurityService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service("operatorService")
public class OperatorServiceImpl implements OperatorService {
  @Autowired
  private OperatorRepository operatorRepository;
  @Autowired
  private OperatorDao operatorDao;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  @Autowired
  private RoleService roleService;
  @Autowired
  private SecurityService securityService;
  @Autowired
  private MapTreeService mapTreeService;

  
  
  @Override
  @Transactional
  public void create(OperatorEntity operator) {
    //判断用户提交信息完整
    VerificationByCreate(operator);

    //验证地区
    MapTreeEntity mapTree = operator.getArea();
    Validate.notNull(mapTree, "地区不能为空");
    mapTree = mapTreeService.findById(mapTree.getId());
    Validate.notNull(mapTree, "无法获取该地区");
    operator.setArea(mapTree);
    // operator.setPassword(passwordEncoder.encodePassword(operator.getPassword(), null));
    operator.setPassword(passwordEncoder.encode(operator.getPassword()));
    operatorRepository.saveAndFlush(operator);
   
  }

  @Override
  @Transactional
  public void update(OperatorEntity operator) {
    //判断用户提交信息完整
    VerificationByUpdate(operator);
    OperatorEntity oldOperator = getById(operator.getId());
    Validate.notNull(oldOperator,"无法获取该管理员");
    //保存数据
    oldOperator.setName(operator.getName());
    oldOperator.setHead(operator.getHead());
    if(!operator.getPassword().equals("11111111")){
      // oldOperator.setPassword(passwordEncoder.encodePassword(operator.getPassword(), null));
      oldOperator.setPassword(passwordEncoder.encode(operator.getPassword()));
    }

    //验证地区
    MapTreeEntity mapTree = operator.getArea();
    Validate.notNull(mapTree, "地区不能为空");
    mapTree = mapTreeService.findById(mapTree.getId());
    Validate.notNull(mapTree, "无法获取该地区");
    oldOperator.setArea(mapTree);
    oldOperator.setModifyUser(operator.getModifyUser());
    oldOperator.setModifyDate(new Date());
    operatorRepository.saveAndFlush(oldOperator);
  }
  
  @Override
  @Transactional
  public void updatePassword(String id, String oldPassword, String newPassword,
      OperatorEntity modifyOperator) {
    //判断用户提交信息完整
    Validate.notBlank(id,"id不能为空");
    Validate.notBlank(oldPassword,"原密码不能为空");
    Validate.notBlank(newPassword,"新密码不能为空");
    Validate.notNull(modifyOperator,"修改人不能为空");
    OperatorEntity operator = getById(id);
    Validate.notNull(operator,"无法获取该管理员");
    //获取加密后用户输入的原密码
    // String oldpwd = passwordEncoder.encodePassword(oldPassword, null);
    String oldpwd = passwordEncoder.encode(oldPassword);
    //验证原密码是否输入正确
    Validate.isTrue(operator.getPassword().equals(oldpwd), "原密码不正确");
    // Validate.isTrue(StringValidateUtils.validStrByPattern(newPassword, "[a-zA-Z\\d]{6,12}"),
    //     "新密码长度必须为6-12个字符，大小写英文字符或数字！");
    // operator.setPassword(passwordEncoder.encodePassword(newPassword, null));
    operator.setPassword(passwordEncoder.encode(newPassword));
    operator.setModifyUser(modifyOperator);
    operator.setModifyDate(new Date());
    operatorRepository.saveAndFlush(operator);
  }

  @Override
  @Transactional
  public void disable(String id, OperatorEntity updateOperator) {
    Validate.notNull(id,"id不能为空");
    OperatorEntity operator = getById(id);
    Validate.notNull(operator,"无法获取该管理员");
    operator.setStatus(UseStatus.STATUS_DISABLE);
    operatorRepository.saveAndFlush(operator);
  }

  @Override
  @Transactional
  public void enable(String id, OperatorEntity updateOperator) {
    Validate.notBlank(id,"id不能为空");
    OperatorEntity operator = getById(id);
    Validate.notNull(operator,"无法获取该管理员");
    operator.setStatus(UseStatus.STATUS_NORMAL);
    operatorRepository.saveAndFlush(operator);
  }

  @Override
  public OperatorEntity getById(String id) {
    Validate.notNull(id,"id不能为空");
    return operatorRepository.getOne(id);
  }

  /* (non-Javadoc)
   * @see com.vanda.fourthPayment.manager.service.OperatorService#findByAccountAndStatus(java.lang.String, com.vanda.fourthPayment.pojo.common.UseStatus)
   */
  @Override
  public OperatorEntity findByAccountAndStatus(String account, UseStatus status) {
    Validate.notBlank(account, "account must be input");
    Validate.notNull(status, "status must not be null");

    OperatorEntity result = this.operatorRepository.findByAccountAndStatus(account, status);
    if (result == null) {
      return null;
    }
    return result;
  }

  /* (non-Javadoc)
   * @see com.vanda.fourthPayment.manager.service.OperatorService#findByAccount(java.lang.String)
   */
  @Override
  public OperatorEntity findByAccount(String account) {
    Validate.notBlank(account, "参数错误！");
    OperatorEntity op = operatorRepository.findByAccountAndStatus(account, UseStatus.STATUS_NORMAL);
    if (op == null) {
      return null;
    }
    return op;
  }

  @Override
  public Page<OperatorEntity> findByParams(Map<String, Object> params, Pageable pageable) {
    Map<String, Object> map = new HashMap<String, Object>();
    if(null != params.get("account")){
      map.put("account", params.get("account"));
    }
    if(null != params.get("name")){
      map.put("name", params.get("name"));
    }
    if(null != params.get("status")){
      map.put("status", UseStatus.get(Integer.valueOf(String.valueOf(params.get("status")))));
    }
    if(null != params.get("area")){
      map.put("area", params.get("area"));
    }
    if(null != params.get("areas")){
      map.put("areas", params.get("areas"));
    }
    if(null != params.get("userDeparts")){
      map.put("userDeparts", params.get("userDeparts"));
    }
    Date beginDate = null;
    Date endDate = null;
    
    if (null != params.get("beginDateStr")) {
      beginDate = DateUtils.parserDate((String) params.get("beginDateStr"),DateUtils.SHORT_DATE_FORMATE_ONE);
      map.put("beginDate", beginDate);
    }
    if (null != params.get("endDateStr")) {
      endDate = DateUtils.parserDate((String) params.get("endDateStr"),
          DateUtils.SHORT_DATE_FORMATE_ONE);
      map.put("endDate", endDate);
    }
    // 验证起始时间 与终止时间
    if(null != beginDate && null != endDate){
      Validate.isTrue(beginDate.compareTo(endDate)<0, "起始时间不能大于截止时间！");
    }
    return operatorDao.findByParams(map, pageable);
  }
  
  /**
   * 创建用户信息的验证
   * @param operator 要创建的管理员
   */
  private void VerificationByCreate(OperatorEntity operator){
    Validate.notNull(operator,"管理员信息不能为空");
    Validate.isTrue(StringUtils.isBlank(operator.getId()),"创建管理员时不应带有逻辑键");
    Validate.notBlank(operator.getName(),"管理员姓名不能为空");
    Validate.isTrue(operator.getName().length()>=1 && operator.getName().length()<=12, "姓名长度必须为1-12个字符");
    Validate.notBlank(operator.getAccount(),"管理员账号不能为空");
    Validate.isTrue(null==operatorRepository.findByAccount(operator.getAccount()),"账号已被注册请检查");
    Validate.notNull(operator.getCreateUser(),"创建者不能为空");
    String accountRegex = "^[a-zA-Z0-9_]{4,20}$";
    Validate.isTrue(operator.getAccount().matches(accountRegex), "账号长度必须为4-20个字符，大小写英文字符或数字");
    Validate.notBlank(operator.getPassword(),"管理员密码不能为空");
    String pwRegex = "^[\\@A-Za-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6,12}$";
    Validate.isTrue(operator.getPassword().matches(pwRegex), "密码长度必须为6-12个字符，大小写英文字符或数字");
  }
  
  /**
   * 修改用户信息的验证
   * @param operator 要修改的管理员
   */
  private void VerificationByUpdate(OperatorEntity operator){
    Validate.notNull(operator,"管理员信息不能为空");
    Validate.notBlank(operator.getId(),"无法获取该管理员id");
    Validate.notBlank(operator.getName(),"管理员姓名不能为空");
    Validate.isTrue(operator.getName().length()>=1 && operator.getName().length()<=12, "姓名长度必须为1-12个字符");
    String pwRegex = "^[\\@A-Za-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6,12}$";
    Validate.isTrue(operator.getPassword().matches(pwRegex), "密码长度必须为6-12个字符，大小写英文字符或数字");
    Validate.notNull(operator.getModifyUser(),"修改者不能为空");
  }

  @Override
  public void resetPassword(String resetId, OperatorEntity operator) {
    Validate.notNull(resetId,"id不能为空");
    // OperatorEntity changeOperator = operatorRepository.findById(resetId);
    OperatorEntity changeOperator = operatorRepository.getOne(resetId);
    Validate.notNull(changeOperator,"无法获取该管理员");
    // changeOperator.setPassword(passwordEncoder.encodePassword("123456", null));
    changeOperator.setPassword(passwordEncoder.encode("123456"));
    changeOperator.setModifyUser(operator);
    operatorRepository.saveAndFlush(changeOperator);
    
  }

  @Override
  @Transactional
  public void updateLoginDate(OperatorEntity operator) {
    if(null != operator){
      operator.setLastLoginDate(new Date());
      operatorRepository.saveAndFlush(operator);
    }
  }

  @Override
  public void createByInit() {
    OperatorEntity operator = new OperatorEntity();
    operator.setName("admin");
    operator.setAccount("admin");
    // operator.setPassword(passwordEncoder.encodePassword("123456", null));
    operator.setPassword(passwordEncoder.encode("123456"));
    Set<RoleEntity> exsitRoles = new HashSet<>();
    RoleEntity currentRole = this.roleService.findByName("ADMIN");
    Validate.isTrue(currentRole != null && currentRole.getStatus() == UseStatus.STATUS_NORMAL,
        "错误的角色信息，请检查!!");
    exsitRoles.add(currentRole);
    operator.setRoles(exsitRoles);
    OperatorEntity newOperator = operatorRepository.saveAndFlush(operator);
    securityService.bindRoleForOperator(currentRole.getId(), newOperator.getId());
  }

  @Override
  public void updateStatusByMaptree(MapTreeEntity position, UseStatus status) {
    Validate.notNull(position, "地区不能为空");
    Validate.notNull(status, "状态不能为空");
    operatorRepository.updateStatusByMaptree(position, status);
  }

}
