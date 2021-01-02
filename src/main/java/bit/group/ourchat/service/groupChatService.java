package bit.group.ourchat.service;

import bit.group.ourchat.entity.*;
import bit.group.ourchat.repository.friendRepository;
import bit.group.ourchat.repository.groupChatRepository;
import bit.group.ourchat.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class groupChatService {
    @Autowired
    private bit.group.ourchat.repository.groupChatRepository groupChatRepository;
    @Autowired
    private userRepository userRepository;

    public GroupChat_new findByID(int id){
        return groupChatRepository.findById(id);
    }

//    public List<GroupChat_new> findByAdmin(user admin){
//        return groupChatRepository.findByAdmin(admin);
//    }
//
//    public List<GroupChat_new> findByAdminId(int adminId){
//        user admin = userRepository.findById(adminId);
//        return groupChatRepository.findByAdmin(admin);
//    }


    public void save(GroupChat_new groupChat){

        groupChatRepository.save(groupChat);
    }



//    public void addGroupMember(GroupChat_new groupChat, GroupMembers_new groupMember){
//        groupChat.addGroupMember(groupMember);
//    }
//    public void addMemberById(GroupChat_new groupChat, int user_id){
//        GroupMembers_new m = new GroupMembers_new();
//        m.setGroupChatId(groupChat.getId());
//        m.setUser_id(user_id);
//        groupChat.addGroupMember(m);
//        groupChatRepository.save(groupChat);
//    }
//
//    public void createGroupChar(String groupName, user admin){
//        GroupChat_new groupChat = new GroupChat_new();
//        groupChat.setGroupName(groupName);
//        groupChat.setAdmin(admin);
//        groupChat.setGroupMembersList(new ArrayList<GroupMembers_new>());
//        groupChat.setNotice("Welcome!");
//        groupChatRepository.save(groupChat);
//    }
}
