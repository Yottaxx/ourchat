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
public class userService {
    @Autowired
    //private friendRepository friendRepository;
    private userRepository userRepository;
    private user user;

    public user findUserById(int Id){
        return userRepository.findById(Id);
    }
    public void saveUser(user user){
        userRepository.save(user);
    }

    public user findUserByName(String name){
        user = userRepository.findByName(name);
        return user;
    }

    public List<friend> findFriends(user user){
        return user.getFriendList();
    }

    public void addFriend(int userId, int friendId){
        user frienduser = new user();
        frienduser = userRepository.findById(friendId);
        user user = new user();
        user = userRepository.findById(userId);
        friend friend_pair = new friend(userId, friendId);
        friend_pair.setUser(user);
        user.addFriend(friend_pair);
        userRepository.save(user);
    }
}
