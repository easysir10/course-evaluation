package com.study.evaluation.bean;

import lombok.Data;

/**
 * 教师类
 *
 * @author Hu.Wang 2020/03/17 15:24
 */
@Data
public class TeacherBean {

    private int teacherId;              // 教师id
    private int teacherNo;              // 工号
    private String teacherName;         // 姓名
    private String teacherGender;          // 性别
    private String teacherCardNo;       // 身份证号
    private String teacherPhone;        // 电话
    private String teacherPassword;     // 密码
    private int instituteId;            // 学院id
    private String instituteName;       // 学院
    private int titleId;                // 职称id
    private String titleName;           // 职称
    private int teacherPermission;      // 权限：0教师，1管理员
}
