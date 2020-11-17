package bit.group.ourchat.controller;


import bit.group.ourchat.entity.exampleEntity;
import bit.group.ourchat.service.exampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class exampleController {
    @Autowired
    private exampleService exampleService;

    @RequestMapping(value = "/newmoment/week")
    public String NewMomentsWeek(Model model, HttpSession session)
    {
        Integer num=-7;
        exampleEntity exampleEntity= (exampleEntity) session.getAttribute("customer");
        session.setAttribute("time",num);
        return "redirect:/chat";
    }

    @PostMapping(value = "/chat")
    public String Char_Main(Model model,HttpSession httpSession) {

        int a= (int) httpSession.getAttribute("time");
        return "/blogthree";
    }

    @GetMapping(value = "/Moment/return/{id}")
    public String MomentOperation(@PathVariable("id") int id)
    {
        exampleEntity exampleEntity=exampleService.findById(id);
        return "index";
    }
}
