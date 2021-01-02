package bit.group.ourchat.entity;

import javax.persistence.*;

@Entity
//@Table(name = "group_chat")
public class GroupChat_new {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "group_name")
    private String groupName; //群名

    @Column(name = "adminid")
    private Integer adminID; //管理员

    @Column(name = "notice")
    private String notice; //群公告

    @Column(name = "admin_id")
    private Integer admin_id;

    public GroupChat_new(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Integer getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Integer admin_id) {
        this.admin_id = admin_id;
    }
}
