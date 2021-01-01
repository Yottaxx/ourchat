package bit.group.ourchat.repository;
import bit.group.ourchat.entity.user;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface userRepository extends CrudRepository<user,Integer>{
    user findById(int id);
    user findByEmail(String email);
    user findByName(String name);
    int countBy();
}