package bit.group.ourchat.repository;

import bit.group.ourchat.entity.friend_request;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface friendRequestRepository extends CrudRepository<friend_request,Integer> {
    <List>friend_request findAllByUserId(int id);
    friend_request findByUserIdAndRequestId(int id,int requestId);
}
