package com.study.evaluation.service.Impl;

import com.study.evaluation.bean.CourseBean;
import com.study.evaluation.bean.IndexBean;
import com.study.evaluation.bean.RoleBean;
import com.study.evaluation.dao.CourseDao;
import com.study.evaluation.dao.IndexDao;
import com.study.evaluation.service.CourseService;
import com.study.evaluation.service.IndexService;
import com.study.evaluation.service.RoleService;
import com.study.evaluation.utils.ImportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;

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
    @Autowired
    IndexDao indexDao;
    @Autowired
    IndexService indexService;
    @Autowired
    RoleService roleService;

    /**
     * 查询所有的课程信息
     */
    @Override
    public List<CourseBean> selectAll(int id) {
        List<CourseBean> list = courseDao.selectAll(id);
        for (CourseBean bean : list) {
            if (courseDao.selectResult(bean.getCourseId()) != null) {
                CourseBean res = courseDao.selectResult(bean.getCourseId());
                bean.setCount(res.getCount());
                bean.setAvgScore(Double.parseDouble(String.format("%.2f", res.getAvgScore())));
                bean.setTeacherCount(res.getTeacherCount());
                bean.setStudentCount(res.getStudentCount());
                bean.setOtherCount(res.getOtherCount());
            }
            if (id != -1) {
                bean.setRes1(indexDao.selectResult(bean.getCourseId(), -1,-1, 100));
                bean.setRes2(indexDao.selectResult(bean.getCourseId(), -1,-1, 80));
                bean.setRes3(indexDao.selectResult(bean.getCourseId(), -1,-1, 60));
                bean.setRes4(indexDao.selectResult(bean.getCourseId(), -1,-1,40));
                bean.setRes5(indexDao.selectResult(bean.getCourseId(), -1,-1,20));
                if (bean.getCount() != 0) {
                    bean.setResult(overallEva(id));
                }
            }
        }
        return list;
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

        int[] result = new int[2];
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
            result[0] = olist.size() - result[1];
        }

        return result;
    }

    /**
     *
     * 利用模糊综合评价进行课程最终结果的评价计算
     *
     */
    private String overallEva(int courseId) {

        // 各级指标的权重总和
        double sum0 = 0, sum1, sum2, sum3;



        // 查询出角色权重,并计算出权重总和
        List<RoleBean> roleBeanList = roleService.selectAll();
        // 角色的权重向量数组
        Matrix a01 = DenseMatrix.Factory.zeros(1, roleBeanList.size());
        Matrix a02 = DenseMatrix.Factory.zeros(roleBeanList.size(), 5);
        int i0=0;

        for (RoleBean bean : roleBeanList) {
            sum0 += bean.getRolePercent();
        }
        for (RoleBean bean:roleBeanList) {
            List<IndexBean> list = indexService.countIndex(courseId,bean.getRoleId());

            sum1=0;
            Matrix a11 = DenseMatrix.Factory.zeros(1, list.size());
            Matrix a12 = DenseMatrix.Factory.zeros(list.size(), 5);
            int i1 = 0;
            // 计算所有一级指标的权重总和
            for (IndexBean index1 : list) {
                sum1 += index1.getIndexPercent();
            }

            for (IndexBean index1 : list) {
                sum2 = 0;
                Matrix a21 = DenseMatrix.Factory.zeros(1, index1.getSeedList().size());
                Matrix a22 = DenseMatrix.Factory.zeros(index1.getSeedList().size(), 5);
                int i2 = 0;

                // 计算所有二级指标的权重总和
                for (IndexBean index2 : index1.getSeedList()) {
                    sum2 += index2.getIndexPercent();
                }

                for (IndexBean index2 : index1.getSeedList()) {
                    sum3 = 0;
                    Matrix a31 = DenseMatrix.Factory.zeros(1, index2.getSeedList().size());
                    Matrix a32 = DenseMatrix.Factory.zeros(index2.getSeedList().size(), 5);
                    int i3 = 0;
                    // 计算三级指标的权重总和
                    for (IndexBean index3 : index2.getSeedList()) {
                        sum3 += index3.getIndexPercent();
                    }

                    for (IndexBean index3 : index2.getSeedList()) {
                        a31.setAsDouble(index3.getIndexPercent() / sum3, 0, i3);
                        if (index3.getSumRes()!=0){
                            a32.setAsDouble((double) index3.getRes1() / index3.getSumRes(), i3, 0);
                            a32.setAsDouble((double) index3.getRes2() / index3.getSumRes(), i3, 1);
                            a32.setAsDouble((double) index3.getRes3() / index3.getSumRes(), i3, 2);
                            a32.setAsDouble((double) index3.getRes4() / index3.getSumRes(), i3, 3);
                            a32.setAsDouble((double) index3.getRes5() / index3.getSumRes(), i3, 4);
                        }else {
                            a32.setAsDouble(0, i3, 0);
                            a32.setAsDouble(0, i3, 1);
                            a32.setAsDouble(0, i3, 2);
                            a32.setAsDouble(0, i3, 3);
                            a32.setAsDouble(0, i3, 4);
                        }
                        i3++;
                    }
                    a21.setAsDouble(index2.getIndexPercent() / sum2, 0, i2);
                    for (int j = 0; j < a31.mtimes(a32).getColumnCount(); j++) {
                        a22.setAsDouble(a31.mtimes(a32).getAsDouble(0, j), i2, j);
                    }
                    i2++;
                }
                a11.setAsDouble(index1.getIndexPercent() / sum1, 0, i1);
                for (int j = 0; j < a21.mtimes(a22).getColumnCount(); j++) {
                    a12.setAsDouble(a21.mtimes(a22).getAsDouble(0, j), i1, j);
                }
                i1++;
            }
            a01.setAsDouble(bean.getRolePercent() / sum0,0,i0);
            for (int j = 0; j < a11.mtimes(a12).getColumnCount(); j++) {
                a02.setAsDouble(a11.mtimes(a12).getAsDouble(0, j), i0, j);
            }
            i0++;
        }

        Matrix a = a01.mtimes(a02);
        int maxIndex = 0;
        for (int i = 0; i < a.getColumnCount() - 1; i++) {
            if (a.getAsDouble(0, i) < a.getAsDouble(0, i + 1)) {
                maxIndex = i + 1;
            }
        }
        String result;
        if (maxIndex == 0) {
            result = "优秀";
        } else if (maxIndex == 1) {
            result = "良好";
        } else if (maxIndex == 2) {
            result = "一般";
        } else if (maxIndex == 3) {
            result = "差";
        } else {
            result = "极差";
        }
        return result;
    }


}
