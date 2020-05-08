package com.study.evaluation.bean;

import lombok.Data;

/**
 * description
 *
 * @author Hu.Wang 2020/04/07 20:57
 */
@Data
public class CourseBean {

    private int courseId; // 课程id
    private String courseName; // 课程名
    private double courseCredit;// 学分
    private int typeId; // 课程类型id
    private String typeName; // 课程类型
    private int midId; // 中间表id
    private int teacherId; // 任课老师id
    private String teacherName; // 任课老师姓名
    private int status; // 课程是否已评价：0未评价，1已评价

    private int count = 0; // 评价总数
    private int teacherCount = 0; // 教师评价数
    private int studentCount = 0; // 学生评价数
    private int otherCount = 0; // 同行评价数
    private double avgScore = 0.00; // 平均分
    private String result = "暂无评价"; // 评价结果

    private int res1; // 优秀指标数
    private int res2; // 良好指标数
    private int res3; // 一般指标数
    private int res4; // 差指标数
    private int res5; // 极差指标数

}
