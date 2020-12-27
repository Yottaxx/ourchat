package bit.group.ourchat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GroupMembers_new {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "group_member_id")
    private Integer group_member_id;

    @Column(name = "group_chat_id")
    private Integer group_chat_id;

    @Column(name = "user_id")
    private Integer user_id;

    public GroupMembers_new(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroup_member_id() {
        return group_member_id;
    }

    public void setGroup_member_id(Integer group_member_id) {
        this.group_member_id = group_member_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getGroup_chat_id() {
        return group_chat_id;
    }

    public void setGroup_chat_id(Integer group_chat_id) {
        this.group_chat_id = group_chat_id;
    }
}
