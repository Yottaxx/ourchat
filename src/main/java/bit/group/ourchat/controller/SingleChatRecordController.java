package bit.group.ourchat.controller;

import bit.group.ourchat.entity.singleChat;
import bit.group.ourchat.entity.singleChatRecord;
import bit.group.ourchat.repository.SingleChatRecordRepository;
import bit.group.ourchat.repository.SingleChatRepository;
import bit.group.ourchat.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/single_chat_record")
public class SingleChatRecordController {
    @Autowired
    SingleChatRecordRepository singleChatRecordRepository;

    @Autowired
    SingleChatRepository singleChatRepository;

    @Autowired
    userRepository userrepository;

    @RequestMapping(path = "/show")
    public String show(
//            @RequestParam("my_name") String my_name,
//                       @RequestParam("friend_name") String friend_name
    ){

        return "singlechatRecord";
    }

    //获得两个用户间的聊天记录<singleChatRecord>
    @GetMapping(path = "/{user1_name}&{user2_name}")
    public singleChatRecord get2UserChatRecord(@PathVariable("user1_name") String user1_name,
                                               @PathVariable("user2_name") String user2_name){
        Integer user1_id = userrepository.findByName(user1_name).getId();
        Integer user2_id = userrepository.findByName(user2_name).getId();
        Integer singlechatid;
        List<singleChat> singleChats_1 = singleChatRepository.findByUser1andUser2(user1_id, user2_id);
        List<singleChat> singleChats_2 = singleChatRepository.findByUser1andUser2(user2_id, user1_id);
        if(singleChats_1.size() == 0){
            singlechatid = singleChats_2.get(0).getSingleChatID();
        }else{
            singlechatid = singleChats_1.get(0).getSingleChatID();
        }
        //返回singlechatid对应的所有聊天记录
        //。。。
        return singleChatRecordRepository.findBySingleChatId(singlechatid);
    }

    //获得所有单聊会话的记录<singleChatRecord>
    @GetMapping(path = "/all")
    public List<singleChatRecord> getAllSingleChatRecord(){
        return singleChatRecordRepository.findAll();
    }

    //增加记录
    @PostMapping(value = "/insert")
    public boolean InsertRecord(@PathParam("message") String message, @PathParam("my_name") String my_name,
                                @PathParam("friend_name") String friend_name) {
        Integer singlechatID, my_id, friend_id;
        System.out.println(my_name);
        my_id = userrepository.findByName(my_name).getId();
        friend_id = userrepository.findByName(friend_name).getId();
        List<singleChat> singleChats_1 = singleChatRepository.findByUser1andUser2(my_id, friend_id);
        List<singleChat> singleChats_2 = singleChatRepository.findByUser1andUser2(friend_id, my_id);
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
        singleChatRecord.setMes_from(my_name);
        try {
            singleChatRecordRepository.save(singleChatRecord);
            return true;
        } catch (Exception e){
            return  false;
        }
    }

}