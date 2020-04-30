package com.study.evaluation.controller;

import com.alibaba.fastjson.JSONObject;
import com.study.evaluation.bean.StudentBean;
import com.study.evaluation.bean.TeacherBean;
import com.study.evaluation.service.EvaluationService;
import com.study.evaluation.service.StudentService;
import com.study.evaluation.service.TeacherService;
import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 登录Controller
 *
 * @author Hu.Wang 2020/03/17 14:27
 */
@Controller
public class LoginController {

    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    EvaluationService evaluationService;

    /**
     * 跳转至登陆界面
     */
    @RequestMapping("/")
    public String login(HttpSession httpSession,Model model){
        if(httpSession.getAttribute("message")!=null){
            model.addAttribute("message",httpSession.getAttribute("message"));
            httpSession.setAttribute("message",null);
        }
        httpSession.setAttribute("user",null);
        return "login";
    }

    /**
     * 跳转至忘记密码界面
     */
    @RequestMapping("/forgetPassword")
    public String forgetPassword(){
        return "forget_password";
    }

    /**
     * 跳转至主界面
     */
    @RequestMapping("/index")
    public String index(HttpSession httpSession,Model model){
        int type = (int) httpSession.getAttribute("type");
        if (type==0){
            return "teacher_index";
        }else if (type==1){
            model.addAttribute("message",httpSession.getAttribute("messageT"));
            httpSession.removeAttribute("messageT");
            model.addAttribute("message1",httpSession.getAttribute("messageT1"));
            httpSession.removeAttribute("messageT1");
            model.addAttribute("evaList",evaluationService.selectAll());
            return "admin_evaluation";
        }else {
            return "student_index";
        }
    }

    /**
     * 登陆验证
     */
    @RequestMapping("/loginCheck")
    public String loginCheck(HttpSession httpSession,int username,String password,int type,Model model) {

        StudentBean studentBean = studentService.loginCheck(username,password);
        TeacherBean teacherBean = teacherService.loginCheck(username,password,type);

        if (type==2){
            if (studentBean!=null){
                httpSession.setAttribute("user", studentBean);
                httpSession.setAttribute("type",2);
                return "redirect:/index";
            }else {
                httpSession.setAttribute("message","账号或密码错误！");
                return "redirect:/";
            }
        }else{
            if (teacherBean!=null){
                httpSession.setAttribute("user", teacherBean);
                if (type==0){
                    httpSession.setAttribute("type", 0);
                    return "redirect:/index";
                }else {
                    httpSession.setAttribute("type", 1);
                    return "redirect:/index";
                }

            }else {
                httpSession.setAttribute("message","账号或密码错误！");
                return "redirect:/";
            }
        }

    }

    /**
     * 发送短信验证码
     */
    @RequestMapping("/sendSms")
    @ResponseBody
    public String sendSms(HttpSession httpSession,String phone){
        try {
            JSONObject json;
            //生成6位验证码
            String code = String.valueOf(new Random().nextInt(899999) + 100000);
            System.out.println(code);

            //发送短信
            ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com", "105026","2529e0ed-7952-4523-8dad-e855a706545a");
            Map<String, String> map = new HashMap<>();
            map.put("number",phone);
            map.put("message","#软件学院课程质量评价系统#您的验证码为:" + code + "，该验证码码有效期为5分钟，只能使用一次！");

//            String result = client.send(map);

//            json = JSONObject.parseObject(result);
//            if(json.getIntValue("code") != 0){
//                return "fail"; // 发送失败
//            }

            //将验证码和时间存到session中
            json = new JSONObject();
            json.put("verifyCode", code);
            json.put("createTime", System.currentTimeMillis());
            httpSession.setAttribute("code", json);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 重置密码
     */
    @RequestMapping("/alterPassword")
    public String alterPassword(HttpSession httpSession,
                                int no,
                                String phone,
                                String code,
                                String password1,
                                Model model){
        int type = (int) httpSession.getAttribute("type");


        JSONObject json = (JSONObject)httpSession.getAttribute("code");
        if(json==null||!json.getString("verifyCode").equals(code)){
            model.addAttribute("message","验证码错误！");
            return "forget_password";
        }
        if((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 5){
            model.addAttribute("message","验证码已过期！");
            return "forget_password";
        }

        int result;
        if (type==0||type==1){
            result = teacherService.updatePasswd(no,phone,password1);
        }else {
            result = studentService.updatePasswd(no,phone,password1);
        }
        if (result==0){
            model.addAttribute("message","账号或手机号错误！");
            return "forget_password";

        }else {
            model.addAttribute("message","重置密码成功！");
            return "login";
        }

    }
}
