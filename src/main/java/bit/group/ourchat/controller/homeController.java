package bit.group.ourchat.controller;

import bit.group.ourchat.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class homeController {
    @Autowired
    private userService userService;

    //    去到主页面
    @RequestMapping("/homepage")
    public String login(){
        return "homepage";
    }

}
