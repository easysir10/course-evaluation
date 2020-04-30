package com.study.evaluation.dao;

import com.study.evaluation.bean.CourseBean;
import com.study.evaluation.bean.TeacherBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/03/18 11:58
 */
@Mapper
public interface TeacherDao {
    @Select("select * from teacher t1,title t2,institute i " +
            "where t1.teacher_no=#{teacherNo} and t1.teacher_password=#{teacherPassword} and t1.teacher_permission=#{type}" +
            " and teacher_permission=#{type} and t1.title_id=t2.title_id and t1.institute_id=i.institute_id;")
    TeacherBean loginCheck(@Param("teacherNo") int teacherNo,
                           @Param("teacherPassword") String teacherPassword,
                           @Param("type") int type);

    @Update("update teacher set teacher_password=#{teacherPassword} where teacher_phone=#{teacherPhone} " +
            " and teacher_no=#{teacherNo};")
    int updatePasswd(@Param("teacherNo") int teacherNo,
                     @Param("teacherPhone") String teacherPhone,
                     @Param("teacherPassword") String teacherPassword);

    @Select("select * from teacher t1,title t2,institute i where teacher_permission=0 " +
            "and t1.title_id=t2.title_id and t1.institute_id=i.institute_id;")
    List<TeacherBean> selectAll();

    @Delete("delete from teacher where teacher_id = #{id}")
    int deleteById(@Param("id") int id);

    @Update("update teacher set teacher_no=#{teacherBean.teacherNo}," +
            "teacher_name=#{teacherBean.teacherName}," +
            "teacher_gender=#{teacherBean.teacherGender}," +
            "teacher_card_no=#{teacherBean.teacherCardNo}," +
            "teacher_phone=#{teacherBean.teacherPhone}," +
            "teacher_password=#{teacherBean.teacherPassword}," +
            "institute_id=#{teacherBean.instituteId}," +
            "title_id=#{teacherBean.titleId} where teacher_id=#{teacherBean.teacherId}")
    int updateById(@Param("teacherBean") TeacherBean teacherBean);

    @Insert("insert into teacher(teacher_no,teacher_name,teacher_Gender,teacher_card_no,institute_id,title_id,teacher_phone,teacher_password) " +
            "values(#{teacherBean.teacherNo},#{teacherBean.teacherName},#{teacherBean.teacherGender}," +
            "#{teacherBean.teacherCardNo},#{teacherBean.instituteId},#{teacherBean.titleId}," +
            "#{teacherBean.teacherPhone},#{teacherBean.teacherPassword})")
    int insert(@Param("teacherBean") TeacherBean teacherBean);

    @Select("select * from course c,course_type ct,teacher_course tc " +
            " where c.type_id=ct.type_id  and c.course_id=tc.course_id " +
            "and tc.teacher_id=#{id};")
    List<CourseBean> selectTeaCourse(@Param("id") int id);
}
