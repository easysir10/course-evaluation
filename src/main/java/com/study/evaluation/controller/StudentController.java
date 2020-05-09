package com.study.evaluation.controller;

import com.study.evaluation.bean.StudentBean;
import com.study.evaluation.service.CourseService;
import com.study.evaluation.service.EvaluationService;
import com.study.evaluation.service.IndexService;
import com.study.evaluation.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * description
 *
 * @author Hu.Wang 2020/03/18 11:58
 */
@Controller
public class StudentController {
    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @Autowired
    IndexService indexService;

    @Autowired
    EvaluationService evaluationService;

    @RequestMapping("/deleteStudentById")
    @ResponseBody
    public void deleteById(int id, HttpSession session) {
        int result = studentService.deleteById(id);
        if (result == 0) {
            session.setAttribute("messageT", "删除失败！");
        } else {
            session.setAttribute("messageT", "删除成功！");
        }
    }

    @RequestMapping(value = "/updateStudentById")
    public String updateById(StudentBean studentBean, HttpSession session) {
        int result = studentService.updateById(studentBean);
        if (result == 0) {
            session.setAttribute("messageT", "修改失败！");
        } else {
            session.setAttribute("messageT", "修改成功！");
        }
        return "redirect:/adminStudent";
    }

    @RequestMapping(value = "/insertStudentBatch")
    public String insetCourseBatch(@RequestParam("file") MultipartFile file, HttpSession session) {
        int[] result = studentService.insertBatch(file);
        session.setAttribute("messageT", "导入完成！");
        session.setAttribute("messageT1", "（成功：" + result[1] + "条，失败：" + result[0] + "条）");
        return "redirect:/adminStudent";
    }

    /**
     * 跳转至我的课程界面
     */
    @RequestMapping("/studentCourse")
    public String studentCourse(Model model, HttpSession session) {
        model.addAttribute("message", session.getAttribute("messageT"));
        session.removeAttribute("messageT");
        model.addAttribute("message1", session.getAttribute("messageT1"));
        session.removeAttribute("messageT1");
        StudentBean bean = (StudentBean) session.getAttribute("user");
        model.addAttribute("courseList", studentService.selectStuCourse(bean.getStudentId()));
        return "student_course";
    }

    /**
     * 跳转至课程评价界面
     */
    @RequestMapping("/studentEvaluation")
    public String studentEvaluation(Model model, HttpServletRequest request) {
        model.addAttribute("courseId", request.getParameter("courseId"));
        model.addAttribute("courseName", request.getParameter("courseName"));
        model.addAttribute("midId", request.getParameter("midId"));
        model.addAttribute("indexList", indexService.displayIndex());
        model.addAttribute("indexSize",evaluationService.getIndexCount(3));
        return "student_evaluation";
    }

    /**
     * 提交学生评价
     */
    @RequestMapping("/submitStuEvaluation")
    public String submitStuEvaluation(Integer[] indexScore, int courseId, int midId, int personNo,
                    String evaluationAdvice, HttpSession session) {
        boolean result = evaluationService.submitStuEvaluation(indexScore, courseId, personNo, evaluationAdvice,
                        session, midId);
        if (result) {
            session.setAttribute("messageT", "评价成功！");
        } else {
            session.setAttribute("messageT", "评价失败！");
        }

        return "redirect:/studentCourse";
    }
}
