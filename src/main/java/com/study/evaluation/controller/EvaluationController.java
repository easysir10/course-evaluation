package com.study.evaluation.controller;

import com.alibaba.fastjson.JSON;
import com.study.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/04/29 14:47
 */
@Controller
public class EvaluationController {

    @Autowired
    EvaluationService evaluationService;

    @RequestMapping("/deleteEvaById")
    @ResponseBody
    public void deleteById(int id, HttpSession session){
        int result = evaluationService.deleteById(id);
        if (result==0){
            session.setAttribute("messageT", "删除失败！");
        }else{
            session.setAttribute("messageT", "删除成功！");
        }
    }

    @RequestMapping(value = "/insertEvaBatch")
    public String insetCourseBatch(@RequestParam("file") MultipartFile file, HttpSession session){
        int[] result = evaluationService.insertBatch(file);
        session.setAttribute("messageT", "导入完成！");
        session.setAttribute("messageT1", "（成功："+result[1]+"条，失败："+result[0]+"条）");
        return "redirect:/index";
    }


    @RequestMapping("/getOneIndexScore")
    @ResponseBody
    public String getOneIndexScore(int courseId){
        HashMap<String, List> map= evaluationService.getOneIndexScore(courseId);
        return JSON.toJSONString(map);
    }
}
