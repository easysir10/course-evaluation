package com.study.evaluation.bean;

import lombok.Data;

/**
 * description
 *
 * @author Hu.Wang 2020/04/07 20:57
 */
@Data
public class CourseBean {

    private int courseId;       // 课程id
    private String courseName;  // 课程名
    private double courseCredit;// 学分
    private int typeId;         // 课程类型id
    private String typeName;    // 课程类型
    private int midId;          // 中间表id
    private int teacherId;      // 任课老师id
    private String teacherName; // 任课老师姓名
    private int status;         // 课程是否已评价：0未评价，1已评价

}
