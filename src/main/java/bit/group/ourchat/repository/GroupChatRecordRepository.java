package bit.group.ourchat.repository;

import bit.group.ourchat.entity.GroupChatRecord_new;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupChatRecordRepository extends JpaRepository<GroupChatRecord_new,Integer> {
    @Query(value = "select r from GroupChatRecord_new r")
    public List<GroupChatRecord_new> findAll();

    @Query(value = "select r from GroupChatRecord_new r where r.group_chat_id = ?1 order by r.record_date desc ")
    public List<GroupChatRecord_new> findAllById(Integer GroupId);
}
