package com.study.evaluation.service.Impl;

import com.study.evaluation.bean.IndexBean;
import com.study.evaluation.bean.UtilBean;
import com.study.evaluation.dao.IndexDao;
import com.study.evaluation.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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

        for (IndexBean indexBean : list) {
            indexBean.setSeedList(indexDao.selectTwo(indexBean.getIndexId()));
            for (IndexBean indexBean1 : indexBean.getSeedList()) {
                indexBean1.setSeedList(indexDao.selectThree(indexBean1.getIndexId()));
            }
        }

        return list;
    }

    @Override
    public List<IndexBean> countIndex(int courseId, int roleId) {
        List<IndexBean> list = displayIndex();
        for (IndexBean indexBean1 : list) {
            for (IndexBean indexBean2 : indexBean1.getSeedList()) {
                for (IndexBean indexBean3 : indexBean2.getSeedList()) {
                    indexBean3.setRes1(indexDao.selectResult(courseId, indexBean3.getIndexId(), roleId, 100));
                    indexBean3.setRes2(indexDao.selectResult(courseId, indexBean3.getIndexId(), roleId, 80));
                    indexBean3.setRes3(indexDao.selectResult(courseId, indexBean3.getIndexId(), roleId, 60));
                    indexBean3.setRes4(indexDao.selectResult(courseId, indexBean3.getIndexId(), roleId, 40));
                    indexBean3.setRes5(indexDao.selectResult(courseId, indexBean3.getIndexId(), roleId, 20));
                }
            }
        }
        return list;
    }

    /**
     * 获取三级指标数据信息
     *
     * @param courseId 课程id
     */
    @Override
    public HashMap<String, List> getIndexScoreDetail(int courseId) {

        HashMap<String, List> hashMap = new HashMap<>();

        List<String> indexLabel;
        List<Integer> indexCount1;
        List<Integer> indexCount2;
        List<Integer> indexCount3;
        List<Integer> indexCount4;
        List<Integer> indexCount5;
        List<IndexBean> list = countIndex(courseId, -1);
        indexLabel = new ArrayList<>();
        indexCount1 = new ArrayList<>();
        indexCount2 = new ArrayList<>();
        indexCount3 = new ArrayList<>();
        indexCount4 = new ArrayList<>();
        indexCount5 = new ArrayList<>();
        for (IndexBean bean1 : list) {
            for (IndexBean bean2 : bean1.getSeedList()) {
                for (IndexBean bean3 : bean2.getSeedList()) {
                    indexLabel.add(bean3.getIndexName());
                    indexCount1.add(bean3.getRes1());
                    indexCount2.add(bean3.getRes2());
                    indexCount3.add(bean3.getRes3());
                    indexCount4.add(bean3.getRes4());
                    indexCount5.add(bean3.getRes5());
                }
            }
        }
        hashMap.put("indexLabel", indexLabel);
        hashMap.put("indexCount1", indexCount1);
        hashMap.put("indexCount2", indexCount2);
        hashMap.put("indexCount3", indexCount3);
        hashMap.put("indexCount4", indexCount4);
        hashMap.put("indexCount5", indexCount5);

        return hashMap;
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
        int id = indexBean.getParentId();
        if (id == 0) {
            indexBean.setIndexGrade(1);
        } else {
            indexBean.setIndexGrade(indexDao.selectIndexGrade(id) + 1);
        }

        return indexDao.insert(indexBean);
    }

    @Override
    public UtilBean getIndexTree() {

        List<IndexBean> list = displayIndex();
        UtilBean bean = new UtilBean("评价指标", new ArrayList<>(list.size()));

        for(int i = 0; i < list.size(); i++) {
            bean.getChildren().add(new UtilBean(list.get(i).getIndexName(), new ArrayList<>(list.get(i).getSeedList().size())));
            for (int j = 0;j<list.get(i).getSeedList().size();j++){
                bean.getChildren().get(i).getChildren().add(new UtilBean(list.get(i).getSeedList().get(j).getIndexName(), new ArrayList<>(list.get(i).getSeedList().get(j).getSeedList().size())));
                for (int k=0;k<list.get(i).getSeedList().get(j).getSeedList().size();k++){
                    bean.getChildren().get(i).getChildren().get(j).getChildren().add(new UtilBean(
                            list.get(i).getSeedList().get(j).getSeedList().get(k).getIndexName(),
                            new ArrayList<>()));
                }
            }
        }



        return bean;
    }
}
