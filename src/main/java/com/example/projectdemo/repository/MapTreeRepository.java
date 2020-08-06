package com.example.projectdemo.repository;

import com.example.projectdemo.common.enums.MapTreeType;
import com.example.projectdemo.entity.MapTreeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 码表树
 *
 * @author weibo
 * date:2019年3月18日 下午4:32:02 
 *
 */
@Repository("mapTreeRepostory")
public interface MapTreeRepository
    extends
        JpaRepository<MapTreeEntity, String>,
        JpaSpecificationExecutor<MapTreeEntity> {
  /**
   * 插入一个节点 （系统初始化使用）
   * 
   * @param childCount
   * @param code
   * @param del
   * @param index
   * @param introduction
   * @param type
   * @param name
   * @param state_type
   * @param id
   * @return
   */
  @Modifying
  @Query(value = " INSERT INTO map_tree (id, name, introduction, code, type, state_type, child_count, item_index, is_delete, parent_id)"
      + " VALUES (:id, :name, :introduction, :code, :type, :state_type, :child_count, :item_index, :is_delete, :parent_id)", nativeQuery = true)
  void insert(@Param("id") String id, @Param("name") String name,
              @Param("introduction") String introduction, @Param("code") String code,
              @Param("type") int maptreeType, @Param("state_type") int stateType,
              @Param("child_count") int childCount, @Param("item_index") int index,
              @Param("is_delete") boolean del, @Param("parent_id") MapTreeEntity parent);


  /**
   * 按码表类型获取码表parent节点下的未删除的节点集合 （没有排除禁用启用的节点）
   *
   * @param parent 获取其子类并且类型为 mapType 的码表对象
   * @param mapType 码表类型
   * @return
   */
  @Query("from MapTreeEntity m where m.parent=:parent and m.maptreeType=:type and m.del=false")
  List<MapTreeEntity> findByParentAndTypeAndDeleteFalse(@Param("parent") MapTreeEntity parent,
                                                        @Param("type") MapTreeType mapType);

  /**
   * 通过码表节点名称获取该节点
   * 
   * @param name 名称
   * @return
   */
  List<MapTreeEntity> findByName(String name);

  /**
   * 根据名称获取该子集合
   * 
   * @param id
   * @return
   */
  @Query("from MapTreeEntity m where m.parent.name = :name and m.del=false")
  List<MapTreeEntity> findChildrenByName(@Param("name") String name);

  /**
   * 根据id串获取码表树节点
   * 
   * @param ids
   * @return
   */
  @Query("from MapTreeEntity m where m.id in :ids")
  List<MapTreeEntity> findByIds(@Param("ids") List<String> ids);

  /**
   * 根据id船以及码表类型获取码表树节点
   * 
   * @param ids
   * @param type
   * @return
   */
  @Query("from MapTreeEntity m where m.maptreeType = :type and m.id in :ids")
  List<MapTreeEntity> findByIds(@Param("ids") List<String> ids, @Param("type") MapTreeType type);
}
