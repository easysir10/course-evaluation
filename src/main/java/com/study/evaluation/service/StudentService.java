package com.study.evaluation.service;

import com.study.evaluation.bean.CourseBean;
import com.study.evaluation.bean.StudentBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/03/18 11:41
 */
public interface StudentService {

    /**
     * 学生登录验证
     */
    StudentBean loginCheck(int studentNo, String studentPassword);

    /**
     * 修改学生密码
     */
    int updatePasswd(int studentNo, String phone,String studentPassword);

    /**
     * 查找学生信息
     */
    StudentBean selectByNo(String no);

    /**
     * 查找所有教师信息
     */
    List<StudentBean> selectAll();

    /**
     * 删除学生信息
     */
    int deleteById(int id);

    /**
     * 修改学生信息
     */
    int updateById(StudentBean studentBean);

    /**
     * 插入学生信息
     */
    int[] insertBatch(MultipartFile file);

    /**
     * 查找学生课程信息
     */
    List<CourseBean> selectStuCourse(int id);
}
