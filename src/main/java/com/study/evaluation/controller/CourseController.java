package com.study.evaluation.controller;

import com.study.evaluation.bean.CourseBean;
import com.study.evaluation.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * description
 *
 * @author Hu.Wang 2020/04/07 21:11
 */
@Controller
public class CourseController {

    @Autowired
    CourseService courseService;

    @RequestMapping("/deleteCourseById")
    @ResponseBody
    public void deleteById(int id, HttpSession session){
        int result = courseService.deleteById(id);
        if (result==0){
            session.setAttribute("messageT", "删除失败！");
        }else{
            session.setAttribute("messageT", "删除成功！");
        }
    }

    @RequestMapping(value = "/updateCourseById")
    public String updateById(CourseBean courseBean, HttpSession session){
        int result = courseService.updateById(courseBean);
        if (result==0){
            session.setAttribute("messageT", "修改失败！");
        }else{
            session.setAttribute("messageT", "修改成功！");
        }
        return "redirect:/adminCourse";
    }

    @RequestMapping(value = "/insertCourseBatch")
    public String insetCourseBatch(@RequestParam("file")MultipartFile file, HttpSession session){
        int[] result = courseService.insertBatch(file);
        session.setAttribute("messageT", "导入完成！");
        session.setAttribute("messageT1", "（成功："+result[1]+"条，失败："+result[0]+"条）");
        return "redirect:/adminCourse";
    }
}
