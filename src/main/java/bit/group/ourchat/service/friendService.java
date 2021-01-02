package bit.group.ourchat.service;

import bit.group.ourchat.entity.exampleEntity;
import bit.group.ourchat.entity.friend;
import bit.group.ourchat.entity.user;
import bit.group.ourchat.repository.friendRepository;
import bit.group.ourchat.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class friendService {
    @Autowired
    private friendRepository friendRepository;
    private friend friend;
//    private userRepository userRepository;

    public friend findByUserAndFriendId(int userId, int friendId){
        return friendRepository.findByUserIdAndUserfriendId(userId, friendId);
    }

    public List<friend> findFriendsByFriendId(int friendId){
        return friendRepository.findByUserfriendId(friendId);
    }

    public List<friend> findFriendsByUserId(int userId){
        return friendRepository.findById(userId);
    }


    public void saveFriend(friend friend){
        friendRepository.save(friend);
    }

}
