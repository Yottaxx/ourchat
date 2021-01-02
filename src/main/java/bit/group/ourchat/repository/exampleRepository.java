package bit.group.ourchat.repository;

import bit.group.ourchat.entity.exampleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface exampleRepository extends CrudRepository<exampleEntity,Integer> {
    exampleEntity findById(int Id);
    exampleEntity findByEmailEndsWith(String email);
}
