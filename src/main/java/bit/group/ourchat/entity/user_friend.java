package bit.group.ourchat.entity;

import java.io.Serializable;

public class user_friend implements Serializable {
    private Integer id;
    private Integer userfriendId;

    // default constructor

    public user_friend(Integer id, Integer userfriendId) {
        this.id = id;
        this.userfriendId = userfriendId;
    }
    public user_friend(){

    }
    // equals() and hashCode()
}
