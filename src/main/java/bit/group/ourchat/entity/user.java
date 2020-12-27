package bit.group.ourchat.entity;


import com.fasterxml.jackson.databind.JsonSerializer;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

// user 用户信息表
@Entity
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

    //单聊表
    @OneToMany(mappedBy = "user1",cascade= CascadeType.ALL,fetch = FetchType.LAZY)
    private List<singleChat> singleChatList1;
    @OneToMany(mappedBy = "user2",cascade= CascadeType.ALL,fetch = FetchType.LAZY)
    private List<singleChat> singleChatList2;

    //群聊用户表，用户所参加的群聊
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<groupMembers> groupMembersList;

    //群聊表，user所管理的群聊表
    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<groupChat> groupChatList;


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

    public List<singleChat> getSingleChatList1() {
        return singleChatList1;
    }

    public void setSingleChatList1(List<singleChat> singleChatList1) {
        this.singleChatList1 = singleChatList1;
    }

    public List<singleChat> getSingleChatList2() {
        return singleChatList2;
    }

    public void setSingleChatList2(List<singleChat> singleChatList2) {
        this.singleChatList2 = singleChatList2;
    }

    public List<groupChat> getGroupChatList() {
        return groupChatList;
    }

    public void setGroupChatList(List<groupChat> groupChatList) {
        this.groupChatList = groupChatList;
    }

    public List<groupMembers> getGroupMembersList() {
        return groupMembersList;
    }

    public void setGroupMembersList(List<groupMembers> groupMembersList) {
        this.groupMembersList = groupMembersList;
    }

    public user(Integer id, String name, String email, String password, String nickname, String sign, String profile_photo,
                List<singleChat> singleChatList1, List<singleChat> singleChatList2, List<friend> friendList,
                List<groupMembers> groupMembersList, List<groupChat> groupChatList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.sign =sign;
        this.profile_photo = profile_photo;
        this.singleChatList1 = singleChatList1;
        this.singleChatList2 = singleChatList2;
        this.friendList = friendList;
        this.groupMembersList = groupMembersList;
        this.groupChatList = groupChatList;
    }

    public user()
    {

    }
}
