package com.study.evaluation.service.Impl;

import com.study.evaluation.bean.RoleBean;
import com.study.evaluation.dao.RoleDao;
import com.study.evaluation.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/05/02 10:17
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Override
    public List<RoleBean> selectAll() {
        return roleDao.selectAll();
    }

    @Override
    public int deleteById(int id) {
        return roleDao.deleteById(id);
    }

    @Override
    public int updateById(RoleBean bean) {
        return roleDao.updateById(bean);
    }

    @Override
    public int insert(RoleBean bean) {
        return roleDao.insert(bean);
    }
}
