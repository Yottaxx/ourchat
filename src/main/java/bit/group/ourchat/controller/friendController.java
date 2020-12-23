package bit.group.ourchat.controller;

import bit.group.ourchat.entity.user;
import bit.group.ourchat.service.userService;
import bit.group.ourchat.service.friendService;
import org.hibernate.annotations.Parameter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

@Controller
public class friendController {
    @Autowired
    private userService userService;
    @Autowired
    private friendService friendService;


    @RequestMapping("/homepage")
    public String homepage(){
        return "homepage";
    }

    //查找添加好友
    @PostMapping(path = "/homepage_search")
    @ResponseBody
    public Map search_user(String current_name,String search_name) throws JSONException {
        System.out.println(current_name);
        System.out.println(search_name);
        Map result = new HashMap();
        user user0, user1;
        boolean isSelf,isFriend;
        int currentId,lookupId;
        current_name = current_name.substring(current_name.indexOf("(")+1, current_name.indexOf(")"));
        user0 = userService.findByName(current_name);
        user1 = userService.findByName(search_name);
        currentId = user0.getId();
        lookupId = user1.getId();
        result.put("user",user1);
        isSelf = (currentId==lookupId);
        isFriend = friendService.isFriend(currentId,lookupId);
        result.put("isFriend",isSelf||isFriend);
        return result;
    }
    //发送好友申请
    @PostMapping(path = "/homepage_sendReq")
    @ResponseBody
    public String send_FriendReq(String current_name,String friend_name) throws JSONException {
        System.out.println("send_FriendReq!!!!!!");
        System.out.println(current_name);
        System.out.println(friend_name);
        user user0, user1;
        int id,requestId;
        current_name = current_name.substring(current_name.indexOf("(")+1, current_name.indexOf(")"));
        user0 = userService.findByName(current_name);
        user1 = userService.findByName(friend_name);
        id = user0.getId();
        requestId = user1.getId();
        String megstr = friendService.send_friRequest(id,requestId);
        return megstr;
    }

    //接收好友申请
    //按分组查看好友
}
