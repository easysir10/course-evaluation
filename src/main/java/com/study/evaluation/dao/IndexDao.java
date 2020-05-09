package com.study.evaluation.dao;

import com.study.evaluation.bean.IndexBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/04/13 13:25
 */
@Mapper
public interface IndexDao {

    @Select("select i1.index_id,i1.index_name,i1.index_percent,i1.index_grade,"
                    + "i2.index_id as parent_id,i2.index_name as parent_name"
                    + " from `index` as i1 left join `index` as i2 on i1.parent_id=i2.index_id;")
    List<IndexBean> selectAll();

    @Delete("delete from `index` where index_id = #{id}")
    int deleteById(@Param("id") int id);

    @Update("update `index` set index_name=#{indexBean.indexName},"
                    + "index_percent=#{indexBean.indexPercent},parent_id=#{indexBean.parentId} "
                    + "where index_id=#{indexBean.indexId}")
    int updateById(@Param("indexBean") IndexBean indexBean);

    @Insert("insert into `index`(index_name,index_percent,index_grade,parent_id) "
                    + "values(#{indexBean.indexName},#{indexBean.indexPercent},#{indexBean.indexGrade},#{indexBean.parentId})")
    int insert(@Param("indexBean") IndexBean indexBean);

    @Select("select * from `index` where index_grade=1;")
    List<IndexBean> selectOne();

    @Select("select * from `index` where index_grade=2 and parent_id=#{id};")
    List<IndexBean> selectTwo(@Param("id") int id);

    @Select("select * from `index` where index_grade=3 and parent_id=#{id};")
    List<IndexBean> selectThree(@Param("id") int id);

    @Select("select index_grade from `index` where index_id=#{id};")
    int selectIndexGrade(@Param("id") int id);

    @Select("<script>" +
                "SELECT count( * ) FROM evaluation_index WHERE index_score = #{score} " +
                "AND evaluation_id IN " +
                    "( SELECT evaluation_id FROM evaluation WHERE course_id = #{courseId} " +
                    "<if test='roleId!=-1'>" +
                        "and role_id =#{roleId}" +
                    "</if>)"+
                "<if test='indexId!=-1'>" +
                    "and index_id =#{indexId}" +
                "</if>"+
            "</script>")
    int selectResult(@Param("courseId") int courseId,@Param("indexId") int indexId, @Param("roleId") int roleId,@Param("score") double score);
}
