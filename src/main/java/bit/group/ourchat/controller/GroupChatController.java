package bit.group.ourchat.controller;

import bit.group.ourchat.entity.GroupChat_new;
import bit.group.ourchat.entity.groupChat;
import bit.group.ourchat.repository.GroupChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/group_chat")
public class GroupChatController {
    @Autowired
    GroupChatRepository groupChatRepository;

    @GetMapping(path = "/all")
    public List<GroupChat_new> getAllGroupChat(){
        List data = groupChatRepository.findAll();
        System.out.println(data.size());
        return data;
    }

    //获取该用户所有群聊
    @GetMapping(path = "/all/group/{user_id}")
    public List<GroupChat_new> getAllGroupChatById(@PathVariable("user_id") Integer user_id){
        return groupChatRepository.findByMembersId(user_id);
    }

    //通过ID查找群聊
    @GetMapping(path = "/group/{group_id}")
    public List<GroupChat_new> getAllGroupChatByGroupId(@PathVariable("group_id") Integer group_id){
        return groupChatRepository.findByGroupId(group_id);
    }
}
