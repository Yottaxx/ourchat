package bit.group.ourchat.controller;

import bit.group.ourchat.entity.user;
import bit.group.ourchat.service.userService;
import bit.group.ourchat.service.friendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class friendController {
    @Autowired
    private userService userService;
    @Autowired
    private friendService friendService;
    @GetMapping(value = {"/homepage"})
    public String ChatHtml(HttpSession session, Model model){
        return "homepage";
    }
    //查找好友
    @RequestMapping(value={"/homepage_search"},method = RequestMethod.GET)
    private  @ResponseBody int search_user(String getName){

        System.out.println(getName);
        return 1;
    }
    //发送好友申请
    //接收好友申请
    //按分组查看好友
}
