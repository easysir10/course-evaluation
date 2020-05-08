package com.study.evaluation.dao;

import com.study.evaluation.bean.RoleBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/05/02 9:51
 */
@Mapper
public interface RoleDao {

    @Select("select * from `role`;")
    List<RoleBean> selectAll();

    @Update("update `role`set role_name=#{bean.roleName},role_percent=#{bean.rolePercent} "
                    + "where role_id=#{bean.roleId}")
    int updateById(@Param("bean") RoleBean bean);

    @Insert("insert into `role`(role_name,role_percent) values(#{bean.roleName},#{bean.rolePercent});")
    int insert(@Param("bean") RoleBean bean);

    @Delete("delete from role where role_id=#{id};")
    int deleteById(@Param("id") int id);
}
