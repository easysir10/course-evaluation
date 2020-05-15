package com.study.evaluation.service;

import com.study.evaluation.bean.EvaluationBean;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/04/27 13:22
 */
public interface EvaluationService {

    /**
     * 提交学生评价信息
     */
    boolean submitStuEvaluation(Integer[] indexScore, int courseId, int personId, String evaluationAdvice,
                    HttpSession session, int midId);

    /**
     * 提交教师评价信息
     */
    boolean submitTeaEvaluation(Integer[] indexScore, int courseId, int personId, String evaluationAdvice,
                    HttpSession session, int midId);

    /**
     * 提交同行评价信息
     */
    boolean submitCompanyEvaluation(Integer[] indexScore, int courseId, String evaluationAdvice,
                    HttpSession session);

    /**
     * 根据指标等级获取指标个数
     */
    int getIndexCount(int grade);

    /**
     * 查询所有评价信息
     */
    List<EvaluationBean> selectAll();

    /**
     * 删除评价信息
     */
    int deleteById(int id);

    /**
     * 插入评价信息
     */
    int[] insertBatch(MultipartFile file);


    HashMap<String,List> getOneIndexScore(int courseId);
}
