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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.io.PrintWriter;
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

    public int page_size = 9;
    @RequestMapping("/homepage")
    public String homepage(){
        return "homepage";
    }


    @RequestMapping("/home")
    public String protfliofour(HttpServletRequest request,Model model) throws IOException {
        HttpSession session=request.getSession();
        user user1 = (user) session.getAttribute("user");
        model.addAttribute("user_name",user1.getName());
        String user_nickname = userService.findByName(user1.getName()).getNickname();
        String user_sign = userService.findByName(user1.getName()).getSign();
        String user_profile_photo = userService.findByName(user1.getName()).getProfile_photo();
        model.addAttribute("user_nickname",user_nickname);
        model.addAttribute("user_sign",user_sign);
        model.addAttribute("user_profile_photo",user_profile_photo);
        System.out.println("user_profile_photo"+user_profile_photo);
        showFriend_page(0,request,model);
        int fri_count = friendService.countAll(user1.getName());
        int page = fri_count/page_size+1;
        int first_num = page_size;
        if(fri_count%page_size==0)
            page = page-1;
        if(page_size>fri_count)
            first_num = fri_count;
        List<Map> result = new ArrayList<>();
        Map first = new HashMap();
        first.put("page_num",page);
        first.put("first_num",first_num);
        result.add(first);
        model.addAttribute("user_friend_num",fri_count);

        return "portfoliofour";
    }

    @RequestMapping("/portfolio-page")
    public String showFriend_page(@RequestParam(value = "page_num") int page_num,
                                  HttpServletRequest request, Model model) throws IOException {
        HttpSession session = request.getSession();
        user u1 = (user)session.getAttribute("user");
        Page<friend> fri_page = friendService.findPageFriendsByUserId(u1.getId(),page_num,page_size);
        List<friend> fri_list = fri_page.getContent();
        List<Map> result = new ArrayList<>();
        for(friend fri:fri_list){
            user fri_u = userService.findById(fri.getUserfriendId());
            Map f = new HashMap();
            f.put("user",fri_u);
            f.put("fri",fri);
        }
        model.addAttribute("user_Friends",result);
        return "portfolio";
    }

    //查看所有好友
    @PostMapping(path = "/Allfriends")
    @ResponseBody
    public List<Map> showAllFriends(String current_name,int page_size){
        current_name = current_name.substring(current_name.indexOf("(")+1, current_name.indexOf(")"));
        user u1 = userService.findByName(current_name);
        int u1_id = u1.getId();
        List<friend> fri_List= friendService.findAllByUserId(u1_id);
        int fri_count = friendService.countAll(current_name);
        int page = fri_count/page_size+1;
        int first_num = page_size;
        if(fri_count%page_size==0)
            page = page-1;
        if(page_size>fri_count)
            first_num = fri_count;
        List<Map> result = new ArrayList<>();
        Map first = new HashMap();
        first.put("page_num",page);
        first.put("first_num",first_num);
        result.add(first);
        for(friend fri:fri_List){
            Map data = new HashMap();
            //分组
            String fri_group = fri.getFriendGroup();
            //备注
            String fri_remark = fri.getRemark();
            //好友ID
            int fri_id = fri.getUserfriendId();
            //好友用户名
            user fri_user = userService.findById(fri_id);
            System.out.println(fri_user);
            data.put("user",fri_user);
            data.put("remark",fri_remark);
            data.put("group",fri_group);
            result.add(data);
        }
        return result;
    }

    //分页查看好友
    @PostMapping(path = "/Pagefriends")
    @ResponseBody
    public List<Map> showPageFriends(String current_name,int current_page,int page_size){
        current_name = current_name.substring(current_name.indexOf("(")+1, current_name.indexOf(")"));
        user u1 = userService.findByName(current_name);
        int u1_id = u1.getId();
        Page<friend> fri_Page = friendService.findPageFriendsByUserId(u1_id,current_page,page_size);
        List<friend> fri_List = fri_Page.getContent();
        List<Map> result = new ArrayList<>();
        for(friend fri:fri_List){
            Map data = new HashMap();
            //分组
            String fri_group = fri.getFriendGroup();
            //备注
            String fri_remark = fri.getRemark();
            //好友ID
            int fri_id = fri.getUserfriendId();
            //好友用户名
            user fri_user = userService.findById(fri_id);
            System.out.println(fri_user);
            data.put("user",fri_user);
            data.put("remark",fri_remark);
            data.put("group",fri_group);
            result.add(data);
        }
        return result;
    }

    //分页查看分组好友
    @PostMapping(path = "/PagefriendGroup")
    @ResponseBody
    public List<Map> showPageFriendGroup(String current_name,String group,int current_page,int page_size){
        current_name = current_name.substring(current_name.indexOf("(")+1, current_name.indexOf(")"));
        user u1 = userService.findByName(current_name);
        int u1_id = u1.getId();
        Page<friend> fri_Page = friendService.findPageFriendsByUserIdAndFriendGroup(u1_id,group,current_page,page_size);
        List<friend> fri_List = fri_Page.getContent();
        List<Map> result = new ArrayList<>();
        Map first = new HashMap();
        int total_num = friendService.countByGroup(current_name,group);
        int page = total_num/page_size+1;
        if(total_num%page_size==0)
            page = page-1;
        int first_num = page_size;
        if(page_size>total_num)
            first_num=total_num;
        first.put("page_num",page);
        first.put("first_num",first_num);
        result.add(first);
        for(friend fri:fri_List){
            Map data = new HashMap();
            //备注
            String fri_remark = fri.getRemark();
            //好友ID
            int fri_id = fri.getUserfriendId();
            //好友用户名
            user fri_user = userService.findById(fri_id);
            System.out.println(fri_user);
            data.put("user",fri_user);
            data.put("remark",fri_remark);
            result.add(data);
        }
        return result;

    }

    //查找添加好友
    @PostMapping(path = "/homepage_search")
    @ResponseBody
    public Map search_user(String current_name,String search_name) throws JSONException {
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
        current_name = current_name.substring(current_name.indexOf("(")+1, current_name.indexOf(")"));
        user u1 = userService.findByName(current_name);
        List<friend_request> fri_req_list = friendService.findAllRequestByUserId(u1.getId());
        List<Map> result = new ArrayList<>();
        for(friend_request fri_req: fri_req_list){
            Map data = new HashMap();
            int requestId = fri_req.getRequestId();
            String requestName = userService.findById(fri_req.getRequestId()).getName();
            String requestNickname = userService.findById(fri_req.getRequestId()).getNickname();
            String requestPhoto = userService.findById(fri_req.getRequestId()).getProfile_photo();
            System.out.println(requestId+","+requestName+","+requestNickname);
            data.put("Id",requestId);
            data.put("Name",requestName);
            data.put("Nickname",requestNickname);
            data.put("Photo",requestPhoto);
            data.put("request","request");
            result.add(data);
        }
        List<friend_request> fri_req_list1=friendService.findAllByRequestId(u1.getId());
        for(friend_request fri_req:fri_req_list1){
            Map data = new HashMap();
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
    public Map RequestAgree(String request_name,String current_name){
        current_name = current_name.substring(current_name.indexOf("(")+1, current_name.indexOf(")"));
        user request_user = userService.findByName(request_name);
        user current_user = userService.findByName(current_name);
        System.out.println(current_user+","+request_user);
        Map data = new HashMap();
        List<String> groups = friendService.findAllFriendsGroupsByUser(current_name);
        data.put("user",request_user);
        data.put("groups",groups);
        return data;
    }
    @PostMapping(value = {"/addFriend"})
    public String AddFriend(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
        session=request.getSession(false);
        user user1 = (user) session.getAttribute("user");
        String add_fri_name = request.getParameter("add_friend_name");
        String remark = request.getParameter("remark");
        String friend_group = request.getParameter("friend_group");
        if(remark=="" || remark.equals("null")){
            remark=null;
        }
        if( friend_group.equals("null") || friend_group==""){
            friend_group=null;
        }
        System.out.println(friend_group);
        int user_id = user1.getId();
        int fri_id = userService.findByName(add_fri_name).getId();
        if(friendService.Request_Agree(user_id,fri_id,friend_group,remark)){
            return "redirect:/home";
        }
        else{
            return "redirect:/home";
        }
    }
    //拒绝好友申请
    @PostMapping(path = "/Request_Reject")
    @ResponseBody
    public Map RequestReject(String request_name,String current_name){
        current_name = current_name.substring(current_name.indexOf("(")+1, current_name.indexOf(")"));
        user request_user = userService.findByName(request_name);
        user current_user = userService.findByName(current_name);
        System.out.println(current_user+","+request_user);
        Map data = new HashMap();
        data.put("current_user_name",current_name);
        data.put("request_user_name",request_name);
//        if(friendService.Request_Reject(current_user_id,request_user_id)){
//            return "拒绝成功";
//        }
//        return "拒绝失败";
        return data;
    }
    @PostMapping(value = {"/delFriendRequest"})
    public String DelFriendRequest(HttpServletRequest request,HttpSession session){
        String current_name= request.getParameter("current_user_name");
        String request_name = request.getParameter("request_user_name");
        int current_id = userService.findByName(current_name).getId();
        int request_id = userService.findByName(request_name).getId();
        if(friendService.Request_Reject(current_id,request_id)){
            return "redirect:/home";
        }
        return "redirect:/home";
    }

    //查找用户的所有好友分组
    @PostMapping(path = "/AllfriendsGroups")
    @ResponseBody
    public List<Map> showAllFriendsGroups(String current_name){
        current_name = current_name.substring(current_name.indexOf("(")+1, current_name.indexOf(")"));
        System.out.println("controller show all friends groups------current name:"+current_name);
        List<String> groups = friendService.findAllFriendsGroupsByUser(current_name);
        List<Map> result = new ArrayList<>();
        Map data = new HashMap();
        int count = friendService.countAll(current_name);
        data.put("group","所有好友");
        data.put("count",count);
        result.add(data);
        for(String group:groups){
         count = friendService.countByGroup(current_name,group);
         if(group==""||group.equals("null"))
             continue;
         Map g = new HashMap();
         g.put("group",group);
         g.put("count",count);
         result.add(g);
        }
        return result;
    }
    //按分组查看好友

    //好友信息查看修改
    @RequestMapping("/portfolio-details")
    public String portfolio_details(@RequestParam(value = "friend_name") String friend_name,
                                    HttpServletRequest request, Model model) throws IOException {
        HttpSession session=request.getSession();
        //user--当前用户
        user user1 = (user) session.getAttribute("user");
        model.addAttribute("user_name",user1.getName());
        String user_nickname = user1.getNickname();
        String user_sign = user1.getSign();
        model.addAttribute("user_nickname",user_nickname);
        model.addAttribute("user_sign",user_sign);
        user user_friend = userService.findByName(friend_name);
        //user_friend_name--要查看的好友的用户名
        model.addAttribute("user_friend_name",user_friend.getName());
        model.addAttribute("user_friend_nickname",user_friend.getNickname());
        model.addAttribute("user_friend_email",user_friend.getEmail());
        model.addAttribute("user_friend_address",user_friend.getAddress());
        model.addAttribute("user_friend_profile_photo",user_friend.getProfile_photo());
        String user_friend_sign = user_friend.getSign();
        if(user_friend_sign==null){
            user_friend_sign = "该用户未设置个性签名。";
        }
        model.addAttribute("user_friend_sign",user_friend_sign);
        friend fri = friendService.findByUserIdAndUserfriendId(user1.getId(),user_friend.getId());
        String fri_remark= fri.getRemark();
        if(fri_remark==null){
            fri_remark = "未设置该好友的备注";
        }
        model.addAttribute("user_friend_remark",fri_remark);
        String fri_group= fri.getFriendGroup();
        if(fri_group==null){
            fri_group = "未设置该好友的分组";
        }
        model.addAttribute("user_friend_group",fri_group);
        return "portfolio-details";
    }
    //查找好友分组he备注
    @PostMapping(path = "/getFriendRemarkAndGroup")
    @ResponseBody
    public Map GetFriendRemarkAndGroup(String current_name,String friend_name){
        System.out.println(current_name);
        System.out.println(friend_name);
        current_name = current_name.substring(current_name.indexOf("(")+1, current_name.indexOf(")"));
        int user_id = userService.findByName(current_name).getId();
        int fri_id = userService.findByName(friend_name).getId();
        friend fri = friendService.findByUserIdAndUserfriendId(user_id,fri_id);
        String remark = fri.getRemark();
        String group = fri.getFriendGroup();
        Map result = new HashMap();
        if(remark==null){
            remark = "未设置该好友的备注";
        }
        if(group==null){
            group = "未设置该好友的分组";
        }
        result.put("remark",remark);
        result.put("group",group);
        return result;
    }

    //设置好友备注
    @PostMapping(path = "/setFriendRemark")
    @ResponseBody
    public Map SetFriendRemark(String current_name,String friend_name,String remark){
        System.out.println(current_name);
        System.out.println(friend_name);
        current_name = current_name.substring(current_name.indexOf("(")+1, current_name.indexOf(")"));
        int user_id = userService.findByName(current_name).getId();
        int fri_id = userService.findByName(friend_name).getId();
        Map result = new HashMap();
        String new_remark;
        if(friendService.setRemark(user_id,fri_id,remark)){
            friend fri = friendService.findByUserIdAndUserfriendId(user_id,fri_id);
            new_remark = fri.getRemark();
        }
        else {
            new_remark = remark;
        }
        if(new_remark==null){
            new_remark = "未设置该好友的备注";
        }
        result.put("remark",new_remark);
        return result;
    }

    //设置好友分组
    @PostMapping(path = "/setFriendGroup")
    @ResponseBody
    public Map SetFriendGroup(String current_name,String friend_name,String group){
        System.out.println(current_name);
        System.out.println(friend_name);
        current_name = current_name.substring(current_name.indexOf("(")+1, current_name.indexOf(")"));
        int user_id = userService.findByName(current_name).getId();
        int fri_id = userService.findByName(friend_name).getId();
        Map result = new HashMap();
        String new_group;
        if(friendService.setFriendGroup(user_id,fri_id,group)){
            friend fri = friendService.findByUserIdAndUserfriendId(user_id,fri_id);
            new_group = fri.getFriendGroup();
        }
        else
            new_group = group;
        if(new_group==null){
            new_group = "未设置该好友的分组";
        }
        result.put("group",new_group);
        return result;
    }


}
