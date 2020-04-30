package com.study.evaluation.service.Impl;

import com.study.evaluation.bean.CourseBean;
import com.study.evaluation.bean.StudentBean;
import com.study.evaluation.dao.StudentDao;
import com.study.evaluation.service.StudentService;
import com.study.evaluation.utils.ImportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/03/18 11:44
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentDao studentDao;

    /**
     * 学生登录验证
     *
     * @param studentNo 学号
     * @param studentPassword 密码
     */
    @Override
    public StudentBean loginCheck(int studentNo, String studentPassword) {
        return studentDao.loginCheck(studentNo, studentPassword);
    }

    /**
     * 修改用户密码
     *
     * @param phone 学生电话
     * @param studentPassword 新密码
     */
    @Override
    public int updatePasswd(int studentNo,String phone, String studentPassword) {
        return studentDao.updatePasswd(studentNo,phone,studentPassword);
    }

    /**
     * 查找学生信息
     *
     * @param no 学号
     */
    @Override
    public StudentBean selectByNo(String no) {
        return null;
    }

    /**
     * 查找所有教师信息
     */
    @Override
    public List<StudentBean> selectAll() {
        return studentDao.selectAll();
    }

    /**
     * 删除学生信息
     *
     * @param id 学生id
     */
    @Override
    public int deleteById(int id) {
        return studentDao.deleteById(id);
    }

    /**
     * 修改学生信息
     *
     * @param studentBean 学生实体
     */
    @Override
    public int updateById(StudentBean studentBean) {
        return studentDao.updateById(studentBean);
    }

    /**
     * 插入学生信息
     *
     * @param file
     */
    @Override
    public int[] insertBatch(MultipartFile file) {
        int []result=new int[2];
        List<List<Object>> olist = null;
        try {
            olist = ImportExcelUtil.getListByExcel(file.getInputStream(), file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (olist != null) {
            for (List<Object> list : olist) {
                StudentBean studentBean = new StudentBean();
                studentBean.setStudentNo(Double.valueOf((String) list.get(0)).intValue());
                studentBean.setStudentName((String) list.get(1));
                studentBean.setStudentGender((String) list.get(2));
                studentBean.setStudentDormNo((String) list.get(3));
                studentBean.setStudentCardNo((String) list.get(4));
                studentBean.setInstituteId(Double.valueOf((String) list.get(5)).intValue());
                studentBean.setStudentPhone((String) list.get(6));
                studentBean.setStudentPassword((String) list.get(7));
                studentBean.setClassId(Double.valueOf((String) list.get(8)).intValue());

                result[1] += studentDao.insert(studentBean);
            }
            result[0]=olist.size()-result[1];
        }

        return result;
    }

    /**
     * 查找学生课程信息
     *
     * @param id 学生id
     */
    @Override
    public List<CourseBean> selectStuCourse(int id) {
        return studentDao.selectStuCourse(id);
    }
}
