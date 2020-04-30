package com.study.evaluation.service;

import com.study.evaluation.bean.ClassBean;
import com.study.evaluation.bean.CourseTypeBean;
import com.study.evaluation.bean.InstituteBean;
import com.study.evaluation.bean.TitleBean;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/04/06 11:41
 */
public interface SelectService {

    /**
     * 查询所有的教师职称
     */
    List<TitleBean> selectTitle();

    /**
     * 查询所有的学院名称
     */
    List<InstituteBean> selectInstitute();

    /**
     * 查询所有的班级
     */
    List<ClassBean> selectClass();

    /**
     * 查询所有的课程类型
     */
    List<CourseTypeBean> selectCourseType();
}
