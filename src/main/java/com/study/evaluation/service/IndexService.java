package com.study.evaluation.service;

import com.study.evaluation.bean.IndexBean;
import com.study.evaluation.bean.UtilBean;

import java.util.HashMap;
import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/04/18 11:37
 */
public interface IndexService {

    List<IndexBean> selectAll();

    List<IndexBean> displayIndex();

    List<IndexBean> countIndex(int courseId,int roleId);

    /**
     * 获取一些指标数据信息
     */
    HashMap<String,List> getIndexScoreDetail(int courseId);

    /**
     * 删除指标信息
     */
    int deleteById(int id);

    /**
     * 修改指标信息
     */
    int updateById(IndexBean indexBean);

    /**
     * 插入学生信息
     */
    int insertIndex(IndexBean indexBean);

    UtilBean getIndexTree();
}
