package bit.group.ourchat.controller;

import bit.group.ourchat.entity.friend;
import bit.group.ourchat.entity.user;
import bit.group.ourchat.entity.friend_request;
import bit.group.ourchat.service.userService;
import bit.group.ourchat.service.friendService;
import org.hibernate.annotations.Parameter;
import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    //查看好友申请
    @PostMapping(path = "/homepage_requestMsg")
    @ResponseBody
    public List<Map> request_msg(String current_name) throws JSONException {
        System.out.println("request_msg_------------------------");
        System.out.println(current_name);
        current_name = current_name.substring(current_name.indexOf("(")+1, current_name.indexOf(")"));
        user u1 = userService.findByName(current_name);
        List<friend_request> fri_req_list = friendService.findAllRequestByUserId(u1.getId());
        List<Map> result = new ArrayList<>();
        for(friend_request fri_req: fri_req_list){
            Map data = new HashMap();
            if(fri_req.getStatus()==-1)
                continue;
            int requestId = fri_req.getRequestId();
            String requestName = userService.findById(fri_req.getRequestId()).getName();
            String requestNickname = userService.findById(fri_req.getRequestId()).getNickname();
            System.out.println(requestId+","+requestName+","+requestNickname);
            data.put("Id",requestId);
            data.put("Name",requestName);
            data.put("Nickname",requestNickname);
            data.put("request","request");
            result.add(data);
        }
        List<friend_request> fri_req_list1=friendService.findAllByRequestId(u1.getId());
        for(friend_request fri_req:fri_req_list1){
            Map data = new HashMap();
            if(fri_req.getStatus()==0)
                continue;
            int userId = fri_req.getUser().getId();
            String userName = fri_req.getUser().getName();
            String userNickname = fri_req.getUser().getNickname();
            System.out.println(userId+","+userName+","+userNickname);
            data.put("Id",userId);
            data.put("Name",userName);
            data.put("Nickname",userNickname);
            data.put("request","reject");
        }
        return result;
    }
    //接收好友申请
    @PostMapping(path = "/Request_Agree")
    @ResponseBody
    public void RequestAgree(String request_name,String current_name){
        current_name = current_name.substring(current_name.indexOf("(")+1, current_name.indexOf(")"));
        user request_user = userService.findByName(request_name);
        user current_user = userService.findByName(current_name);
        int request_user_id = request_user.getId();
        int current_user_id = current_user.getId();
        System.out.println(current_user+","+request_user);
        friendService.Request_Agree(current_user_id,request_user_id);
    }
    //拒绝好友申请
    @PostMapping(path = "/Request_Reject")
    @ResponseBody
    public void RequestReject(){

    }
    //忽略好友申请
    @PostMapping(path = "/Request_Ignore")
    @ResponseBody
    public void RequestIgnore(){

    }
    //查看所有好友
    @PostMapping(path = "/Allfriends")
    @ResponseBody
    public List<Map> search_friend(String current_name){
        current_name = current_name.substring(current_name.indexOf("(")+1, current_name.indexOf(")"));
        user u1 = userService.findByName(current_name);
        int u1_id = u1.getId();
        List<friend> fri_List= friendService.findAllByUserId(u1_id);
        List<Map> result = new ArrayList<>();
        for(friend fri:fri_List){
            Map data = new HashMap();
            //分组
            String fri_group = fri.getFriendGroup();
            System.out.println(fri_group);
            //备注
            String fri_remark = fri.getRemark();
            System.out.println(fri_remark);
            //好友ID
            int fri_id = fri.getUserfriendId();
            System.out.println(fri_id);
            //好友用户名
            user fri_user = userService.findById(fri_id);
            String fri_name = fri_user.getName();
            System.out.println(fri_name);

            data.put("name",fri_name);
            data.put("remark",fri_remark);
            data.put("group",fri_group);

            result.add(data);
        }
        return result;
    }
    //按分组查看好友
}
