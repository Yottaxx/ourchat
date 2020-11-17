package bit.group.ourchat.service;

import bit.group.ourchat.entity.exampleEntity;
import bit.group.ourchat.repository.exampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class exampleService {
    @Autowired
    private exampleRepository exampleRepository;

    public exampleEntity findById(int Id)
    {
        return exampleRepository.findById(Id);
    }
}
