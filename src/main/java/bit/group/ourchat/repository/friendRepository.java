package bit.group.ourchat.repository;

import bit.group.ourchat.entity.friend;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface friendRepository extends CrudRepository<friend,Integer> {
    List<friend> findAllByUserId(int id);
    List<friend> findAllByFriendGroup(String group);
    friend findByUserIdAndUserfriendId(int id,int friId);
    int countByFriendGroupAndAndUserId(String group,int id);
    int countAllByUserId(int id);
    Page<friend> findAllByUserId(int id, Pageable pageable);
    Page<friend> findAllByUserIdAndAndFriendGroup(int id,String group,Pageable pageable);
}
