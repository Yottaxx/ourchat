package bit.group.ourchat.repository;

import bit.group.ourchat.entity.singleChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SingleChatRepository extends JpaRepository<singleChat, Integer> {
   public List<singleChat> findByUserID1(Integer id);
    public List<singleChat> findByUserID2(Integer id);
    public singleChat  findByUserID1AndUserID2(Integer user1_id, Integer user2_id);
    public singleChat  findByUserID2AndUserID1(Integer user2_id, Integer user1_id);

    @Query(value = "select s from singleChat s where s.userID1 = ?1 and s.userID2 = ?2")
    public List<singleChat> findByUser1andUser2(Integer user_id1, Integer user_id2);


}
