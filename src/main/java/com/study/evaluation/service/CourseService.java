package com.study.evaluation.service;

import com.study.evaluation.bean.CourseBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/04/01 11:19
 */
public interface CourseService {

    /**
     * 查询所有的课程信息
     */
    List<CourseBean> selectAll(int id);

    /**
     * 删除课程信息
     */
    int deleteById(int id);

    /**
     * 修改课程信息
     */
    int updateById(CourseBean courseBean);

    /**
     * 插入课程信息
     */
    int[] insertBatch(MultipartFile file);
}
