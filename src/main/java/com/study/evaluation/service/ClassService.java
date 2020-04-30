package com.study.evaluation.service;

import com.study.evaluation.bean.ClassBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/04/30 12:32
 */
public interface ClassService {

    /**
     * 查询所有的班级信息
     */
    List<ClassBean> selectAll();

    /**
     * 删除班级信息
     */
    int deleteById(int id);

    /**
     * 修改班级信息
     */
    int updateById(ClassBean classBean);

    /**
     * 插入班级信息
     */
    int[] insertBatch(MultipartFile file);
}
