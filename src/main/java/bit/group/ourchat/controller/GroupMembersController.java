package bit.group.ourchat.controller;

import bit.group.ourchat.entity.GroupMembers_new;
import bit.group.ourchat.repository.GroupMembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/group_members")
public class GroupMembersController {
    @Autowired
    GroupMembersRepository groupMembersRepository;

    @GetMapping(path = "/all")
    public List<GroupMembers_new> getAllGroupMembers(){
        return groupMembersRepository.findAll();
    }

    @PostMapping(path = "/insert")
    public GroupMembers_new Insert(@RequestParam("user_id") Integer user_id,
                                   @RequestParam("group_id") Integer group_id){
        GroupMembers_new groupMembers_new = new GroupMembers_new();
        groupMembers_new.setGroup_member_id(user_id);
        groupMembers_new.setGroup_chat_id(group_id);
        groupMembers_new.setUser_id(user_id);
        return groupMembersRepository.save(groupMembers_new);
    }

//    @PutMapping("/luckymoneys/{id}")
//    public Luckymoney update(@PathVariable("id")Integer id,
//                             @RequestParam("consumer")String consumer) {
//        Optional<Luckymoney> optional = repository.findById(id);
//        if (optional.isPresent()){
//            Luckymoney luckymoney = optional.get();
//            luckymoney.setId(id);
//            luckymoney.setConsumer(consumer);
//            return repository.save(luckymoney);
//        }
//        return null;
//    }

//    @DeleteMapping("/demo4")
//    public String test4(@RequestParam("id") Integer id,@RequestParam("name") String name) {
//        System.out.println("test4......");
//        return Integer.valueOf(id) + ":" + name;
//    }
}
