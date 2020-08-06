package com.example.projectdemo.service.internal;

import com.example.projectdemo.common.enums.MapTreeType;
import com.example.projectdemo.common.enums.UseStatus;
import com.example.projectdemo.entity.MapTreeEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.repository.MapTreeRepository;
import com.example.projectdemo.service.MapTreeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("mapTreeService")
public class MapTreeServiceImpl implements MapTreeService {
  
  @Autowired
  private MapTreeRepository mapTreeRepository;
  
  
  @Override
  public MapTreeEntity findById(String id) {
//    Validate.notBlank(id, "id不能为空");
//     MapTreeEntity dict = this.mapTreeRepository.findOne(id);
    MapTreeEntity dict = this.mapTreeRepository.getOne(id);
    return dict;
  }

  @Override
  public List<MapTreeEntity> findIn(List<String> ids, MapTreeType type) {
    Validate.isTrue(null != ids && ids.size() > 0, "id不能为空");
    if (null == type) {
      return mapTreeRepository.findByIds(ids);
    } else {
      return mapTreeRepository.findByIds(ids, type);
    }
  }

  @Override
  @Transactional
  public void insert(MapTreeEntity mapTree) {
    Validate.notNull(mapTree, "码表信息不能为空");
    // 验证 的名字 是否为空
    String name = mapTree.getName();
    Validate.notBlank(name, "码表名不能为空");
    Validate.matchesPattern(name, "^.{1,60}$", "码表名长度必须为1-60个字符");
    Validate.notNull(mapTree.getMaptreeType(), "码表类型不能为空");
    mapTreeRepository.insert(mapTree.getId(), mapTree.getName(), mapTree.getIntroduction(),
        mapTree.getCode(), mapTree.getMaptreeType().getValue(), mapTree.getStateType().getValue(),
        mapTree.getChildCount(), mapTree.getIndex(), mapTree.isDel(), mapTree.getParent());
    MapTreeEntity parent = mapTree.getParent();
    if (null != parent) {
      parent.setChildCount(parent.getChildCount() + 1);
      mapTreeRepository.saveAndFlush(parent);
    }
  }

  @Override
  @Transactional
  public MapTreeEntity createMapTree(MapTreeEntity mapTree, UserEntity user) {
    // 验证当前操作者不能为空
    Validate.notNull(user, "操作者不能为空");
    // 验证新增的 节点信息 不能为空
    Validate.notNull(mapTree, "新增节点信息不能为空");
    // 验证新增 的父节点 和 是否真实存在不能为空
    Validate.notNull(mapTree.getParent(), "请指定父节点");
    String parentId = mapTree.getParent().getId();
    Validate.notEmpty(parentId, "parentId不能为空");
    // 验证新增 的父节点 是否真实存在
    MapTreeEntity parent = get(parentId);
    Validate.notNull(parent, "父节点不存在");
    // 验证 的名字 是否为空
    String name = mapTree.getName();
    Validate.notBlank(name, "码表名不能为空");
    Validate.matchesPattern(name, "^.{2,20}$", "名称长度必须为2-20个字符");
    // 验证 名字是否已存在
    MapTreeEntity temp = getByParentAndName(parentId, name);
    if (temp != null) {
      throw new IllegalArgumentException("该节点下业务名称已存在");
    }
    String introduction = mapTree.getIntroduction();
    if (StringUtils.isNotBlank(introduction)) {
      Validate.isTrue(introduction.length() <= 60, "备注长度不能超过60个字符");
    }
    mapTree.setIntroduction(introduction);
    // 如果父节点下的子节点数大于0，则取最后一个 节点的 index+1 否则为1
    if (parent.getChildCount() > 0) {
      mapTree.setIndex(parent.getChildren().get(parent.getChildCount() - 1).getIndex() + 1);
    } else {
      mapTree.setIndex(1);
    }
    // 如果父节点的父亲节点不为空 则视为不是顶级节点，在其下的节点 的类型直接取父节点的类型
    if (parent.getParent() != null) {
      mapTree.setMaptreeType(parent.getMaptreeType());
    }
    if (mapTree.getMaptreeType() == null) {
      mapTree.setMaptreeType(MapTreeType.MAP_TREE_TYPE_OTHER);
    }
//    if (mapTree.getMaptreeType().equals(MapTreeType.MAP_TREE_REVENUE)) {
//    }
    mapTree.setParent(parent);
    mapTree.setCreator(user);
    mapTree.setCreateDate(new Date());
    // 如果父节点为null
    if (parent.getChildren() == null) {
      parent.setChildren(new ArrayList<MapTreeEntity>());
    }
    parent.getChildren().add(mapTree);
    parent.setChildCount(parent.getChildren().size());
    mapTree = this.mapTreeRepository.save(mapTree);
    mapTree.setCode(parent.getCode() + mapTree.getId() + "|");
    return this.mapTreeRepository.saveAndFlush(mapTree);
  }

  @Override
  @Transactional
  public MapTreeEntity modify(MapTreeEntity mapTree, UserEntity user) {
 // 验证修改的信息不能为空
    Validate.notNull(mapTree, "修改码表节点信息不能为空");
    // 验证修改操作时对应的节点id不能为空
    String id = mapTree.getId();
    Validate.notBlank(id, "修改码表节点信息时，节点id为必填项");
    // 验证分类是否存在
    MapTreeEntity oldMapTree = get(id);
    Validate.notNull(oldMapTree, "修改的码表节点不存在");
    // 验证分类的名字不能为空 长度为1-60个字符
    String name = mapTree.getName();
    Validate.notBlank(name, "分类名不能为空");
    Validate.matchesPattern(name, "^.{1,60}$", "码表名长度必须为1-60个字符");
    // 验证操作者不能为空
//    Validate.notNull(user, "操作者不能为空");
    // 验证同级分类下是否已存在 该分类名
    // 如果节点parent为null 视为顶级节点
    if (oldMapTree.getParent() == null) {
      throw new IllegalArgumentException("根节点不能修改");
    }
    // 判断该节点是否已被使用
    MapTreeEntity temp = getByParentAndName(oldMapTree.getParent().getId(), name);
    if (temp != null && temp.getId() != mapTree.getId()) {
      throw new IllegalArgumentException("父节点下已存在节点名为" + name + "的节点");
    }
    String introduction = mapTree.getIntroduction();

    oldMapTree.setName(name);
    oldMapTree.setIntroduction(introduction);
    oldMapTree.setModifier(user);
    oldMapTree.setModifyDate(new Date());
    return this.mapTreeRepository.saveAndFlush(oldMapTree);
  }

  @Override
  @Transactional
  public MapTreeEntity deleteMapTree(String id, UserEntity user) {
    Validate.notBlank(id, "删除码表节点时，码表节点id不能为空");
//    Validate.notNull(user, "操作者不能为空");
//     MapTreeEntity dict = mapTreeRepository.findOne(id);
    MapTreeEntity dict = mapTreeRepository.getOne(id);
    if (dict == null) {
      throw new IllegalArgumentException("删除的节点不存在");
    }

    // List<MapTreeEntity> children = dict.getChildren();
    // if (children != null) {
    // if (!childrenIsAllDelete(children)) {
    // throw new IllegalArgumentException("目录存在未删除的子节点,不能删除");
    // }
    // }
    // 判断该节点是否已被使用
    MapTreeEntity parent = dict.getParent();
    parent.getChildren().remove(dict);
    parent.setChildCount(parent.getChildren().size());
    this.mapTreeRepository.saveAndFlush(parent);
    dict.setDel(true);
    return this.mapTreeRepository.saveAndFlush(dict);
  }

  @Override
  @Transactional
  public MapTreeEntity disable(String id, UserEntity user) {
    Validate.notBlank(id, "禁用码表节点时，码表节点id不能为空");
    Validate.notNull(user, "操作者不能为空");
    // MapTreeEntity dict = mapTreeRepository.findOne(id);
    MapTreeEntity dict = mapTreeRepository.getOne(id);
    Validate.notNull(dict, "禁用的码表节点不存在");
    dict.setStateType(UseStatus.STATUS_NORMAL);
    dict.setModifier(user);
    dict.setModifyDate(new Date());
    return mapTreeRepository.saveAndFlush(dict);
  }

  @Override
  @Transactional
  public MapTreeEntity enable(String id, UserEntity user) {
    Validate.notBlank(id, "重新启用码表节点时，码表节点id不能为空");
    Validate.notNull(user, "操作者不能为空");
    // MapTreeEntity dict = mapTreeRepository.findOne(id);
    MapTreeEntity dict = mapTreeRepository.getOne(id);
    Validate.notNull(dict, "启用的码表节点不存在");
    dict.setStateType(UseStatus.STATUS_NORMAL);
    dict.setModifier(user);
    dict.setModifyDate(new Date());
    return mapTreeRepository.saveAndFlush(dict);
  }

  @Override
  public MapTreeEntity get(String id) {
    Validate.notBlank(id, "id不能为空");
    // MapTreeEntity dict = this.mapTreeRepository.findOne(id);
    MapTreeEntity dict = this.mapTreeRepository.getOne(id);
    if (dict == null || dict.isDel()) {
      Validate.notNull(dict, "id为" + id + "的码表节点不存在");
    }
    return dict;
  }

  @Override
  public List<MapTreeEntity> getByName(String name) {
    Validate.notBlank(name, "节点名称不能为空");
    return mapTreeRepository.findByName(name);
  }

  @Override
  public List<MapTreeEntity> findChildrenByName(String name) {
    Validate.notBlank(name, "节点名称不能为空");
    return mapTreeRepository.findChildrenByName(name);
  }

  @Override
  public List<MapTreeEntity> findByParentAndTypeAndDeleteFalse(MapTreeEntity parent,
      MapTreeType mapType) {
    Validate.notNull(parent, "父节点不能为空");
    Validate.notNull(mapType, "节点类型不能为空");
    return mapTreeRepository.findByParentAndTypeAndDeleteFalse(parent, mapType);
  }

  @Override
  public MapTreeEntity matchAreaName(String province, String city, String district) {
 // 手机定位区域匹配系统内码表区域
    //
    if (StringUtils.isNotBlank(district)) {
      List<MapTreeEntity> districts = mapTreeRepository.findByName(district);
      if (districts != null && districts.size() == 1) {
        return districts.get(0);
      }
      if (districts != null && StringUtils.isNotBlank(province) && StringUtils.isNotBlank(city)) {
        for (MapTreeEntity districtTemp : districts) {
          MapTreeEntity cityTemp = districtTemp.getParent();
          if (cityTemp != null) {
            // 如果city相同 则比较 province
            if (city.equals(cityTemp.getName())) {
              MapTreeEntity provinceTemp = cityTemp.getParent();
              if (province.equals(city) && provinceTemp.getId().equals("3")) {
                return districtTemp;
              }
              if (province.equals(provinceTemp.getName())) {
                return districtTemp;
              }

            }
          }
        }
      }
    } else {
      List<MapTreeEntity> citys = mapTreeRepository.findByName(city);
      if (citys != null && citys.size() == 1) {
        return citys.get(0);
      }
      if (StringUtils.isNotBlank(province) && StringUtils.isNotBlank(city)) {
        for (MapTreeEntity cityTemp : citys) {
          MapTreeEntity provinceTemp = cityTemp.getParent();
          if (provinceTemp != null) {

            if (province.equals(provinceTemp.getName())) {
              return cityTemp;
            }
          }
        }
      }
    }
    return null;
  }
  
  /**
   * 检查parentId节点下是否有存在没有删除的且节点名为name的节点
   * 
   * @param parentId
   * @param name
   * @return
   */
  private MapTreeEntity getByParentAndName(String parentId, String name) {
    Validate.notBlank(parentId, "父节点id不能为空");
    Validate.notBlank(name, "分类名不能为空");
    // MapTreeEntity parent = this.mapTreeRepository.findOne(parentId);
    MapTreeEntity parent = this.mapTreeRepository.getOne(parentId);
    List<MapTreeEntity> dictList = parent.getChildren();
    if (dictList != null && !dictList.isEmpty()) {
      for (MapTreeEntity temp : dictList) {
        if (temp.getName().equals(name) && temp.isDel() == false) {
          return temp;
        }
      }
    }
    return null;
  }
}
