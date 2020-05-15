package com.study.evaluation.controller;

import com.study.evaluation.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * description
 *
 * @author Hu.Wang 2020/04/01 11:12
 */
@Controller
public class AdminController {

    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    SelectService selectService;
    @Autowired
    CourseService courseService;
    @Autowired
    IndexService indexService;
    @Autowired
    ClassService classService;
    @Autowired
    EvaluationService evaluationService;
    @Autowired
    RoleService roleService;

    /**
     * 跳转至课程评价数据统计界面
     */
    @RequestMapping("/adminDataDetails")
    public String adminDataDetails(Model model, HttpSession session, HttpServletRequest request) {
        model.addAttribute("message", session.getAttribute("messageT"));
        session.removeAttribute("messageT");
        model.addAttribute("message1", session.getAttribute("messageT1"));
        session.removeAttribute("messageT1");
        model.addAttribute("course", courseService.selectAll(Integer.parseInt(request.getParameter("id"))).get(0));
        return "admin_data_details";
    }

    /**
     * 跳转至课程界面
     */
    @RequestMapping("/adminCourse")
    public String adminData(Model model, HttpSession session) {
        model.addAttribute("message", session.getAttribute("messageT"));
        session.removeAttribute("messageT");
        model.addAttribute("message1", session.getAttribute("messageT1"));
        session.removeAttribute("messageT1");
        model.addAttribute("typeList", selectService.selectCourseType());
        model.addAttribute("courseList", courseService.selectAll(-1));
        return "admin_course";
    }

    /**
     * 跳转至教师管理界面
     */
    @RequestMapping("/adminTeacher")
    public String adminTeacher(Model model, HttpSession session) {
        model.addAttribute("message", session.getAttribute("messageT"));
        session.removeAttribute("messageT");
        model.addAttribute("message1", session.getAttribute("messageT1"));
        session.removeAttribute("messageT1");
        model.addAttribute("instituteList", selectService.selectInstitute());
        model.addAttribute("titleList", selectService.selectTitle());
        model.addAttribute("teacherList", teacherService.selectAll());
        return "admin_teacher";
    }

    /**
     * 跳转至学生管理界面
     */
    @RequestMapping("/adminStudent")
    public String adminStudent(Model model, HttpSession session) {
        model.addAttribute("message", session.getAttribute("messageT"));
        session.removeAttribute("messageT");
        model.addAttribute("message1", session.getAttribute("messageT1"));
        session.removeAttribute("messageT1");
        model.addAttribute("instituteList", selectService.selectInstitute());
        model.addAttribute("classList", selectService.selectClass());
        model.addAttribute("studentList", studentService.selectAll());
        return "admin_student";
    }

    /**
     * 跳转至课程管理界面
     */
    @RequestMapping("/adminEvaluation")
    public String adminCourse(Model model, HttpSession session) {
        model.addAttribute("message", session.getAttribute("messageT"));
        session.removeAttribute("messageT");
        model.addAttribute("message1", session.getAttribute("messageT1"));
        session.removeAttribute("messageT1");
        model.addAttribute("evaList", evaluationService.selectAll());
        return "admin_evaluation";
    }

    /**
     * 跳转至指标管理界面
     */
    @RequestMapping("/adminIndex")
    public String adminIndex(Model model, HttpSession session) {
        model.addAttribute("message", session.getAttribute("messageT"));
        session.removeAttribute("messageT");
        model.addAttribute("indexList", indexService.selectAll());
        return "admin_index";
    }

    /**
     * 跳转至班级管理界面
     */
    @RequestMapping("/adminClass")
    public String adminClass(Model model, HttpSession session) {
        model.addAttribute("message", session.getAttribute("messageT"));
        session.removeAttribute("messageT");
        model.addAttribute("message1", session.getAttribute("messageT1"));
        session.removeAttribute("messageT1");
        model.addAttribute("classList", classService.selectAll());
        model.addAttribute("instituteList", selectService.selectInstitute());
        model.addAttribute("teacherList", teacherService.selectAll());
        return "admin_class";
    }


    /**
     * 跳转至其他设置界面
     */
    @RequestMapping("/adminOther")
    public String adminOther(Model model, HttpSession session) {
        model.addAttribute("message", session.getAttribute("messageT"));
        session.removeAttribute("messageT");
        model.addAttribute("message1", session.getAttribute("messageT1"));
        session.removeAttribute("messageT1");
        model.addAttribute("roleList", roleService.selectAll());
        return "admin_other";
    }




}
