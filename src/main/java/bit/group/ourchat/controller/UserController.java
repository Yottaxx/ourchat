package bit.group.ourchat.controller;

import bit.group.ourchat.entity.friend;
import bit.group.ourchat.entity.user;
import bit.group.ourchat.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private bit.group.ourchat.service.userService userService;

    //个人信息查看修改
    @RequestMapping("/user-details")
    public String portfolio_details(@RequestParam(value = "user_name") String user_name,
                                    HttpServletRequest request, Model model) throws IOException {
        HttpSession session=request.getSession();
        //user--当前用户
        user user1 = (user) session.getAttribute("user");
        model.addAttribute("user_name",user1.getName());
        String user_nickname = user1.getNickname();
        String user_sign = user1.getSign();
        String user_email = user1.getEmail();
        String user_address = user1.getAddress();
        model.addAttribute("user_nickname",user_nickname);
        model.addAttribute("user_sign",user_sign);
        model.addAttribute("user_email",user_email);
        model.addAttribute("user_address",user_address);
        String user_profile_photo = userService.findByName(user1.getName()).getProfile_photo();
        model.addAttribute("user_profile_photo",user_profile_photo);

        return "user-details";
    }

    //个人信息
    @PostMapping(path = "/getUserInfo")
    @ResponseBody
    public Map GetUserInfo(String current_name){
        System.out.println(current_name);
        int user_id = userService.findByName(current_name).getId();
        user u = userService.findByName(current_name);
        Map result = new HashMap();
        String nickname = u.getNickname();
        String sign = u.getSign();
        String email = u.getEmail();
        String address = u.getAddress();
        result.put("nickname",nickname);
        if(sign==null)
            sign ="";
        result.put("sign",sign);
        result.put("email",email);
        result.put("address",address);
        return result;
    }

    //修改个人信息
    @PostMapping(path = "/setUserInfo")
    @ResponseBody
    public Map SetUserInfo(String current_name,String new_nickname,String new_sign,String new_email,String new_address,String new_profile_photo){
        System.out.println(current_name);
        System.out.println(new_nickname);
        System.out.println(new_sign);
        System.out.println(new_email);
        System.out.println(new_address);
        System.out.println(new_profile_photo);

        int user_id = userService.findByName(current_name).getId();
        Map result = new HashMap();
        userService.Update(user_id,new_nickname,new_sign,new_email,new_address,new_profile_photo);
        user u = userService.findByName(current_name);
        String nickname = u.getNickname();
        String sign = u.getSign();
        String email = u.getEmail();
        String address = u.getAddress();
        String profile_photo = u.getProfile_photo();

        if(sign==null){
            sign = "";
        }

        result.put("nickname",nickname);
        result.put("sign",sign);
        result.put("email",email);
        result.put("address",address);
        result.put("profile_photo",profile_photo);


        return result;
    }
}
