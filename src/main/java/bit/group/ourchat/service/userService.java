package bit.group.ourchat.service;

import bit.group.ourchat.entity.user;
import bit.group.ourchat.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encode = encoder.encode(user1.getPassword());
            user1.setPassword(encode);
            System.out.println(user1.getId()+ "\n" +user1.getName()+ "\n" +user1.getNickname()+ "\n"+user1.getEmail()+
                    "\n" +user1.getAddress()+ "\n" +user1.getProfile_photo());
            userRepository.save(user1);
            return true;
        }
        else
            return false;
    }
    public void Update(int id,String new_nickname,String new_sign,String new_email,String new_address,String new_profile_photo)
    {
        user u1 = userRepository.findById(id);
        u1.setProfile_photo(new_profile_photo);
        u1.setAddress(new_address);
        u1.setEmail(new_email);
        u1.setSign(new_sign);
        u1.setNickname(new_nickname);
        userRepository.save(u1);
    }
    public boolean Login(user user)
    {
        if(userRepository.findByName(user.getName()) == null)
            return false;
        user u1 = userRepository.findByName(user.getName());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(user.getPassword(),u1.getPassword());
//        return u1.getPassword().equals(user.getPassword());
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
