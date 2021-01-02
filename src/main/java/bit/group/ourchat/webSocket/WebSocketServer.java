package bit.group.ourchat.webSocket;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{from}")
@Component
public class WebSocketServer {
    private static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<String, WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //from
    private static String from = "";

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("from") String from) {
        this.session = session;
        webSocketMap.put(from, this);     //加入set中
        addOnlineCount();           //在线数加1
        logger.info("有新窗口开始监听:"+from+",当前在线人数为" + getOnlineCount());
        System.out.println("有新窗口开始监听:"+from+",当前在线人数为" + getOnlineCount());
        this.from=from;
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            logger.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketMap.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) throws JSONException {
        logger.info("收到来自窗口"+from+"的信息:"+message);
        JSONObject obj = new JSONObject();
        Date date = new Date();
        obj.put("cmd", "heartcheck");//业务类型
        obj.put("msgTxt", "服务端心跳响应 ");//消息内容
        obj.put("msgDate", format.format(date));//时间
        session.getAsyncRemote().sendText(obj.toString());
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误");
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     * */
    public static void sendMqMessage(String message) throws IOException {
        JSONObject jsonObject = new JSONObject();
        logger.info("推送消息到窗口"+from+"，推送内容:"+message);
        ChatMsg chatMsg = JSONObject.parseObject(message, ChatMsg.class);

        if(chatMsg != null && chatMsg.getTo() != null && webSocketMap.containsKey(chatMsg.getTo())){
            webSocketMap.get(chatMsg.getTo()).sendMessage(message);
        }else{
            logger.error("用户"+chatMsg.getTo()+",不在线！");
        }

    }

    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message,@PathParam("from") String from) throws IOException {
        JSONObject jsonObject = new JSONObject();
        logger.info("推送消息到窗口"+from+"，推送内容:"+message);
//        for (WebSocketServer item : webSocketMap) {
//            try {
//                String date = format.format(new Date());
//                String mes = message+ " (" + date + ")";
//                jsonObject.put("mes",mes);
//                jsonObject.put("sender",from);
//                //这里可以设定只推送给这个sid的，为null则全部推送
//                if(from==null) {
//                    item.sendMessage(jsonObject.toString());
//                }else if(item.from.equals(from)){
//                    item.sendMessage(jsonObject.toString());
//                }
//            } catch (IOException e) {
//                continue;
//            }
//        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}

