package com.study.evaluation.service.Impl;

import com.study.evaluation.bean.ClassBean;
import com.study.evaluation.bean.CourseTypeBean;
import com.study.evaluation.bean.InstituteBean;
import com.study.evaluation.bean.TitleBean;
import com.study.evaluation.dao.SelectDao;
import com.study.evaluation.service.SelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/04/06 11:43
 */
@Service
public class SelectServiceImpl implements SelectService {

    @Autowired
    SelectDao selectDao;

    /**
     * 查询所有的教师职称
     */
    @Override
    public List<TitleBean> selectTitle() {
        return selectDao.selectTitle();
    }

    /**
     * 查询所有的学院名称
     */
    @Override
    public List<InstituteBean> selectInstitute() {
        return selectDao.selectInstitute();
    }

    /**
     * 查询所有的班级
     */
    @Override
    public List<ClassBean> selectClass() {
        return selectDao.selectClass();
    }

    /**
     * 查询所有的课程类型
     */
    @Override
    public List<CourseTypeBean> selectCourseType() {
        return selectDao.selectCourseType();
    }
}
