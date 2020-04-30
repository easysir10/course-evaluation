package com.study.evaluation.service.Impl;

import com.study.evaluation.bean.IndexBean;
import com.study.evaluation.dao.IndexDao;
import com.study.evaluation.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/04/18 12:04
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    IndexDao indexDao;

    @Override
    public List<IndexBean> selectAll() {
        return indexDao.selectAll();
    }

    @Override
    public List<IndexBean> displayIndex() {
        List<IndexBean> list = indexDao.selectOne();

        for (IndexBean indexBean:list){
            indexBean.setSeedList(indexDao.selectTwo(indexBean.getIndexId()));
            for(IndexBean indexBean1:indexBean.getSeedList()){
                indexBean1.setSeedList(indexDao.selectThree(indexBean1.getIndexId()));
            }
        }

        return list;
    }

    /**
     * 删除指标信息
     *
     * @param id 指标id
     */
    @Override
    public int deleteById(int id) {
        return indexDao.deleteById(id);
    }

    /**
     * 修改指标信息
     *
     * @param indexBean 指标实体
     */
    @Override
    public int updateById(IndexBean indexBean) {
        return indexDao.updateById(indexBean);
    }

    /**
     * 插入指标信息
     *
     * @param indexBean 指标实体
     */
    @Override
    public int insertIndex(IndexBean indexBean) {
        int id =  indexBean.getParentId();
        if (id==0){
            indexBean.setIndexGrade(1);
        }else {
            indexBean.setIndexGrade(indexDao.selectIndexGrade(id)+1);
        }

        return indexDao.insert(indexBean);
    }
}
