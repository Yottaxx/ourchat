package bit.group.ourchat.controller;

import bit.group.ourchat.entity.GroupChatRecord_new;
import bit.group.ourchat.entity.GroupMembers_new;
import bit.group.ourchat.repository.GroupChatRecordRepository;
import bit.group.ourchat.repository.GroupMembersRepository;
import bit.group.ourchat.webSocket.ChatMsg;
import com.alibaba.fastjson.JSONObject;
//import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.net.http.WebSocket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class chatController {
    private static Logger logger =LoggerFactory.getLogger(chatController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate; //rabbitTemplate是springboot 提供的默认实现

    @Autowired
    GroupMembersRepository groupMembersRepository;

    @Autowired
    GroupChatRecordRepository groupChatRecordRepository;

    @RequestMapping(value = {"/Login","/login"})
    public String newUser(ChatMsg chatMsg,HttpServletRequest request)
    {
        return "login";
    }

    @RequestMapping(value="/send")
    @ResponseBody
    public void defaultMessage(@PathParam("message") String message,
                               @PathParam("from") String from,
                               @PathParam("group_id") Integer group_id) {
        List<GroupMembers_new> group = groupMembersRepository.findGroupById(group_id);
        Date date = new Date();
        for(int i = 0; i < group.size(); i++){
            System.out.println(group.get(i).getGroup_member_id());
//            if(group.get(i).getGroup_member_id().toString().equals(from)){
//                break;
//            }
            ChatMsg chatMsg = new ChatMsg();
            System.out.println("data:"+message+"  From:"+from+"  To:"+group.get(i).getGroup_member_id());
            chatMsg.setFrom(from);
            chatMsg.setTo(group.get(i).getGroup_member_id().toString());
            chatMsg.setContent(message);
            chatMsg.setDate(date);
            try {
                rabbitTemplate.convertAndSend("fanoutExchange", "", JSONObject.toJSONString(chatMsg));
            } catch (Exception e){
                System.out.println(e);
            }
        }
        GroupChatRecord_new groupChatRecord_new =new GroupChatRecord_new();
        groupChatRecord_new.setGroup_chat_id(group_id);
        groupChatRecord_new.setGroup_chatid(group_id);
        groupChatRecord_new.setRecord_content(message);
        groupChatRecord_new.setRecord_date(new Timestamp(date.getTime()));
        groupChatRecord_new.setRecord_path("Record Path!");
        groupChatRecord_new.setFrom(Integer.parseInt(from));
        groupChatRecordRepository.save(groupChatRecord_new);
    }

}
