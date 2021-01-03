package bit.group.ourchat.controller;

import bit.group.ourchat.entity.singleChat;
import bit.group.ourchat.entity.singleChatRecord;
import bit.group.ourchat.entity.user;
import bit.group.ourchat.repository.*;
import bit.group.ourchat.webSocket.ChatMsg;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

@Controller
public class S_chatController {
//    private static Logger logger = LoggerFactory.getLogger(chatController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate; //rabbitTemplate是springboot 提供的默认实现

    @Autowired
    SingleChatRepository singleChatRepository;

    @Autowired
    SingleChatRecordRepository singleChatRecordRepository;

    @Autowired
    userRepository userrepository;

    @RequestMapping(value = {"/single/Login","/single/login"})
    public String newUser(ChatMsg chatMsg, HttpServletRequest request)
    {
        return "second-hand";
    }

    //开启会话
    @RequestMapping(path = "/singlechat")
    public String beginSingleChat(@RequestParam("friend_name") String friend_name, HttpServletRequest request, Model model){
        singleChat singlechat = new singleChat();
        HttpSession session=request.getSession();
        user me = (user) session.getAttribute("user");
        model.addAttribute("single_user_name",me.getName());
        model.addAttribute("single_user_address",me.getAddress());
        model.addAttribute("single_user_id",me.getId());
        model.addAttribute("single_signature",me.getSign());
        model.addAttribute("single_nickname",me.getNickname());
        model.addAttribute("single_photo",me.getProfile_photo());
        model.addAttribute("single_friend_name",friend_name);
        model.addAttribute("single_friend_photo",userrepository.findByName(friend_name).getProfile_photo());
        Integer userid1 = userrepository.findByName(me.getName()).getId();
        Integer userid2 = userrepository.findByName(friend_name).getId();
        List<singleChat> singleChats_1 = singleChatRepository.findByUser1andUser2(userid1, userid2);
        List<singleChat> singleChats_2 = singleChatRepository.findByUser1andUser2(userid2, userid1);

        if(singleChats_1.size() == 0 && singleChats_2.size() == 0){
            //没有聊过天 处理  发起聊天  存储
            singleChat singleChat = new singleChat();
            singleChat.setUserID2(userid2);
            singleChat.setUserID1(userid1);
            singleChatRepository.save(singleChat);
            System.out.println("保存成功！");

        } else {
            //显示记录
            System.out.println("有过记录！");
        }

        model.addAttribute("");
        model.addAttribute("");

        return "singlechat";


    }


    //把消息和单聊双方用户名存进chatMsg中用rabbit发送，同时将单聊的会话保存在单聊记录中
    @RequestMapping(value="/singlechat_send")
    @ResponseBody
    public void defaultMessage(@PathParam("message") String message,
//                               @PathParam("from_id") Integer from_id,
                               @PathParam("from_name") String from_name,
                               @PathParam("to_name") String to_name) {

        Integer singlechatID, from_id, to_id;
        from_id = userrepository.findByName(from_name).getId();
        to_id = userrepository.findByName(to_name).getId();
        List<singleChat> singleChats_1 = singleChatRepository.findByUser1andUser2(from_id, to_id);
        List<singleChat> singleChats_2 = singleChatRepository.findByUser1andUser2(to_id, from_id);
        if(singleChats_1.size() == 0){
            singlechatID = singleChats_2.get(0).getSingleChatID();
        }else{
            singlechatID = singleChats_1.get(0).getSingleChatID();
        }

        singleChatRecord singleChatRecord = new singleChatRecord();
        singleChatRecord.setRecordPath("This is the PANTH!"); //换
        singleChatRecord.setSingleChatID(singlechatID);         //换
        singleChatRecord.setRecordDate(new Date());
        singleChatRecord.setRecordContent(message);
        singleChatRecord.setMes_from(from_name);
        try {
            singleChatRecordRepository.save(singleChatRecord);
            System.out.println("保存成功！");
        } catch (Exception e){
            System.out.println("保存失败！");
        }

        ChatMsg chatMsg = new ChatMsg();
        System.out.println("data:" + message + "  From:" + from_name + "  To:" + to_name);
        chatMsg.setFrom(from_name);
        chatMsg.setTo(to_name);
        chatMsg.setContent(message);
        chatMsg.setDate(new Date());
        try {
            rabbitTemplate.convertAndSend("fanoutExchange", "", JSONObject.toJSONString(chatMsg));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
