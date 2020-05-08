package com.study.evaluation.dao;

import com.study.evaluation.bean.CourseBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/04/01 11:18
 */
@Mapper
public interface CourseDao {

    @Select("<script>" + "select * from course c,course_type ct where c.type_id=ct.type_id " + "<if test='id!=-1'>"
                    + "and c.course_id =#{id}" + "</if>" + "</script>")
    List<CourseBean> selectAll(@Param("id") int id);

    @Delete("delete from course where course_id = #{id}")
    int deleteById(@Param("id") int id);

    @Update("update course set course_name=#{courseBean.courseName}," + "course_credit=#{courseBean.courseCredit},"
                    + "type_id=#{courseBean.typeId} " + "where course_id=#{courseBean.courseId}")
    int updateById(@Param("courseBean") CourseBean courseBean);

    @Insert("insert into course(course_name,course_credit,type_id) "
                    + "values(#{courseBean.courseName},#{courseBean.courseCredit},#{courseBean.typeId})")
    int insert(@Param("courseBean") CourseBean courseBean);

    /**
     * 查询课程评价结果
     */
    @Select("select count(*) as count,avg(evaluation_score) as avg_score," +
            "(select count(*) from evaluation where course_id=#{id} and role_id=0)as teacher_count," +
            "(select count(*) from evaluation where course_id=#{id} and role_id=1)as other_count," +
            "(select count(*) from evaluation where course_id=#{id} and role_id=2)as student_count " +
            "from evaluation where course_id=#{id};")
    CourseBean selectResult(@Param("id") int id);
}
