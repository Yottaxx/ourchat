package bit.group.ourchat.entity;


import javax.persistence.*;
import java.util.List;

@Entity
public class singleChat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer userID1;
    private Integer userID2;

    @ManyToOne()
    private user user1;
    @ManyToOne()
    private user user2;

    @OneToMany(mappedBy = "singleChat",cascade= CascadeType.ALL,fetch = FetchType.LAZY)
    private List<chatRecord> chatRecordList;//地址


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserID1() {
        return userID1;
    }

    public void setUserID1(Integer userID1) {
        this.userID1 = userID1;
    }

    public Integer getUserID2() {
        return userID2;
    }

    public void setUserID2(Integer userID2) {
        this.userID2 = userID2;
    }

    public List<chatRecord> getChatRecordList() {
        return chatRecordList;
    }

    public void setChatRecordList(List<chatRecord> chatRecordList) {
        this.chatRecordList = chatRecordList;
    }

    public user getUser1() {
        return user1;
    }

    public void setUser1(user user1) {
        this.user1 = user1;
    }

    public user getUser2() {
        return user2;
    }

    public void setUser2(user user2) {
        this.user2 = user2;
    }

    public singleChat(Integer singleChatID, Integer userID1, Integer userID2) {
        this.id = singleChatID;
        this.userID1 = userID1;
        this.userID2 = userID2;
    }

    public singleChat()
    {

    }
}


