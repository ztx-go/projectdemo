package com.example.projectdemo.service;

import com.example.projectdemo.common.enums.MapTreeType;
import com.example.projectdemo.entity.MapTreeEntity;
import com.example.projectdemo.entity.UserEntity;

import java.util.List;

public interface MapTreeService {
  /**
   * 根据id获取该对象
   * 
   * @param id
   * @return
   */
  MapTreeEntity findById(String id);

  /**
   * 根据id串和码表类型获取码表
   * 
   * @param ids
   * @param type
   * @return
   */
  List<MapTreeEntity> findIn(List<String> ids, MapTreeType type);

  /**
   * 插入一个节点(系统初始化数据使用)
   * 
   * 
   */
  void insert(MapTreeEntity mapTree);

  /**
   * 新增maptree
   * 
   * @param mapTreeEntity
   * @param operator
   * @return
   */
  MapTreeEntity createMapTree(MapTreeEntity mapTreeEntity, UserEntity user);

  /***
   * 修改maptree
   * 
   * @param mapTreeEntity
   * @param operator
   * @return
   */
  MapTreeEntity modify(MapTreeEntity mapTreeEntity, UserEntity user);

  /**
   * 删除mapTree
   * 
   * @param id
   * @param operator
   * @return
   */
  MapTreeEntity deleteMapTree(String id, UserEntity user);

  /**
   * 禁用字典对象
   * 
   * @param id 字典对象id
   * @param operator 操作员
   */
  MapTreeEntity disable(String id, UserEntity user);

  /**
   * 恢复字典对象
   * 
   * @param id 字典对象id
   * @param operator 操作员
   * @return
   */
  MapTreeEntity enable(String id, UserEntity user);

  /**
   * 根据Id获取该对象
   * 
   * @param id
   * @return
   */
  MapTreeEntity get(String id);

  /**
   * 根据名称获取该对象
   * 
   * @param id
   * @return
   */
  List<MapTreeEntity> getByName(String name);

  /**
   * 根据名称获取该子集合
   * 
   * @param id
   * @return
   */
  List<MapTreeEntity> findChildrenByName(String name);

  /**
   * 按码表类型获取码表parent节点下的未删除的节点集合 （没有排除禁用启用的节点）
   * 
   * @param parent 获取其子类并且类型为 mapType 的码表对象
   * @param mapType 码表类型
   * @return
   */
  List<MapTreeEntity> findByParentAndTypeAndDeleteFalse(MapTreeEntity parent, MapTreeType mapType);

  /**
   * 手机定位服务使用 用于匹配定位的地区和系统内的地区匹配
   * 
   * @param province 省
   * @param city 市
   * @param district 区
   * @return
   */
  MapTreeEntity matchAreaName(String province, String city, String district);

}
