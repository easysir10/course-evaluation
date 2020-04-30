package com.study.evaluation.controller;

import com.study.evaluation.bean.IndexBean;
import com.study.evaluation.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * description
 *
 * @author Hu.Wang 2020/04/18 11:37
 */
@Controller
public class IndexController {
    @Autowired
    IndexService indexService;

    @RequestMapping("/deleteIndexById")
    @ResponseBody
    public void deleteById(int id, HttpSession session){
        int result = indexService.deleteById(id);
        if (result==0){
            session.setAttribute("messageT", "删除失败！");
        }else{
            session.setAttribute("messageT", "删除成功！");
        }
    }

    @RequestMapping(value = "/updateIndexById")
    public String updateById(IndexBean indexBean, HttpSession session){
        int result = indexService.updateById(indexBean);
        if (result==0){
            session.setAttribute("messageT", "修改失败！");
        }else{
            session.setAttribute("messageT", "修改成功！");
        }
        return "redirect:/adminIndex";
    }

    @RequestMapping(value = "/insertIndex")
    public String insertIndex(IndexBean indexBean, HttpSession session){
        int result = indexService.insertIndex(indexBean);
        if (result==0){
            session.setAttribute("messageT", "添加失败！");
        }else{
            session.setAttribute("messageT", "添加成功！");
        }
        return "redirect:/adminIndex";
    }
}
