package com.study.evaluation.controller;

import com.study.evaluation.service.EvaluationService;
import com.sun.deploy.net.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

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

    /**
     * 提交同行评价
     */
    @RequestMapping("/submitCompanyEvaluation")
    public String submitCompanyEvaluation(Integer[] indexScore, int courseId,String courseName,
                                      String evaluationAdvice, HttpSession session) throws UnsupportedEncodingException {
        System.out.println(courseName);
        boolean result = evaluationService.submitCompanyEvaluation(indexScore,courseId,evaluationAdvice,session);
        if (result) {
            session.setAttribute("messageT", "评价成功！");
        } else {
            session.setAttribute("messageT", "评价失败！");
        }

        return "redirect:/companyEvaluation?courseId="+courseId+"&courseName="+ URLEncoder.encode(courseName,"UTF-8");
    }
}
