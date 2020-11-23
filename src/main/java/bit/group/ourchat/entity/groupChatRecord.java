package bit.group.ourchat.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class groupChatRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; //记录ID
    private Integer groupChatID; //对应需要保存的单聊ID
    private Date recordDate; //记录日期
    private String recordContent; //记录内容
    private String recordPath; //记录对应的本地path

    @ManyToOne()
    private groupChat groupChat;


    public Integer getGroupChatID() {
        return groupChatID;
    }

    public void setGroupChatID(Integer groupChatID) {
        this.groupChatID = groupChatID;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRecordContent() {
        return recordContent;
    }

    public void setRecordContent(String recordContent) {
        this.recordContent = recordContent;
    }

    public String getRecordPath() {
        return recordPath;
    }

    public void setRecordPath(String recordPath) {
        this.recordPath = recordPath;
    }


    public groupChatRecord(Integer groupChatID, Integer recordID, Date recordDate, String recordContent, String recordPath) {
        this.groupChatID = groupChatID;
        this.id = recordID;
        this.recordDate = recordDate;
        this.recordContent = recordContent;
        this.recordPath = recordPath;
    }

    public groupChatRecord()
    {

    }
}


