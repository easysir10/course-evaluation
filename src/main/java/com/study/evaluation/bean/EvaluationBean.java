package com.study.evaluation.bean;

import lombok.Data;

/**
 * description
 *
 * @author Hu.Wang 2020/04/29 12:07
 */
@Data
public class EvaluationBean {

    private int evaluationId;
    private int evaluationPeopleNo;
    private String evaluationPeopleName;
    private int courseId;
    private String courseName;
    private String evaluationTime;
    private int roleId;
    private String roleName;
    private double evaluationScore;
    private String evaluationAdvice;

}
