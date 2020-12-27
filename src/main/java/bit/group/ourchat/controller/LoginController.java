package bit.group.ourchat.controller;


import bit.group.ourchat.entity.user;
import bit.group.ourchat.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
public class LoginController {

    @Autowired
    private userService userService;

    @RequestMapping("/home")
    public String protfliofour(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException {
        HttpSession session=request.getSession();
        user user1 = (user) session.getAttribute("user");
        System.out.println("--------hello-----");
        System.out.println(user1.getName());
        System.out.println(userService.findByName(user1.getName()).getNickname());
        model.addAttribute("user_name",user1.getName());
        String user_nickname = userService.findByName(user1.getName()).getNickname();
        String user_sign = userService.findByName(user1.getName()).getSign();
        model.addAttribute("user_nickname",user_nickname);
        model.addAttribute("user_sign",user_sign);
        PrintWriter out = response.getWriter();
        out.println("<script>alert('登录成功！')</script>");

        return "portfoliofour";
    }

    @PostMapping(value = {"/Login","/login"})
    public String newUser(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        HttpSession session=request.getSession();
        user user1 = new user();
        user1.setName((String) request.getParameter("name"));
        user1.setPassword((String) request.getParameter("password"));
        if(userService.Login(user1)) {
            user user2=userService.findByName(user1.getName());
            session.setAttribute("user",user2);
            System.out.println(session.getAttribute("user"));
            return "redirect:/home";
        }else{
            PrintWriter out = response.getWriter();
            out.println("<script>alert('用户名或密码输入错误，登录失败！')</script>");
            return "login";
        }
    }

    @GetMapping(value = {"/Login","/login"})
    public String GetLogin(user user1)
    {
       return "login";
    }

    @GetMapping(value = {"/Logout","/logout"})
    public String logout(HttpSession session){
//        session.removeAttribute("user");
        return "redirect:/login";
    }

}
