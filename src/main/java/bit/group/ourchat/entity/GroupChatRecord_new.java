package bit.group.ourchat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class GroupChatRecord_new {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "group_chatid")
    private Integer group_chatid;

    @Column(name = "record_content")
    private String record_content;

    @Column(name = "record_date")
    private Timestamp record_date;

    @Column(name = "record_path")
    private String record_path;

    @Column(name = "group_chat_id")
    private Integer group_chat_id;

    @Column(name = "message_from")
    private Integer from;

    public GroupChatRecord_new(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroup_chatid() {
        return group_chatid;
    }

    public void setGroup_chatid(Integer group_chatid) {
        this.group_chatid = group_chatid;
    }

    public String getRecord_content() {
        return record_content;
    }

    public void setRecord_content(String record_content) {
        this.record_content = record_content;
    }

    public Timestamp getRecord_date() {
        return record_date;
    }

    public void setRecord_date(Timestamp record_date) {
        this.record_date = record_date;
    }

    public String getRecord_path() {
        return record_path;
    }

    public void setRecord_path(String record_path) {
        this.record_path = record_path;
    }

    public Integer getGroup_chat_id() {
        return group_chat_id;
    }

    public void setGroup_chat_id(Integer group_chat_id) {
        this.group_chat_id = group_chat_id;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }
}
