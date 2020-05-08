package com.study.evaluation.bean;

import lombok.Data;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/04/13 12:38
 */
@Data
public class IndexBean {

    private int indexId; // 指标id
    private String indexName; // 指标名
    private double indexPercent; // 指标权重
    private int indexGrade; // 指标等级
    private int parentId; // 父指标id
    private String parentName; // 父指标名
    private int indexScore; // 指标得分

    private List<IndexBean> seedList;

    private int res1 = 0; // 优秀数量
    private int res2 = 0; // 良好数量
    private int res3 = 0; // 一般数量
    private int res4 = 0; // 差数量
    private int res5 = 0; // 极差数量


    public int getSumRes(){
        return res1+res2+res3+res4+res5;
    }

}
