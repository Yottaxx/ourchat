package bit.group.ourchat.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@IdClass(group_user.class)
public class groupMembers {
    @Id
    private Integer id;
    @Id
    private Integer groupMemberId;

    //一条数据的形式？只有一个群聊ID和一个用户ID？
    @ManyToOne()
    private groupChat groupChat;
    @ManyToOne()
    private user user;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(Integer groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    public bit.group.ourchat.entity.user getUser() {
        return user;
    }

    public void setUser(bit.group.ourchat.entity.user user) {
        this.user = user;
    }

    public bit.group.ourchat.entity.groupChat getGroupChat() {
        return groupChat;
    }

    public void setGroupChat(bit.group.ourchat.entity.groupChat groupChat) {
        this.groupChat = groupChat;
    }

    public groupMembers(Integer groupChatID, Integer userID) {
        this.id = groupChatID;
        this.groupMemberId = userID;
    }

    public groupMembers()
    {

    }
}


