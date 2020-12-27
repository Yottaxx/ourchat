package bit.group.ourchat.entity;


import javax.persistence.*;

import bit.group.ourchat.entity.user;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert
@DynamicUpdate
@IdClass(user_friend_request.class)
public class friend_request {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "reqSeq")
    @SequenceGenerator(name = "reqSeq", initialValue = 1, allocationSize = 1, sequenceName = "REQ_SEQUENCE")
    private Integer id;
    @Id
    // 请求添加user为好友的id
    private Integer requestId;

    //@JoinColumn(name="user_id", nullable = false)
    @ManyToOne()
    private user user;

    @ColumnDefault("0")
    private Integer status=0;

    public Integer getId() {return id;}

    public void setId(Integer requested_id){this.id = id;}

    public Integer getRequestId() {
        return requestId;
    }
    public void  setRequestId(Integer requestId){this.requestId = requestId;}

    public bit.group.ourchat.entity.user getUser() {return user;}

    @JsonBackReference
    public void setUser(bit.group.ourchat.entity.user user){this.user = user;}

    public Integer getStatus(){return status;}

    public void setStatus(Integer status){this.status = status;}

    public friend_request(Integer id,Integer requestId,Integer status){
        this.id = id;
        this.requestId = requestId;
        this.status = status;
    }
    public friend_request(){

    }

}
