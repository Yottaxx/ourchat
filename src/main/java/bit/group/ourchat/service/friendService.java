package bit.group.ourchat.service;

import bit.group.ourchat.entity.friend;
import bit.group.ourchat.entity.friend_request;
import bit.group.ourchat.entity.user;
import bit.group.ourchat.repository.userRepository;
import bit.group.ourchat.repository.friendRepository;
import bit.group.ourchat.repository.friendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Service
public class friendService {
    @Autowired
    private userRepository userRepository;
    @Autowired
    private friendRepository friendRepository;
    @Autowired
    private friendRequestRepository friendRequestRepository;

    //发送好友申请
    public String send_friRequest(int userId, int requestId) {
        friend_request friReq = friendRequestRepository.findByUserIdAndRequestId(userId, requestId);
        if (friReq != null) {
            //如果对方也在申请自己为好友
            //首先添加好友，然后删除
            if (addFriend(userId, requestId, "默认分组", null)) {
                friendRequestRepository.delete(friReq);
                return "已成功添加好友";
            } else {
                return "对方已经是您的好友";
            }
        }
        friend_request friReq1 = friendRequestRepository.findByUserIdAndRequestId(requestId, userId);
        if (friReq1 == null) {
            friend_request newFriReq = new friend_request(requestId, userId);
            user u = userRepository.findById(requestId);
            newFriReq.setUser(u);
            u.addFriend_request(newFriReq);
            userRepository.save(u);
        }
        return "已发送好友申请";
    }

    //是否已经是好友
    public boolean isFriend(int currentId, int lookupId) {
        if (friendRepository.findByUserIdAndUserfriendId(currentId, lookupId) == null) {
            return false;
        } else {
            return true;
        }
    }

    //查看好友
    public List<friend> findAllByUserId(int id) {
        return friendRepository.findAllByUserId(id);
    }

    //分页查看好友
    public Page<friend> findPageFriendsByUserId(int id,int currentPage,int pageSize){
        Pageable pageable = PageRequest.of(currentPage,pageSize);
        return friendRepository.findAllByUserId(id, pageable);
    }
    public friend findByUserIdAndUserfriendId(int id, int friendId) {
        return friendRepository.findByUserIdAndUserfriendId(id, friendId);
    }
    //分页查看好友分组
    public Page<friend> findPageFriendsByUserIdAndFriendGroup(int id,String group,int currentPage,int pageSize){
        Pageable pageable = PageRequest.of(currentPage,pageSize);
        return friendRepository.findAllByUserIdAndAndFriendGroup(id,group,pageable);
    }

    //查看好友申请
    public List<friend_request> findAllRequestByUserId(int id) {
        return friendRequestRepository.findAllByUserId(id);
    }

    public List<friend_request> findAllByRequestId(int id) {
        return friendRequestRepository.findAllByRequestId(id);
    }

    //接受好友申请
    public boolean Request_Agree(int userId, int requestId, String group, String remark) {
        if (addFriend(userId, requestId, group, remark)) {
            friend_request fri_req = friendRequestRepository.findByUserIdAndRequestId(userId, requestId);
            friendRequestRepository.delete(fri_req);
            return true;
        }
        return false;
    }

    //拒绝好友申请
    public boolean Request_Reject(int userId, int requestId) {
        friend_request fri_req = friendRequestRepository.findByUserIdAndRequestId(userId, requestId);
        friendRequestRepository.delete(fri_req);
        return true;
    }

    //添加好友
    public boolean addFriend(int user_id, int fri_id, String group, String remark) {
        if (friendRepository.findByUserIdAndUserfriendId(user_id, fri_id) == null) {
            friend fri = new friend(user_id, fri_id);
            user u = userRepository.findById(user_id);
            fri.setUser(u);
            fri.setFriendGroup(group);
            fri.setRemark(remark);
            u.addFriend(fri);
            userRepository.save(u);

            friend fri1 = new friend(fri_id, user_id);
            user u1 = userRepository.findById(fri_id);
            fri1.setUser(u1);
            fri.setFriendGroup(group);
            fri.setRemark(remark);
            u1.addFriend(fri1);
            userRepository.save(u1);
            return true;
        } else
            return false;
    }

    //设置分组 friendGroup
    public boolean setFriendGroup (int user_id, int fri_id, String group){
        if (friendRepository.findByUserIdAndUserfriendId(user_id, fri_id) != null) {
            friend fri = findByUserIdAndUserfriendId(user_id,fri_id);
            user u = userRepository.findById(user_id);
            fri.setFriendGroup(group);
            u.addFriend(fri);
            userRepository.save(u);
            return true;
        }
        return false;
    }
    //设置备注 remark
    public boolean setRemark (int user_id, int fri_id, String remark){
        if (friendRepository.findByUserIdAndUserfriendId(user_id, fri_id) != null) {
            friend fri = findByUserIdAndUserfriendId(user_id,fri_id);
            user u = userRepository.findById(user_id);
            fri.setRemark(remark);
            u.addFriend(fri);
            userRepository.save(u);
            return true;
        }
        return false;
    }

    //查看好友的所有分组
    public List<String> findAllFriendsGroupsByUser(String user_name) {
        user u = userRepository.findByName(user_name);
        int u_id = u.getId();
        List<friend> fri_list = friendRepository.findAllByUserId(u_id);
        List<String> u_group = new ArrayList<String>();
        for (friend fri : fri_list) {
            String group = fri.getFriendGroup();
            if(group !=null){
                u_group.add(group);
            }
        }
        System.out.println(u_group);
        //去重
        List<String> result = new ArrayList<String>(new TreeSet<String>(u_group));
        System.out.println(result);
        return result;
    }
    //分组的好友数
    public int countByGroup(String user_name,String group){
        user u = userRepository.findByName(user_name);
        int u_id = u.getId();
        int count = friendRepository.countByFriendGroupAndAndUserId(group,u_id);
        return count;
    }
    //所有好友数
    public int countAll(String user_name){
        user u = userRepository.findByName(user_name);
        int u_id = u.getId();
        int count = friendRepository.countAllByUserId(u_id);
        return count;
    }
}