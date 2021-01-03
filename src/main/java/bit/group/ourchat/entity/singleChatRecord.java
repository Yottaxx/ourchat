package bit.group.ourchat.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class singleChatRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; //记录ID
    private Integer singleChatID; //对应需要保存的单聊ID
    private String mes_from;
    private Date recordDate; //记录日期
    private String recordContent; //记录内容
    private String recordPath; //记录对应的本地path

//    @ManyToOne()
//    private singleChat singleChat;

    public Integer getSingleChatID() {
        return singleChatID;
    }

    public void setSingleChatID(Integer singleChatID) {
        this.singleChatID = singleChatID;
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

    public String getMes_from() {
        return mes_from;
    }

    public void setMes_from(String mes_from) {
        this.mes_from = mes_from;
    }

    public singleChatRecord(Integer singleChatID, Integer recordID, Date recordDate, String recordContent, String recordPath) {
        this.singleChatID = singleChatID;
        this.id = recordID;
        this.recordDate = recordDate;
        this.recordContent = recordContent;
        this.recordPath = recordPath;
    }

    public singleChatRecord()
    {

    }
}


