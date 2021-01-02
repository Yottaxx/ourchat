package bit.group.ourchat.controller;


import bit.group.ourchat.entity.user;
import bit.group.ourchat.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
public class LoginController {

    @Autowired
    private userService userService;
    @RequestMapping("/blog")
    public String blog(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException {
        return "blog";
    }
    @RequestMapping("/blogtwo")
    public String blogtwo(HttpServletRequest request,Model model) throws IOException {
        HttpSession session=request.getSession();
        user user1 = (user) session.getAttribute("user");
        model.addAttribute("user_name",user1.getName());
        String user_nickname = userService.findByName(user1.getName()).getNickname();
        String user_sign = userService.findByName(user1.getName()).getSign();
        String user_profile_photo = userService.findByName(user1.getName()).getProfile_photo();
        model.addAttribute("user_nickname",user_nickname);
        model.addAttribute("user_sign",user_sign);
        model.addAttribute("user_profile_phto",user_profile_photo);
        return "blogtwo";
    }
    @RequestMapping("/goods-details")
    public String goods_details(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException {
        return "goods-details";
    }
    @RequestMapping("/index")
    public String index(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException {
        return "index";
    }


    @RequestMapping("/pricing")
    public String pricing(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException {
        return "pricing";
    }

    @RequestMapping("/service")
    public String service(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException {
        return "service";
    }
    @RequestMapping("/shortcodes")
    public String shortcodes(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException {
        return "shortcodes";
    }







    @PostMapping(value = {"/Login","/login"})
    public String newUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
