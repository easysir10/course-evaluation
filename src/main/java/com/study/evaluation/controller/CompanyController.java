package com.study.evaluation.controller;

import com.study.evaluation.service.EvaluationService;
import com.study.evaluation.service.IndexService;
import com.sun.deploy.net.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * description
 *
 * @author Hu.Wang 2020/05/09 14:59
 */
@Controller
public class CompanyController {


    @Autowired
    IndexService indexService;
    @Autowired
    EvaluationService evaluationService;

    /**
     * 跳转至同行评价界面
     */
    @RequestMapping("/companyEvaluation")
    public String companyEvaluation(Model model, HttpServletRequest request, HttpSession session) {
        model.addAttribute("message", session.getAttribute("messageT"));
        session.removeAttribute("messageT");
        model.addAttribute("message1", session.getAttribute("messageT1"));
        session.removeAttribute("messageT1");
        model.addAttribute("courseId", request.getParameter("courseId"));
        model.addAttribute("courseName", request.getParameter("courseName"));
        model.addAttribute("indexList", indexService.displayIndex());
        model.addAttribute("indexSize", evaluationService.getIndexCount(3));
        return "company_evaluation";
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
