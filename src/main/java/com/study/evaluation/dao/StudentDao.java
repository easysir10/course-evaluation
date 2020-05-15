package com.study.evaluation.dao;

import com.study.evaluation.bean.CourseBean;
import com.study.evaluation.bean.StudentBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/03/18 11:58
 */
@Mapper
public interface StudentDao {

    @Select("select * from student s,class c,institute i "
                    + "where s.student_no=#{studentNo} and s.student_password=#{studentPassword} "
                    + "and s.class_id=c.class_id and s.institute_id=i.institute_id;")
    StudentBean loginCheck(@Param("studentNo") int studentNo, @Param("studentPassword") String studentPassword);

    @Select("select * from student where student_no=#{studentNo};")
    StudentBean selectByNo(@Param("studentNo") int studentNo);

    @Update("update student set student_password=#{studentPassword} where student_phone=#{studentPhone} "
                    + " and student_no=#{studentNo};")
    int updatePasswd(@Param("studentNo") int studentNo, @Param("studentPhone") String studentPhone,
                    @Param("studentPassword") String studentPassword);

    @Select("select * from student s,class c,institute i where s.class_id=c.class_id "
                    + "and s.institute_id=i.institute_id;")
    List<StudentBean> selectAll();

    @Delete("delete from student where student_id = #{id}")
    int deleteById(@Param("id") int id);

    @Update("update student set student_no=#{studentBean.studentNo}," + "student_name=#{studentBean.studentName},"
                    + "student_gender=#{studentBean.studentGender}," + "student_dorm_no=#{studentBean.studentDormNo},"
                    + "student_card_no=#{studentBean.studentCardNo}," + "student_phone=#{studentBean.studentPhone},"
                    + "student_password=#{studentBean.studentPassword}," + "institute_id=#{studentBean.instituteId},"
                    + "class_id=#{studentBean.classId} where student_id=#{studentBean.studentId}")
    int updateById(@Param("studentBean") StudentBean studentBean);

    @Insert("insert into student(`student_no`, `student_name`, `student_gender`, `student_phone`, `student_password`, "
                    + "`student_card_no`, `student_dorm_no`, `institute_id`,`class_id`) "
                    + "values(#{studentBean.studentNo},#{studentBean.studentName},#{studentBean.studentGender},"
                    + "#{studentBean.studentPhone},#{studentBean.studentPassword},#{studentBean.studentCardNo},"
                    + "#{studentBean.studentDormNo},#{studentBean.instituteId},#{studentBean.classId})")
    int insert(@Param("studentBean") StudentBean studentBean);

    @Select("SELECT * FROM student_course sc,teacher_course tc,course c,course_type ct,teacher t WHERE " +
            "sc.student_id = #{id} AND sc.mid_id = tc.mid_id AND c.course_id = tc.course_id AND ct.type_id = c.type_id " +
            "AND t.teacher_id = tc.teacher_id;")
    List<CourseBean> selectStuCourse(@Param("id") int id);
}
