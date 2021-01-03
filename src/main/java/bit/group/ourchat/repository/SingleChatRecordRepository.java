package bit.group.ourchat.repository;

import bit.group.ourchat.entity.singleChatRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SingleChatRecordRepository extends JpaRepository<singleChatRecord, Integer> {
    @Query(value = "select s from singleChatRecord s where s.singleChatID = ?1")
    public singleChatRecord findBySingleChatId(Integer id);
}
