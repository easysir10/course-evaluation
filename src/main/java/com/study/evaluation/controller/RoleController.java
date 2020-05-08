package com.study.evaluation.controller;

import com.study.evaluation.bean.RoleBean;
import com.study.evaluation.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * description
 *
 * @author Hu.Wang 2020/05/02 9:50
 */
@Controller
public class RoleController {
    @Autowired
    RoleService roleService;

    @RequestMapping("/deleteRoleById")
    @ResponseBody
    public void deleteById(int id, HttpSession session){
        int result = roleService.deleteById(id);
        if (result==0){
            session.setAttribute("messageT", "删除失败！");
        }else{
            session.setAttribute("messageT", "删除成功！");
        }
    }


    @RequestMapping(value = "/updateRoleById")
    public String updateById(RoleBean roleBean, HttpSession session){
        int result = roleService.updateById(roleBean);
        if (result==0){
            session.setAttribute("messageT", "修改失败！");
        }else{
            session.setAttribute("messageT", "修改成功！");
        }
        return "redirect:/adminOther";
    }

    @RequestMapping(value = "/insertRole")
    public String insertRole(RoleBean roleBean, HttpSession session){
        int result =roleService.insert(roleBean);
        if (result==0){
            session.setAttribute("messageT", "添加失败！");
        }else{
            session.setAttribute("messageT", "添加成功！");
        }
        return "redirect:/adminOther";
    }
}
