package bit.group.ourchat.controller;

import bit.group.ourchat.entity.singleChat;
import bit.group.ourchat.entity.user;
import bit.group.ourchat.repository.SingleChatRepository;
import bit.group.ourchat.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/single_chat")
public class SingleChatController {
    @Autowired
    SingleChatRepository singleChatRepository;

    @Autowired
    userRepository userrepository;


    //删除会话

    //返回头像
    @RequestMapping(value="/singlechat_user_details")
    @ResponseBody
    public String singlechat_user_details(@PathParam("user_name") String user_name){
        String photo = userrepository.findByName(user_name).getProfile_photo();
        System.out.println(photo+"!!!");
        return photo;
    }

    //返回singlechatid
    @PostMapping(value="/singlechat_getid")
    @ResponseBody
    public Integer singlechat_getid(@PathParam("user_name") String user_name,
                                   @PathParam("friend_name") String friend_name){
        Integer user_id = userrepository.findByName(user_name).getId();
        Integer friend_id = userrepository.findByName(friend_name).getId();
        List<singleChat> singlechat_1 = singleChatRepository.findByUser1andUser2(user_id,friend_id);
        List<singleChat> singlechat_2 = singleChatRepository.findByUser1andUser2(friend_id,user_id);
        if(singlechat_1.size()==0){
            return singlechat_2.get(0).getSingleChatID();
        }else{
            return singlechat_1.get(0).getSingleChatID();
        }
    }

    //返回所有的单聊会话<singleChat>
    @GetMapping(path = "/all")
    public List<singleChat> getAllSingleChat(){
        List<singleChat> data = singleChatRepository.findAll();
        System.out.println(data.size());
        return data;
    }
    
    //获取与某用户聊天的所有用户<user>
    @GetMapping(path = "/all/single/{user_name}")
    public List<Optional<user>> getAllSingleChatById(@PathVariable("user_name") String user_name){
        List<singleChat> singlechat1, singlechat2;
        List<Optional<user>> userlist = null;
        Integer user_id = userrepository.findByName(user_name).getId();
        //该用户是userid1时，在列表中存储user2
        singlechat1 = singleChatRepository.findByUserID1(user_id);
        for(int i=0;i<singlechat1.size();++i){
            userlist.add(userrepository.findById(singlechat1.get(i).getUserID2()));
        }
        //该用户是userid2时，在列表中存储user1
        singlechat2 = singleChatRepository.findByUserID2(user_id);
        for(int i=0;i<singlechat2.size();++i){
            userlist.add(userrepository.findById(singlechat2.get(i).getUserID1()));
        }
        return  userlist;
    }
}
