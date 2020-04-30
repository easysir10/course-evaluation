package com.study.evaluation.service.Impl;

import com.study.evaluation.bean.CourseBean;
import com.study.evaluation.dao.CourseDao;
import com.study.evaluation.service.CourseService;
import com.study.evaluation.utils.ImportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/04/01 11:20
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseDao courseDao;

    /**
     * 查询所有的课程信息
     */
    @Override
    public List<CourseBean> selectAll() {
        return courseDao.selectAll();
    }

    /**
     * 删除课程信息
     *
     * @param id 课程id
     */
    @Override
    public int deleteById(int id) {
        return courseDao.deleteById(id);
    }

    /**
     * 修改课程信息
     *
     * @param courseBean 课程实体
     */
    @Override
    public int updateById(CourseBean courseBean) {
        return courseDao.updateById(courseBean);
    }

    /**
     * 插入课程信息
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
                CourseBean courseBean = new CourseBean();
                System.out.println(list.toString());
                courseBean.setCourseName((String) list.get(0));
                courseBean.setCourseCredit(new Double(String.valueOf(list.get(1))));
                courseBean.setTypeId(Double.valueOf((String) list.get(2)).intValue());
                result[1] += courseDao.insert(courseBean);
            }
            result[0]=olist.size()-result[1];
        }

        return result;
    }


}
