package bit.group.ourchat.service;

import bit.group.ourchat.entity.friend;
import bit.group.ourchat.repository.userRepository;
import bit.group.ourchat.repository.friendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class friendService {
    @Autowired
    private userRepository userRepository;
    @Autowired
    private friendRepository friendRepository;
    //发送好友申请
    //是否已经是好友
    public boolean isFriend(int currentId,int lookupId){
        if(friendRepository.findByUserIdAndUserfriendId(currentId,lookupId)== null){
            return false;
        }else {
            return true;
        }
    }
    //接受好友申请
    //拒绝好友申请
    //添加好友

    //设置分组 friendGroup
    //设置备注 remark
}
