package bit.group.ourchat.entity;


import com.fasterxml.jackson.databind.JsonSerializer;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

// user 用户信息表
@Entity
@Table(name = "user")
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; //id
    private String name; //用户名
    private String email; //邮箱
    private String password; //密码
    private String role="user"; //权限 user or superUser
    private String nickname; //昵称
    private String sign; //个性签名
    private String profile_photo; //头像（地址）
    private String address= ""; //地址

    //好友表
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<friend> friendList;

    //好友请求表
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<friend_request> friend_requestList;

//    //单聊表
//    @OneToMany(mappedBy = "user1",cascade= CascadeType.ALL,fetch = FetchType.LAZY)
//    private List<singleChat> singleChatList1;
//    @OneToMany(mappedBy = "user2",cascade= CascadeType.ALL,fetch = FetchType.LAZY)
//    private List<singleChat> singleChatList2;
//
    //群聊用户表，用户所参加的群聊
//    @ManyToMany(mappedBy = "userList",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    private List<GroupMembers_new> groupMembers_newList;
//
//    //群聊表，user所管理的群聊表
//    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    private List<GroupChat_new> groupChat_newList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public List<friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<friend> friendList) {
        this.friendList = friendList;
    }

    public void addFriend(friend friend){
        this.friendList.add(friend);
    }

    public List<friend_request> getFriend_requestList() {return friend_requestList;}

    public  void setFriend_requestList(List<friend_request> friend_requestList){this.friend_requestList = friend_requestList;}

    public void addFriend_request(friend_request friend_request){
        this.friend_requestList.add(friend_request);
    }

//    public List<GroupChat_new> getGroupChat_newList() {
//        return groupChat_newList;
//    }
//
//    public void setGroupChat_newList(List<GroupChat_new> groupChat_newList) {
//        this.groupChat_newList = groupChat_newList;
//    }
//
//    public List<GroupMembers_new> getGroupMembers_newList() {
//        return groupMembers_newList;
//    }
//
//    public void setGroupMembers_newList(List<GroupMembers_new> groupMembers_newList) {
//        this.groupMembers_newList = groupMembers_newList;
//    }

    public user(Integer id, String name, String email, String password, String nickname, String sign, String profile_photo,
                List<friend> friendList,List<friend_request> friend_requestList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.sign =sign;
        this.profile_photo = profile_photo;
        this.friendList = friendList;
        this.friend_requestList = friend_requestList;
    }

    public user(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public user(){

    }
}
