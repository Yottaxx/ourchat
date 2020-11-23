package bit.group.ourchat.entity;


import javax.persistence.*;
import java.util.List;

@Entity
public class groupChat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; //群聊ID
    private String groupName; //群名
    private Integer adminID; //管理员
    private String notice; //群公告

    @OneToMany(mappedBy = "groupChat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<groupChatRecord> groupChatRecordList;
    @OneToMany(mappedBy = "groupChat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<groupMembers> groupMembersList;
    @ManyToOne()
    private user admin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public List<groupMembers> getGroupMembersList() {
        return groupMembersList;
    }

    public void setGroupMembersList(List<groupMembers> groupMembersList) {
        this.groupMembersList = groupMembersList;
    }

    public user getAdmin() {
        return admin;
    }

    public void setAdmin(user admin) {
        this.admin = admin;
    }

    public groupChat(Integer groupChatId, Integer adminID, String groupName) {
        this.id = groupChatId;
        this.adminID = adminID;
        this.groupName = groupName;
    }

    public List<groupChatRecord> getGroupChatRecordList() {
        return groupChatRecordList;
    }

    public void setGroupChatRecordList(List<groupChatRecord> groupChatRecordList) {
        this.groupChatRecordList = groupChatRecordList;
    }

    public groupChat()
    {

    }
}


