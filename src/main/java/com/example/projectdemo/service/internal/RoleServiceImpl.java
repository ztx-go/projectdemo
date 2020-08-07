package com.example.projectdemo.service.internal;

import com.example.projectdemo.common.enums.EntityType;
import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.entity.CompetenceEntity;
import com.example.projectdemo.entity.RoleEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.RoleRepository;
import com.example.projectdemo.repository.internal.RoleDao;
import com.example.projectdemo.service.CompetenceService;
import com.example.projectdemo.service.RoleService;
import com.example.projectdemo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * 角色信息变化不大，且经常需要查询，所以需要将查询性质的操作放入到缓存中
 *
 */
@Service("roleServiceImpl")
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private CompetenceService competenceService;
	@Autowired
	private UserService user;

	/**
	 * 配置的那些不允许被删除被作废的角色
	 */
	@Value("${roles.deleteDenys}")
	private String[] deleteDenys;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vanda.fourthPayment.manager.service.RoleService#findByCompetenceId(java.
	 * lang.String)
	 */
	@Override
	public List<RoleEntity> findByCompetenceId(String competenceId) {
		Validate.notEmpty(competenceId, "competenceId must not be empty!");
		Set<RoleEntity> roles = this.roleRepository.findByCompetenceId(competenceId);
		if (roles == null || roles.isEmpty()) {
			return Collections.emptyList();
		}

		List<RoleEntity> rolesList = new ArrayList<RoleEntity>(roles);
		return rolesList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.economic.system.business.service.RoleService#findByUserAccount(java.lang.
	 * String)
	 */
	@Override
	public Set<RoleEntity> findByUserAccount(String userAccount) {
		Validate.notBlank(userAccount, "错误的参数userAccount");
		return this.roleRepository.findByUserAccountAndStatus(userAccount, UseStatus.STATUS_NORMAL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.economic.system.business.service.RoleService#findByOrgs(java.lang.String)
	 */
	@Override
	public Set<RoleEntity> findByOrgs(String orgs) {
		Validate.notBlank(orgs, "错误的参数orgs");
		return this.roleRepository.findByOrgs(orgs.split(",")[0]);
	}

	@Override
	public List<RoleEntity> findByStatus(UseStatus useStatus) {
		Validate.notNull(useStatus, "角色状态不能为空");
		return this.roleRepository.findByStatus(useStatus);
	}

	@Override
	public RoleEntity findRoleById(String roleId) {
		Validate.notBlank(roleId, "角色id不能为空");
		// return this.roleRepository.findOne(roleId);
		return this.roleRepository.getOne(roleId);
	}
	
	@Override
	  public List<RoleEntity> findByOperatorIdAndStatus(String id, UseStatus statusNormal) {
	    Validate.notBlank(id, "id不能为空");
	    return roleRepository.findByOperatorIdAndStatus(id, statusNormal);
	  }
	
	@Override
	public Page<RoleEntity> findByConditions(String roleName, UseStatus status, Pageable pageable) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(roleName)) {
			params.put("roleName", roleName);
		}
		if (status != null) {
			params.put("status", status);
		}
		return roleDao.findByConditions(params, pageable);
	}

	@Override
	@Transactional
	public RoleEntity addRole(RoleEntity role,UserEntity createUser) {
		// 进入传入信息的检查
		this.validateRoleBeforeAdd(role);
		role.setCreateUser(createUser);
		// 开始插入
		RoleEntity currentRole = this.roleRepository.save(role);
		// 查询系统内的默认管理员账号，并将添加的角色绑定到其身上(暂时未确定默认账号)
		return currentRole;
	}

	/**
	 * 该私有方法在新增一个role信息前，检查传入信息的证确定
	 * 
	 * @param role
	 */
	private void validateRoleBeforeAdd(RoleEntity role) {
		Validate.notNull(role, "角色不能为空!");
		Validate.isTrue(StringUtils.isEmpty(role.getId()), "新增role时，其中的id属性不能设定任何值！");
		// 开始验证
		Validate.notEmpty(role.getName(), "角色名不能为空!");
		// 必须是大写
		role.setName(role.getName().toUpperCase());
		RoleEntity oldRole = this.roleRepository.findByName(role.getName());
		Validate.isTrue(oldRole == null, "当前设定的角色名称（role name）已经被使用，请更换!");
		// 当前的创建时间和修改时间要一起写入
		Date currentTime = new Date();
		role.setCreateDate(currentTime);
		role.setModifyDate(currentTime);
		// 说明性信息
		Validate.notEmpty(role.getComment(), "角色中文说明一定要填写(comment)");
		// 当前角色必须是状态正常的
		role.setStatus(UseStatus.STATUS_NORMAL);
	}

	@Override
	@Transactional
	public RoleEntity updateRole(RoleEntity role,UserEntity modifyUser) {
		/*
		 * 修改角色的过程如下： 1、首先确定当前role是可以进行修改的role 2、只进行comment的修改，其它即使传递了也不进行修改
		 */
		Validate.notNull(role, "角色不能为空");
		String roleId = role.getId();
		String updateComment = role.getComment();
		Validate.notEmpty(roleId, "修改role时，角色Id不能为空!");
		Validate.notEmpty(updateComment, "角色中文说明一定要填写(comment)!");
		// RoleEntity currentRole = this.roleRepository.findOne(roleId);
		RoleEntity currentRole = this.roleRepository.getOne(roleId);
		Validate.notNull(currentRole, "角色不存在");

		// 1、========
		String currentName = currentRole.getName();
		// 如果条件成立，说明这个角色信息不能被修改
		for (String deleteDeny : deleteDenys) {
			if (StringUtils.equals(currentName, deleteDeny)) {
				throw new AccessDeniedException(deleteDeny + "不能被修改！");
			}
		}

		// 2、========
		currentRole.setModifyDate(new Date());
		currentRole.setComment(updateComment);
		currentRole.setModifyUser(modifyUser);
		this.roleRepository.saveAndFlush(currentRole);
		return currentRole;
	}

	@Override
	@Transactional
	public RoleEntity disableRole(String roleId) {
		Validate.notBlank(roleId, "角色roleId不能为空");
		// RoleEntity role = roleRepository.findOne(roleId);
		RoleEntity role = roleRepository.getOne(roleId);
		Validate.notNull(role, "角色%s不存在", roleId);
		role.setStatus(UseStatus.STATUS_DISABLE);
		role.setModifyDate(new Date());
		return this.roleRepository.saveAndFlush(role);

	}

	@Override
	@Transactional
	public RoleEntity enableRole(String roleId) {
		Validate.notBlank(roleId, "角色roleId不能为空");
		// RoleEntity role = roleRepository.findOne(roleId);
		RoleEntity role = roleRepository.getOne(roleId);
		Validate.notNull(role, "角色%s不存在", roleId);
		role.setStatus(UseStatus.STATUS_NORMAL);
		role.setModifyDate(new Date());
		return this.roleRepository.saveAndFlush(role);

	}

	@Override
	public Set<RoleEntity> findLdapRolesById(String id) {
		Validate.notBlank(id, "ldap id不能为空");
		return roleRepository.findLdapRolesById(id);
	}

	@Override
	@Transactional
	public void bindRoleForCompetences(String roleId, String[] competenceIds) {
		Validate.notBlank(roleId, "角色绑定权限时，角色roleId不能为空");
		Validate.notEmpty(competenceIds, "角色绑定权限时，权限competenceIds不能为空");
		// RoleEntity role = roleRepository.findOne(roleId);
		RoleEntity role = roleRepository.getOne(roleId);
		Validate.notNull(role, "roleId为%s的角色不存在", roleId);
		for (String competenceId : competenceIds) {
			this.checkCompetenceExist(competenceId);
			// 如果已经有绑定信息，则跳过，进行下一个绑定
			CompetenceEntity oldBind = this.competenceService.findCompetenceByRoleIdAndCompetenceId(roleId,
					competenceId);
			if (oldBind != null) {
				continue;
			}
			// 进行绑定
			this.roleRepository.bindRoleAndCompetence(roleId, competenceId);
		}
	}

	@Override
	@Transactional
	public void unbindRoleForCompetences(String roleId, String[] competenceIds) {
		Validate.notBlank(roleId, "角色解绑权限时，角色roleId不能为空");
		Validate.notEmpty(competenceIds, "角色解定权限时，权限competenceIds不能为空");
		// RoleEntity role = roleRepository.findOne(roleId);
		RoleEntity role = roleRepository.getOne(roleId);
		Validate.notNull(role, "roleId为%s的角色不存在", roleId);
		for (String competenceId : competenceIds) {
			this.checkCompetenceExist(competenceId);
			CompetenceEntity oldBind = this.competenceService.findCompetenceByRoleIdAndCompetenceId(roleId,
					competenceId);
			if (oldBind == null) {
				continue;
			}
			this.roleRepository.unbindRoleAndCompetence(roleId, competenceId);
		}
	}

	@Override
	@Transactional
	public void bindRolesForLdapNode(String[] roleIds, String ldapNodeId) {
		Validate.notBlank(ldapNodeId, "角色绑定权限时，角色ldapNodeId不能为空");
		Validate.notEmpty(roleIds, "角色绑定ldapNode时，权限roleIds不能为空");
		UserEntity ldapNode = user.getById(ldapNodeId,null,EntityType.USERENTITY);
		Validate.notNull(ldapNode, "ldapNodeId为%s的ldap不存在", ldapNodeId);
		for (String roleId : roleIds) {
			this.checkRoleExist(roleId);
			// 如果已经有绑定信息，则不予许再进行绑定
			// LdapNodeEntity oldBind =
			// this.roleRepository.findLdapNodeByRoleIdAndLdapNodeId(roleId, ldapNodeId);
			UserEntity oldBind = this.roleRepository.findLdapNodeByRoleIdAndLdapNodeId(roleId, ldapNodeId);
			if (oldBind != null) {
				continue;
			}
			// 进行绑定
			this.roleRepository.bindRoleForLdapNode(roleId, ldapNodeId);
		}
	}

	@Override
	@Transactional
	public void unbindRolesForLdapNode(String[] roleIds, String ldapNodeId) {
		Validate.notBlank(ldapNodeId, "角色解绑权限时，角色ldapNodeId不能为空");
		Validate.notEmpty(roleIds, "角色解绑ldapNode时，权限roleIds不能为空");
		UserEntity ldapNode = user.getById(ldapNodeId,null,EntityType.USERENTITY);
		Validate.notNull(ldapNode, "ldapNodeId为%s的ldap不存在", ldapNodeId);
		for (String roleId : roleIds) {
			this.checkRoleExist(roleId);
			// 如果已经有绑定信息，则不予许再进行绑定
			// LdapNodeEntity oldBind =
			// this.roleRepository.findLdapNodeByRoleIdAndLdapNodeId(roleId, ldapNodeId);
			UserEntity oldBind = this.roleRepository.findLdapNodeByRoleIdAndLdapNodeId(roleId, ldapNodeId);
			if (oldBind == null) {
				continue;
			}
			// 进行绑定
			this.roleRepository.unbindRoleForLdapNode(roleId, ldapNodeId);
		}
	}

	/**
	 * 检查角色是否存在
	 * 
	 * @param roleId
	 */
	private void checkRoleExist(String roleId) {
		// RoleEntity role = roleRepository.findOne(roleId);
		RoleEntity role = roleRepository.getOne(roleId);
		Validate.notNull(role, "roleId为%s没有发现指定的角色信息,请检查！", roleId);
	}

	/**
	 * 确定有这样一个功能
	 * 
	 * @param competenceId
	 */
	private void checkCompetenceExist(String competenceId) {
		CompetenceEntity competence = this.competenceService.findById(competenceId);
		Validate.notNull(competence, "competenceId为%s没有发现指定的功能信息,请检查！", competenceId);
	}

	@Override
	public List<RoleEntity> findAll() {
		List<RoleEntity> rolesList = this.roleRepository.findAll();
		// 如果条件成立说明系统该数据异常，这是直接抛出错误信息
		if (rolesList == null || rolesList.size() == 0) {
			throw new IllegalArgumentException("role infos error!!");
		}
		return rolesList;
	}

	@Override
	public Page<RoleEntity> getByConditions(String name, UseStatus status, Pageable pageable) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(name)) {
			map.put("name", name);
		}
		if (status != null) {
			map.put("status", status);
		}
		Page<RoleEntity> list = roleDao.findByConditions(map, pageable);
		return list;
	}

	@Override
	public List<RoleEntity> findByConditions(String name, UseStatus status) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(name)) {
			map.put("name", name);
		}
		if (status != null) {
			map.put("status", status);
		}
		List<RoleEntity> list = roleDao.findByConditions(map);
		return list;
	}

	@Override
	public RoleEntity findByName(String name) {

		RoleEntity list = roleRepository.findByName(name);
		return list;
	}

}
