package com.study.evaluation.bean;

import lombok.Data;

/**
 * 学生类
 *
 * @author Hu.Wang 2020/03/17 15:25
 */
@Data
public class StudentBean {

    private int studentId;          // 学生id
    private int studentNo;          // 学号
    private String studentName;     // 姓名
    private String studentGender;   // 性别
    private String studentDormNo;   // 宿舍号
    private String studentCardNo;   // 身份证号
    private String studentPhone;    // 电话
    private String studentPassword; // 密码
    private int instituteId;        // 学院id
    private String instituteName;   // 学院
    private int classId;            // 班级id
    private int classNo;            // 班号
    private String classMajor;      // 专业
}
