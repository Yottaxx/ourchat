package bit.group.ourchat.repository;

import bit.group.ourchat.entity.friend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface friendRepository extends CrudRepository<friend,Integer> {
    List<friend> findAllByUserId(int id);
    friend findByUserfriendId(int id);
    List<friend> findAllByFriendGroup(String group);
    friend findByUserIdAndUserfriendId(int userId,int userfriendId);

    }
