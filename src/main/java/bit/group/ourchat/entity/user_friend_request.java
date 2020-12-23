package bit.group.ourchat.entity;

import java.io.Serializable;

public class user_friend_request implements Serializable {
    private Integer id;
    private Integer requestId;

    // default constructor

    public user_friend_request(Integer id, Integer requestId) {
        this.id = id;
        this.requestId = requestId;
    }
    public user_friend_request(){}

    // equals() and hashCode()
}
