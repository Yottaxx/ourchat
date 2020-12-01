package bit.group.ourchat.controller;

import bit.group.ourchat.entity.user;
import bit.group.ourchat.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

@Controller
public class registerController {

    @Autowired
    private userService userService;

    //    去到注册页面
    @RequestMapping("/register")
    public String register(){
        return "register";
    }
    @PostMapping(value = "/register")
    public String Register_newUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        user user1 = new user();
        user1.setName((String) request.getParameter("name"));
        user1.setNickname((String) request.getParameter("nickname"));
        user1.setEmail((String) request.getParameter("email"));
        user1.setPassword((String) request.getParameter("password"));
        user1.setAddress((String) request.getParameter("address"));

        if (userService.Save(user1)) {
            PrintWriter out = response.getWriter();
            out.println("<script>alert('注册成功！')</script>");
            return  "login";
        } else
        {
            PrintWriter out = response.getWriter();
            out.println("<script>alert('注册失败！用户名已存在。')</script>");
            return  "register";
        }
    }
}
