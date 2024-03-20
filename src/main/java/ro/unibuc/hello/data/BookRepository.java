package ro.unibuc.hello.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<BookEntity, String> {

    List<BookEntity> findByAuthor(AuthorEntity authorEntity);
    // List<BookEntity> findByAuthorAuthorId(String authorId);
}