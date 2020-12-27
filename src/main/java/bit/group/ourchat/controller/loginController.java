package bit.group.ourchat.controller;


import bit.group.ourchat.entity.user;
import bit.group.ourchat.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;


@Controller
public class loginController {

    @Autowired
    private userService userService;


    @RequestMapping("/blogthree")
    public String blogthree(){
        return "blogthree";
    }
//    去到登录页面
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping(value = "/login")
    public String Login_User(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException {
        user user1 = new user();
        user1.setName((String) request.getParameter("name"));
        user1.setPassword((String)request.getParameter("password"));
        if(userService.Login(user1)){
            PrintWriter out = response.getWriter();
            out.println("<script>alert('登录成功！')</script>");
            model.addAttribute("name",user1.getName());
            model.addAttribute("nickname",userService.findByName(user1.getName()).getNickname());
            model.addAttribute("sign",userService.findByName(user1.getName()).getSign());
            return "homepage";
        }else
        {
            PrintWriter out = response.getWriter();
            out.println("<script>alert('登录失败！')</script>");
            return "login";
        }
    }

}




