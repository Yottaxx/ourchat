package bit.group.ourchat.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class singleChat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer singleChatID;
    private Integer userID1;
    private Integer userID2;
//    private Integer singleChatID;

//    @ManyToOne()
//    private user user1;
//    @ManyToOne()
//    private user user2;
//
//    @OneToMany(mappedBy = "singleChat",cascade= CascadeType.ALL,fetch = FetchType.LAZY)
//    private List<singleChatRecord> singleChatRecordList;//地址


//    public Integer getSingleChatID() {
//        return singleChatID;
//    }
//
//    public void setSingleChatID(Integer singleChatID) {
//        this.singleChatID = singleChatID;
//    }

    public Integer getSingleChatID() {
        return singleChatID;
    }

    public void setSingleChatID(Integer id) {
        this.singleChatID = id;
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

//    public List<singleChatRecord> getSingleChatRecordList() {
//        return singleChatRecordList;
//    }

//    public void setSingleChatRecordList(List<singleChatRecord> singleChatRecordList) {
//        this.singleChatRecordList = singleChatRecordList;
//    }
//
//    public user getUser1() {
//        return user1;
//    }
//
//    public void setUser1(user user1) {
//        this.user1 = user1;
//    }
//
//    public user getUser2() {
//        return user2;
//    }
//
//    public void setUser2(user user2) {
//        this.user2 = user2;
//    }

    public singleChat(Integer singleChatID, Integer userID1, Integer userID2) {
        this.singleChatID = singleChatID;
        this.userID1 = userID1;
        this.userID2 = userID2;
    }

    public singleChat()
    {}
}


