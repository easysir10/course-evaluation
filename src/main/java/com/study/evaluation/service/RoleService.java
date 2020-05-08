package com.study.evaluation.service;

import com.study.evaluation.bean.RoleBean;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/05/02 10:15
 */
public interface RoleService {

    List<RoleBean> selectAll();

    int deleteById(int id);

    int updateById(RoleBean bean);

    int insert(RoleBean bean);
}
