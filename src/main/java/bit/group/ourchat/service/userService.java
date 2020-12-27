package bit.group.ourchat.service;

import bit.group.ourchat.entity.user;
import bit.group.ourchat.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userService {
    @Autowired
    private userRepository userRepository;
    public boolean Save(user user1)
    {
        user u1 = userRepository.findByName(user1.getName());
        if (u1==null)
        {
            user1.setId(userRepository.countBy()+1);
            System.out.println(user1.getId()+ "\n" +user1.getName()+ "\n" +user1.getNickname()+ "\n"+user1.getEmail()+ "\n" +user1.getAddress());
            userRepository.save(user1);
            return true;
        }
        else
            return false;
    }
    public boolean Login(user user)
    {
        if(userRepository.findByName(user.getName()) == null)
            return false;
        user u1 = userRepository.findByName(user.getName());
        return u1.getPassword().equals(user.getPassword());
    }

    public user findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }
    public user findById(int id)
    {
        return userRepository.findById(id);
    }
    public user findByName(String name){return userRepository.findByName(name);}
}
