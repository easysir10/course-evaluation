package com.study.evaluation.dao;

import com.study.evaluation.bean.ClassBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/04/30 12:26
 */
@Mapper
public interface ClassDao {

    /**
     * 查询所有的班级信息
     */
    @Select("SELECT class_id," + "class_no," + "class_major,"
                    + "(select count(*) from `student` where class_id=c.class_id)as class_count," + "teacher_id,"
                    + "(select teacher_no from teacher where teacher_id=c.teacher_id) as teacher_no,"
                    + "( select teacher_name FROM teacher WHERE teacher_id = c.teacher_id ) AS teacher_name,"
                    + "institute_id,"
                    + "( SELECT institute_name FROM institute WHERE institute_id = c.institute_id ) AS institute_name "
                    + "FROM `class` c; ")
    List<ClassBean> selectAll();

    /**
     * 根据班级id删除班级信息
     */
    @Delete("delete from `class` where class_id=#{id};")
    int deleteById(@Param("id") int id);

    /**
     * 修改班级信息
     */
    @Update("update `class` set class_no=#{bean.classNo},class_major=#{bean.classMajor},"
                    + "teacher_id=#{bean.teacherId},institute_id=#{bean.instituteId} "
                    + "where class_id=#{bean.classId};")
    int updateById(@Param("bean") ClassBean bean);


    /**
     * 插入班级信息
     */
    @Insert("insert into `class`(class_no,class_major,teacher_id,institute_id) "
                    + "values(#{bean.classNo},#{bean.classMajor},#{bean.teacherId},#{bean.instituteId})")
    int insert(@Param("bean") ClassBean bean);


}
