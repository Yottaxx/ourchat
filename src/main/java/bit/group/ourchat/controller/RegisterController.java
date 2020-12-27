package bit.group.ourchat.controller;

import bit.group.ourchat.service.userService;
import bit.group.ourchat.entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class RegisterController {

    @Autowired
    private userService userService;


    @PostMapping(value = "/register")
    public String newUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("post");
        user user1 = new user();
        user1.setName((String) request.getParameter("name"));
        user1.setNickname((String) request.getParameter("nickname"));
        user1.setPassword((String) request.getParameter("password"));
        user1.setAddress((String) request.getParameter("address"));
        user1.setEmail((String) request.getParameter("email"));

        if(userService.Save(user1)) {
            HttpSession session=request.getSession();
            user user2=userService.findByName(user1.getName());
            session.setAttribute("user",user1);
            return "redirect:/home";

        }else
            return "aboutus";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String newUser2(user user1)
    {
        System.out.println("get");
       return "signup";
    }

    @RequestMapping(value = "/portfoliofour",method = RequestMethod.GET)
    public String portfoliofour(user user1)
    {
        System.out.println("get");
        return "portfoliofour";
    }
}
