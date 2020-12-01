package bit.group.ourchat.controller;

import bit.group.ourchat.entity.user;
import bit.group.ourchat.service.userService;
import bit.group.ourchat.service.friendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class friendController {
    @Autowired
    private userService userService;
    @Autowired
    private friendService friendService;
    //查找好友
    @PostMapping("/search_friend")
    private void lookup_friend(HttpServletRequest request, HttpServletResponse response, Model model){
        user user0 = new user();
        user0 = userService.findByName(request.getParameter("name"));
        user user1 = new user();
        user1 = userService.findByName(request.getParameter("lookup_name"));
        int currentUserId = user0.getId();
        int lookupUserId = user1.getId();
        boolean isSelf = (currentUserId==lookupUserId);
        boolean isFriend = friendService.isFriend(currentUserId,lookupUserId);
        model.addAttribute("lookup_name",user1.getName());
        model.addAttribute("lookup_nickname",user1.getNickname());
        model.addAttribute("lookup_profile_photo",user1.getProfile_photo());
        model.addAttribute("lookup_sign",user1.getSign());
        model.addAttribute("lookup_role",user1.getRole());
        model.addAttribute("lookup_address",user1.getAddress());
        model.addAttribute("lookup_isFriend",isSelf||isFriend);
    }
    //发送好友申请
    //接收好友申请
    //按分组查看好友
}
