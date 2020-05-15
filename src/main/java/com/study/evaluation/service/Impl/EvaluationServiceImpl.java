package com.study.evaluation.service.Impl;

import com.study.evaluation.bean.EvaluationBean;
import com.study.evaluation.bean.IndexBean;
import com.study.evaluation.dao.EvaluationDao;
import com.study.evaluation.dao.IndexDao;
import com.study.evaluation.service.EvaluationService;
import com.study.evaluation.service.IndexService;
import com.study.evaluation.utils.ImportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/04/27 13:25
 */
@Service
public class EvaluationServiceImpl implements EvaluationService {
    @Autowired
    IndexService indexService;
    @Autowired
    IndexDao indexDao;

    @Autowired
    EvaluationDao evaluationDao;

    /**
     * 提交学生评价信息
     *
     * @param indexScore 各项指标分数
     * @param courseId 评价课程id
     * @param evaluationAdvice 意见或建议
     * @param session session
     */
    @Override
    public boolean submitStuEvaluation(Integer[] indexScore, int courseId, int personNo, String evaluationAdvice,
                    HttpSession session, int midId) {
        // 插入一条评价数据
        evaluationDao.submitEvaluation(personNo, courseId, 2, 0, evaluationAdvice);
        // 计算课程最终得分
        double score = dealEvaluation(indexScore);
        // 修改课程评价状态
        boolean result = evaluationDao.updateStuCourse(midId,personNo,1);
        session.setAttribute("messageT1","最终得分："+String.format("%.2f", score)+"分");
        return result;
    }

    /**
     * 提交教师评价信息
     *
     * @param indexScore 各项指标分数
     * @param courseId 评价课程id
     * @param evaluationAdvice 意见或建议
     * @param session session
     */
    @Override
    public boolean submitTeaEvaluation(Integer[] indexScore, int courseId, int personNo, String evaluationAdvice, HttpSession session, int midId) {

        // 插入一条评价数据
        evaluationDao.submitEvaluation(personNo, courseId, 0, 0, evaluationAdvice);
        // 计算课程最终得分
        double score = dealEvaluation(indexScore);
        // 修改课程评价状态
        boolean result = evaluationDao.updateTeaCourse(midId,1);
        session.setAttribute("messageT1","最终得分："+String.format("%.2f", score)+"分");
        return result;
    }

    /**
     * 提交同行评价信息
     *
     * @param indexScore 各项指标分数
     * @param courseId 评价课程id
     * @param evaluationAdvice 意见或建议
     * @param session session
     */
    @Override
    public boolean submitCompanyEvaluation(Integer[] indexScore, int courseId, String evaluationAdvice, HttpSession session) {
        // 插入一条评价数据
        boolean result = evaluationDao.submitEvaluation(-1, courseId, 1, 0, evaluationAdvice);
        // 计算课程最终得分
        double score = dealEvaluation(indexScore);
        session.setAttribute("messageT1","最终得分："+String.format("%.2f", score)+"分");
        return result;
    }


    /**
     * 根据指标等级获取指标个数
     *
     * @param grade 指标等级
     */
    @Override
    public int getIndexCount(int grade) {
        return evaluationDao.getIndexSize(grade);
    }

    /**
     * 查询所有评价信息
     */
    @Override
    public List<EvaluationBean> selectAll() {
        List<EvaluationBean> list = evaluationDao.selectTeaEvaluation();
        list.addAll(evaluationDao.selectStuEvaluation());
        return list;
    }

    /**
     * 删除评价信息
     *
     * @param id 评价id
     */
    @Override
    public int deleteById(int id) {
        return evaluationDao.deleteById(id)+evaluationDao.deleteIndexById(id);
    }

    /**
     * 插入评价信息
     *
     * @param file 导入的文件
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
                EvaluationBean evaluationBean = new EvaluationBean();
                evaluationBean.setEvaluationPeopleNo(Double.valueOf((String) list.get(0)).intValue());
                evaluationBean.setCourseId(Double.valueOf((String) list.get(1)).intValue());
                evaluationBean.setEvaluationTime((String) list.get(2));
                evaluationBean.setRoleId(Double.valueOf((String) list.get(3)).intValue());
                evaluationBean.setEvaluationScore(new Double(String.valueOf(list.get(4))));
                evaluationBean.setEvaluationAdvice((String) list.get(5));
                result[1] += evaluationDao.insert(evaluationBean);
            }
            result[0]=olist.size()-result[1];
        }

        return result;
    }

    @Override
    public HashMap<String, List> getOneIndexScore(int courseId) {
        HashMap<String, List> map = new HashMap<>();

        List<String> list1 = new ArrayList<>();
        List<Double> list2 = new ArrayList<>();
        List<IndexBean> list = indexDao.selectOne();

        for(IndexBean bean:list) {
            list1.add(bean.getIndexName());
            list2.add(evaluationDao.selectOneIndexScore(courseId,bean.getIndexId()));
        }
        map.put("name",list1);
        map.put("score",list2);
        return map;
    }

    /**
     *
     *
     * @param indexScore     指标得分
     * @return double
     */
    private double dealEvaluation(Integer[] indexScore){
        // 查询出所有的指标
        List<IndexBean> list = indexService.displayIndex();

        // 获取最新插入的id
        int evaluationId = evaluationDao.getInsertId();

        int i = 0;
        double sum1 = 0, sum2, sum3;
        double score1 = 0, score2, score3;

        // 计算所有一级指标的权重总和
        for (IndexBean index1 : list) {
            sum1 += index1.getIndexPercent();
        }

        for (IndexBean index1 : list) {
            sum2 = 0;
            score2 = 0;

            // 计算所有二级指标的权重总和
            for (IndexBean index2 : index1.getSeedList()) {
                sum2 += index2.getIndexPercent();
            }

            for (IndexBean index2 : index1.getSeedList()) {
                sum3 = 0;
                score3 = 0;

                // 计算所有三级指标的权重总和
                for (IndexBean index3 : index2.getSeedList()) {
                    sum3 += index3.getIndexPercent();
                }

                for (IndexBean index3 : index2.getSeedList()) {
                    // 插入三级指标得分
                    evaluationDao.insertIndexScore(evaluationId, index3.getIndexId(), indexScore[i]);
                    // // 计算二级指标得分
                    score3 += (index3.getIndexPercent() / sum3) * indexScore[i];
                    i++;
                }
                // 插入二级指标得分
                evaluationDao.insertIndexScore(evaluationId, index2.getIndexId(), score3);
                // 计算一级指标得分
                score2 += index2.getIndexPercent() / sum2 * score3;
            }
            // 插入一级指标得分
            evaluationDao.insertIndexScore(evaluationId, index1.getIndexId(), score2);
            // 计算总得分
            score1 += index1.getIndexPercent() / sum1 * score2;
        }
        // 插入本次评价总得分
        evaluationDao.updateEvaluation(score1, evaluationId);

        return score1;
    }
}
