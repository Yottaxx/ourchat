package bit.group.ourchat.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.amqp.rabbit.listener.exception.FatalListenerStartupException;

import javax.persistence.*;
import java.util.List;

@Entity
@IdClass(user_friend.class)
@Table(name = "friend")
public class friend {
    @Id
    private Integer id;
    @Id
    private Integer userfriendId;
    private String remark; //备注
    private String friendGroup; //friend分组

    @JoinColumn(name="user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private user user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserfriendId() {
        return userfriendId;
    }

    public void setUserfriendId(Integer userfriendId) {
        this.userfriendId = userfriendId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFriendGroup() {
        return friendGroup;
    }

    public void setFriendGroup(String friendGroup) {
        this.friendGroup = friendGroup;
    }

    public bit.group.ourchat.entity.user getUser() {
        return user;
    }

    @JsonBackReference
    public void setUser(bit.group.ourchat.entity.user user) {
        this.user = user;
    }

    public friend(Integer id, Integer userfriendId, String remark, String group) {
        this.id = id;
        this.userfriendId = userfriendId;
        this.remark = remark;
        this.friendGroup = group;
    }

    public friend(Integer id, Integer friendId){
        this.id = id;
        this.userfriendId = friendId;
    }

    public friend()
    {

    }
}


