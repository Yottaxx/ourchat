package bit.group.ourchat.controller;

import bit.group.ourchat.webSocket.ChatMsg;
import com.alibaba.fastjson.JSONObject;
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
import java.net.http.WebSocket;
import java.util.Date;

@Controller
public class chatController {
    private static Logger logger =LoggerFactory.getLogger(chatController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate; //rabbitTemplate是springboot 提供的默认实现

    @RequestMapping(value = {"/Login","/login"})
    public String newUser(ChatMsg chatMsg,HttpServletRequest request)
    {
        return "login";
    }



    @RequestMapping(value="/send")
    @ResponseBody
    public void defaultMessage(String message, String from, String to) {
        ChatMsg chatMsg = new ChatMsg();
        System.out.println("data:"+message+from+to);
        chatMsg.setFrom(from);
        chatMsg.setTo(to);
        chatMsg.setContent(message);
        chatMsg.setDate(new Date());
        rabbitTemplate.convertAndSend("fanoutExchange", "", JSONObject.toJSONString(chatMsg));
    }

}
