package com.study.evaluation.service;

import com.study.evaluation.bean.CourseBean;
import com.study.evaluation.bean.TeacherBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/03/18 11:43
 */
public interface TeacherService {

    /**
     * 教师登录验证
     */
    TeacherBean loginCheck(int teacherNo, String teacherPassword,int type);

    /**
     * 修改教师密码
     */
    int updatePasswd(int teacherNo, String phone,String teacherPassword);


    /**
     * 查找所有教师信息
     */
    List<TeacherBean> selectAll();

    /**
     * 删除教师信息
     */
    int deleteById(int id);

    /**
     * 修改教师信息
     */
    int updateById(TeacherBean teacherBean);

    /**
     * 插入学生信息
     */
    int[] insertBatch(MultipartFile file);

    /**
     * 查找教师课程信息
     */
    List<CourseBean> selectTeaCourse(int id);
}
