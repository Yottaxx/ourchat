package bit.group.ourchat.entity;

import java.io.Serializable;

public class group_user implements Serializable {
    private Integer id;

    private Integer groupMemberId;

    // default constructor

    public group_user(Integer groupId, Integer userId) {
        this.id = groupId;
        this.groupMemberId = userId;
    }

    // equals() and hashCode()
}