package bit.group.ourchat.entity;


import javax.persistence.*;

import bit.group.ourchat.entity.user;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;

@Entity
@IdClass(user_friend_request.class)
@Table(name = "friend_request")
public class friend_request {
    @Id
    private Integer id;
    @Id
    // 请求添加user为好友的id
    private Integer requestId;


    private Date date;
    @JoinColumn(name="user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private user user;

    public Integer getId() {return id;}

    public void setId(Integer id){this.id = id;}

    public Integer getRequestId() {
        return requestId;
    }
    public void  setRequestId(Integer requestId){this.requestId = requestId;}

    public Date getDate(){return date;}

    public void setDate(){this.date =date;}

    public bit.group.ourchat.entity.user getUser() {return user;}

    @JsonBackReference
    public void setUser(bit.group.ourchat.entity.user user){this.user = user;}


    public friend_request(Integer id,Integer requestId,Date date ){
        this.id = id;
        this.requestId = requestId;
        this.date = date;
    }
    public friend_request(){

    }

}
