package com.example.projectdemo.service.internal;

import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.entity.CompetenceEntity;
import com.example.projectdemo.entity.RoleEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.CompetenceRepository;
import com.example.projectdemo.repository.internal.CompetenceDao;
import com.example.projectdemo.service.CompetenceService;
import com.example.projectdemo.service.RoleService;
import com.example.projectdemo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * 功能信息变化不大，且经常需要查询，所以需要将查询性质的操作放入到缓存中<br>
 * 功能在服务中的描述信息，主要为了和角色进行绑定产生权限控制
 * 
 * @author yinwenjie ly
 *
 */
@Service("CompetenceServiceImpl")
public class CompetenceServiceImpl implements CompetenceService {

	/**
	 * 权限repository自动注入.
	 */
	@Autowired
	private CompetenceRepository competenceRepository;
	@Autowired
	private CompetenceDao competenceDao;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.economic.system.business.service.CompetenceService#findByResource(java.
	 * lang.String)
	 */
	@Override
	public List<CompetenceEntity> findByResource(String resource) {
		Validate.notNull(resource, "resource must not be null!");
		return this.competenceRepository.findByResource(resource);
	}

	@Override
	public CompetenceEntity findById(String id) {
		Validate.notBlank(id, "查询功能时 id不能为空");
		// return competenceRepository.findOne(id);
		return competenceRepository.getOne(id);
	}

	@Override
	public boolean isExit(String methods, String resource, String id) {
		int count = 0;
		if (StringUtils.isBlank(id)) {
			count = competenceRepository.findByMethodsAndResource(methods, resource);
		} else {
			count = competenceRepository.findByMethodsAndResourceAndId(methods, resource, id);
		}
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public CompetenceEntity addCompetence(CompetenceEntity competence,UserEntity user) {
	    Validate.notNull(user, "操作者不为空!");
		// 验证
		validCompetence(competence);
		// 验证是否已经存在
		Validate.isTrue(!isExit(competence.getMethods().toUpperCase(), competence.getResource(), null), "功能对象已存在！");
		competence.setMethods(competence.getMethods().toUpperCase());
		competence.setStatus(UseStatus.STATUS_NORMAL);
		competence.setCreateDate(new Date());
		competence.setCreateUser(user);
		CompetenceEntity competenceEntity = competenceRepository.save(competence);
		return competenceEntity;
	}

	@Override
	@Transactional
	public CompetenceEntity updateCompetence(CompetenceEntity competence,UserEntity user) {
	    Validate.notNull(user, "操作者不为空!");
		// CompetenceEntity currentComp = competenceRepository.findOne(competence.getId());
		CompetenceEntity currentComp = competenceRepository.getOne(competence.getId());
		// 验证对象
		Validate.notNull(currentComp, "功能id为%s的对象不存在！", competence.getId());
		currentComp.setResource(competence.getResource());
		currentComp.setMethods(competence.getMethods());
		currentComp.setComment(competence.getComment());
		currentComp.setModifyDate(new Date());
		currentComp.setModifyUser(user);
		// 验证
		validCompetence(currentComp);
		// 验证是否已经存在
		Validate.isTrue(!isExit(currentComp.getMethods().toUpperCase(), currentComp.getResource(), currentComp.getId()),
				"功能对象已存在！");
		// 验证通过后methods统一大写存储
		currentComp.setMethods(currentComp.getMethods().toUpperCase());
		competenceRepository.saveAndFlush(currentComp);
		return currentComp;
	}

	@Override
	@Transactional
	public CompetenceEntity diableOrUndisable(String id, Boolean flag) {
		Validate.notBlank(id, "删除competence时id不能为空");
		// CompetenceEntity currentComp = competenceRepository.findOne(id);
		CompetenceEntity currentComp = competenceRepository.getOne(id);
		Validate.notNull(currentComp, "id为%s的competence不存在", id);
		if (flag) {
			currentComp.setStatus(UseStatus.STATUS_NORMAL);
		} else {
			currentComp.setStatus(UseStatus.STATUS_DISABLE);
		}
		return competenceRepository.saveAndFlush(currentComp);
	}

	/**
	 * 验证功能对象和字段.
	 * 
	 * @param comp 功能对象
	 */
	private void validCompetence(CompetenceEntity comp) {
		// 验证对象是否存在
		Validate.notNull(comp, "功能对象不能为空！");
		// 功能串
		String resource = comp.getResource();
		Validate.notBlank(resource, "功能串不能为空！");
		// 涉及的方法描述（POST/GET……）
		String method = comp.getMethods();
		Validate.notBlank(method, "方法描述不能为空！");
		// 备注
		String comment = comp.getComment();
		Validate.notBlank(comment, "功能备注不能为空！");
	}

	@Override
	public Page<CompetenceEntity> findByConditions(String methods, String comment, String resource, UseStatus useStatus,
                                                   Pageable pageable) {
		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotBlank(resource)) {
			params.put("resource", resource);
		}
		if (StringUtils.isNotBlank(methods)) {
			params.put("methods", methods.toUpperCase());
		}
		if (StringUtils.isNotBlank(comment)) {
			params.put("comment", comment);
		}
		if (useStatus != null) {
			params.put("status", useStatus);
		}
		return competenceDao.findByConditions(params, pageable);
	}

	@Override
	public List<CompetenceEntity> findAllNormal() {

		return competenceRepository.findByStatus(UseStatus.STATUS_NORMAL);
	}

	@Override
	public List<CompetenceEntity> findByRoleId(String roleId) {
		Validate.notBlank(roleId, "角色id不能为空");
		return competenceRepository.findByRoleId(roleId);
	}

	@Override
	public CompetenceEntity findCompetenceByRoleIdAndCompetenceId(String roleId, String competenceId) {
		Validate.notBlank(roleId, "角色roleId不能为空");
		Validate.notBlank(competenceId, "权限competenceId不能为空");
		return competenceRepository.findCompetenceByRoleIdAndCompetenceId(roleId, competenceId);
	}

	@Override
	public Boolean findCompetencePermissionByComment(String account, String comment) {
		Validate.notBlank(account, "account must be input!");
		Validate.notBlank(comment, "comment must be input!");
		// 如果当前用户拥有管理员权限，则都返回true
		//LdapNodeEntity currentOp = this.ldapNodeService.findByLdapNameAndStatus(name, UseStatus.STATUS_NORMAL);
		//Validate.notNull(currentOp, "id=%s,ldapName=%s,用户状态不正确!", currentOp.getId(), currentOp.getLdapName());
		UserEntity currentOp = this.userService.findByAccountAndStatus(account, UseStatus.STATUS_NORMAL);
		Validate.notNull(currentOp, "id=%s,ldapName=%s,用户状态不正确!", currentOp.getId(), currentOp.getUserName());
		Set<RoleEntity> roles = this.roleService.findLdapRolesById(currentOp.getId());
		if (roles == null || roles.isEmpty()) {
			return false;
		}
		for (RoleEntity role : roles) {
			if (StringUtils.equals(role.getName(), "ADMIN")) {
				return true;
			}
		}
		Set<CompetenceEntity> allCompetences = this.queryAllCompetencesByLdapName(account);
		// 看看集合中是不是有这个指定的功能
		if (allCompetences.isEmpty()) {
			return false;
		}
		for (CompetenceEntity item : allCompetences) {
			if (StringUtils.equals(item.getComment(), comment))
				return true;
		}
		return false;
	}

	@Override
	public Boolean findCompetencePermissionByUrl(String account, String url, String methods) {
		Validate.notBlank(account, "account must be input!");
		Validate.notBlank(url, "url must be input!");
		Validate.notBlank(methods, "methods must be input!");
		// 如果当前用户拥有管理员权限，则都返回true
		UserEntity currentOp = this.userService.findByAccountAndStatus(account, UseStatus.STATUS_NORMAL);
		Validate.notNull(currentOp, "id=%s,ldapName=%s,用户状态不正确!", currentOp.getId(), currentOp.getUserName());
		Set<RoleEntity> roles = this.roleService.findLdapRolesById(currentOp.getId());
		if (roles == null || roles.isEmpty()) {
			return false;
		}
		for (RoleEntity role : roles) {
			if (StringUtils.equals(role.getName(), "ADMIN")) {
				return true;
			}
		}

		String currentMethod = methods.toUpperCase();
		Set<CompetenceEntity> allCompetences = this.queryAllCompetencesByLdapName(account);
		// 看看集合中是不是有这个指定的功能
		if (allCompetences.isEmpty()) {
			return false;
		}
		for (CompetenceEntity item : allCompetences) {
			if (StringUtils.equals(item.getResource(), url)
					&& StringUtils.indexOf(item.getMethods(), currentMethod) != -1) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean findCompetencePermissionById(String account, String competenceId) {
		Validate.notBlank(account, "account must be input!");
		Validate.notBlank(competenceId, "competenceId must be input!");
		// 如果当前用户拥有管理员权限，则都返回true
		//LdapNodeEntity currentOp = this.ldapNodeService.findByLdapNameAndStatus(name, UseStatus.STATUS_NORMAL);
		//Validate.notNull(currentOp, "id=%s,ldapName=%s,用户状态不正确!", currentOp.getId(), currentOp.getLdapName());
		UserEntity currentOp = this.userService.findByAccountAndStatus(account, UseStatus.STATUS_NORMAL);
		Validate.notNull(currentOp, "id=%s,ldapName=%s,用户状态不正确!", currentOp.getId(), currentOp.getUserName());
		Set<RoleEntity> roles = this.roleService.findLdapRolesById(currentOp.getId());
		if (roles == null || roles.isEmpty()) {
			return false;
		}
		for (RoleEntity role : roles) {
			if (StringUtils.equals(role.getName(), "ADMIN")) {
				return true;
			}
		}

		Set<CompetenceEntity> allCompetences = this.queryAllCompetencesByLdapName(account);
		// 看看集合中是不是有这个指定的功能
		if (allCompetences.isEmpty()) {
			return false;
		}
		for (CompetenceEntity item : allCompetences) {
			if (StringUtils.equals(item.getId(), competenceId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 这个方法用于查询该用户可使用的所有功能信息
	 * 
	 * @param account
	 * @return
	 */
	private Set<CompetenceEntity> queryAllCompetencesByLdapName(String account) {
		//LdapNodeEntity ldapNode = this.ldapNodeService.findByLdapNameAndStatus(ldapName, UseStatus.STATUS_NORMAL);
		UserEntity ldapNode = this.userService.findByAccountAndStatus(account, UseStatus.STATUS_NORMAL);
		if (ldapNode == null) {
			return Collections.emptySet();
		}
		Set<RoleEntity> roles = ldapNode.getRoles();
		if (roles == null || roles.isEmpty()) {
			return Collections.emptySet();
		}
		// 集成查询当前用户所有的功能
		Set<CompetenceEntity> allCompetences = new HashSet<>();
		for (RoleEntity role : roles) {
			// 这里带有缓存
			List<CompetenceEntity> competences = this.findByRoleId(role.getId());
			if (competences == null || competences.isEmpty()) {
				return Collections.emptySet();
			}
			allCompetences.addAll(competences);
		}
		return allCompetences;
	}

}
