package bit.group.ourchat.controller;


import bit.group.ourchat.webSocket.ChatMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class exampleController {

    @PostMapping(value = "/chat")
    public String Char_Main(Model model,HttpSession httpSession) {

        int a= (int) httpSession.getAttribute("time");
        return "/blogthree";
    }



    @RequestMapping(value = "/blogdetails")
    public String newUser()
    {
        return "blogdetails";
    }
}
