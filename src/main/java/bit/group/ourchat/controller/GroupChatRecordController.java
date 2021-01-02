package bit.group.ourchat.controller;

import bit.group.ourchat.entity.GroupChatRecord_new;
import bit.group.ourchat.repository.GroupChatRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/group_chat_record")
public class GroupChatRecordController {
    @Autowired
    GroupChatRecordRepository groupChatRecordRepository;

    @GetMapping(path = "/all")
    public List<GroupChatRecord_new> getAllGroupChatRecord(){
        return groupChatRecordRepository.findAll();
    }

    @GetMapping(path = "/allbyId/{group_id}")
    public List<GroupChatRecord_new> getAllGroupChatRecordById(@PathVariable("group_id") Integer group_id){
        return groupChatRecordRepository.findAllById(group_id);
    }
}
