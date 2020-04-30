package com.study.evaluation.service.Impl;

import com.study.evaluation.bean.ClassBean;
import com.study.evaluation.dao.ClassDao;
import com.study.evaluation.service.ClassService;
import com.study.evaluation.utils.ImportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/04/30 15:49
 */
@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    ClassDao classDao;

    /**
     * 查询所有的班级信息
     */
    @Override
    public List<ClassBean> selectAll() {
        return classDao.selectAll();
    }

    /**
     * 删除班级信息
     *
     * @param id 班级id
     */
    @Override
    public int deleteById(int id) {
        return classDao.deleteById(id);
    }

    /**
     * 修改班级信息
     *
     * @param classBean 班级实体
     */
    @Override
    public int updateById(ClassBean classBean) {
        return classDao.updateById(classBean);
    }

    /**
     * 插入班级信息
     *
     * @param file 插入的文件
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
                ClassBean classBean = new ClassBean();
                classBean.setClassNo(Double.valueOf((String) list.get(0)).intValue());
                classBean.setTeacherId(Double.valueOf((String) list.get(1)).intValue());
                classBean.setInstituteId(Double.valueOf((String) list.get(2)).intValue());
                classBean.setClassMajor((String) list.get(3));
                result[1] += classDao.insert(classBean);
            }
            result[0]=olist.size()-result[1];
        }

        return result;
    }
}
