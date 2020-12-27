package bit.group.ourchat.service;

import bit.group.ourchat.entity.friend;
import bit.group.ourchat.entity.friend_request;
import bit.group.ourchat.entity.user;
import bit.group.ourchat.repository.userRepository;
import bit.group.ourchat.repository.friendRepository;
import bit.group.ourchat.repository.friendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class friendService {
    @Autowired
    private userRepository userRepository;
    @Autowired
    private friendRepository friendRepository;
    @Autowired
    private friendRequestRepository friendRequestRepository;
    //发送好友申请
    public String send_friRequest(int userId,int requestId){
        friend_request friReq = friendRequestRepository.findByUserIdAndRequestId(userId,requestId);
        if(friReq!=null){
            //如果对方也在申请自己为好友
            if(friReq.getStatus()==0){
                //首先添加好友，然后删除
                if(addFriend(userId,requestId)){
                    friendRequestRepository.delete(friReq);
                    return "已成功添加好友";
                }
                else {
                    return "对方已经是您的好友";
                }
            }
        }
        friend_request friReq1 = friendRequestRepository.findByUserIdAndRequestId(requestId,userId);
        if(friReq1==null ||friReq1.getStatus()==-1){
            friend_request newFriReq= new friend_request();
            newFriReq.setUser(userRepository.findById(requestId));
            newFriReq.setRequestId(userId);
            friendRequestRepository.save(newFriReq);
        }
        return "已发送好友申请";
    }
    //是否已经是好友
    public boolean isFriend(int currentId,int lookupId){
        if(friendRepository.findByUserIdAndUserfriendId(currentId,lookupId)== null){
            return false;
        }else {
            return true;
        }
    }
    //查看好友
    public List<friend> findAllByUserId(int id){
        return friendRepository.findAllByUserId(id);
    }
    //查看好友申请
    public List<friend_request> findAllRequestByUserId(int id){
        return friendRequestRepository.findAllByUserId(id);
    }
    public List<friend_request> findAllByRequestId(int id){
        return friendRequestRepository.findAllByRequestId(id);
    }
    //接受好友申请
    public void Request_Agree(int userId,int requestId){
        addFriend(userId,requestId);
        friend_request fri_req = friendRequestRepository.findByUserIdAndRequestId(userId,requestId);
        friendRequestRepository.delete(fri_req);
    }
    //拒绝好友申请
    public void Request_Reject(int userId,int requestId){
        friend_request fri_req = friendRequestRepository.findByUserIdAndRequestId(userId,requestId);
        fri_req.setStatus(-1);
    }
    //忽略好友申请
    public void Request_Ignore(int userId,int requestId){
        friend_request fri_req = friendRequestRepository.findByUserIdAndRequestId(userId,requestId);
        friendRequestRepository.delete(fri_req);
    }
    //添加好友
    public boolean addFriend(int user_id,int fri_id){
        if(friendRepository.findByUserIdAndUserfriendId(user_id,fri_id)==null){
            friend fri = new friend();
            user u1 = new user();
            u1 = userRepository.findById(user_id);
            fri.setUser(u1);
            fri.setUserfriendId(fri_id);
            friendRepository.save(fri);

            return true;
        }
        else
            return false;

    }

    //设置分组 friendGroup
    //设置备注 remark
}
