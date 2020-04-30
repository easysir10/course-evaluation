package com.study.evaluation.service.Impl;

import com.study.evaluation.bean.CourseBean;
import com.study.evaluation.bean.TeacherBean;
import com.study.evaluation.dao.TeacherDao;
import com.study.evaluation.service.TeacherService;
import com.study.evaluation.utils.ImportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/03/23 10:55
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherDao teacherDao;

    /**
     * 学生登录验证
     *
     * @param teacherNo 教师工号
     * @param teacherPassword 密码
     */
    @Override
    public TeacherBean loginCheck(int teacherNo, String teacherPassword,int type) {
        return teacherDao.loginCheck(teacherNo,teacherPassword,type);
    }

    /**
     * 修改教师密码
     *
     * @param phone 教师电话
     * @param teacherPassword 新密码
     */
    @Override
    public int updatePasswd(int teacherNo,String phone, String teacherPassword) {
        return teacherDao.updatePasswd(teacherNo,phone,teacherPassword);
    }

    /**
     * 查找所有教师信息
     */
    @Override
    public List<TeacherBean> selectAll() {
        return teacherDao.selectAll();
    }

    /**
     * 删除教师信息
     *
     * @param id 教师id
     */
    @Override
    public int deleteById(int id) {
        return teacherDao.deleteById(id);
    }

    /**
     * 修改教师信息
     *
     * @param teacherBean 修改的教师实体
     */
    @Override
    public int updateById(TeacherBean teacherBean) {
        return teacherDao.updateById(teacherBean);
    }

    /**
     * 插入学生信息
     *
     * @param file 导入文件
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
                TeacherBean teacherBean = new TeacherBean();
                teacherBean.setTeacherNo(Double.valueOf(String.valueOf(list.get(0))).intValue());
                teacherBean.setTeacherName((String) list.get(1));
                teacherBean.setTeacherGender((String) list.get(2));
                teacherBean.setTeacherCardNo((String) list.get(3));
                teacherBean.setInstituteId(Double.valueOf(String.valueOf(list.get(4))).intValue());
                teacherBean.setTitleId(Double.valueOf(String.valueOf(list.get(5))).intValue());
                teacherBean.setTeacherPhone((String) list.get(6));
                teacherBean.setTeacherPassword((String) list.get(7));
                result[1] += teacherDao.insert(teacherBean);
            }
            result[0]=olist.size()-result[1];
        }

        return result;
    }

    /**
     * 查找教师课程信息
     *
     * @param id 教师id
     */
    @Override
    public List<CourseBean> selectTeaCourse(int id) {
        return teacherDao.selectTeaCourse(id);
    }

}
