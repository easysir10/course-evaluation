package com.study.evaluation.dao;

import com.study.evaluation.bean.ClassBean;
import com.study.evaluation.bean.CourseTypeBean;
import com.study.evaluation.bean.InstituteBean;
import com.study.evaluation.bean.TitleBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/04/06 11:39
 */
@Mapper
public interface SelectDao {

    @Select("select * from title;")
    List<TitleBean> selectTitle();

    @Select("select * from institute;")
    List<InstituteBean> selectInstitute();

    @Select("select * from class c,teacher t where c.teacher_id=t.teacher_id;")
    List<ClassBean> selectClass();

    @Select("select * from course_type;")
    List<CourseTypeBean> selectCourseType();

}
