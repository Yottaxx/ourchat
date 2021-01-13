package bit.group.ourchat.repository;

import bit.group.ourchat.entity.GroupChat_new;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupChatRepository extends JpaRepository<GroupChat_new, Integer> {
    @Query(value = "select c from GroupChat_new c")
    public List<GroupChat_new> findAll();

    //获取该用户的所有群
    @Query(value = "select c from GroupChat_new c where c.id in (select m.group_chat_id from GroupMembers_new m where m.user_id=?1)")
    public List<GroupChat_new> findByMembersId(Integer id);

    @Query(value = "select c from GroupChat_new c where c.id=?1")
    public List<GroupChat_new> findByGroupId(Integer id);

    @Query(value = "select c from GroupChat_new c where c.groupName like %?1%")
    public List<GroupChat_new> findByGroupName(String group_name);

//    //用户加群，添加记录
//    @SQLInsert(sql = "insert into group_members_new (group_member_id, group_chat_id, user_id) values (?1, ?2, ?1)")
//    public void AddUserIntoGroup(Integer User_id, Integer Group_id);
}
