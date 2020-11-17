package bit.group.ourchat.repository;

import bit.group.ourchat.entity.exampleEntity;
import org.springframework.data.repository.CrudRepository;

public interface exampleRepository extends CrudRepository<exampleEntity,Integer> {
    exampleEntity findById(int Id);
    exampleEntity findByEmailEndsWith(String email);
}
