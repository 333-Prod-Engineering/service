package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.AuthorEntity;
import ro.unibuc.hello.data.AuthorRepository;
import ro.unibuc.hello.dto.Author;

@Component
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public AuthorService() {
    }

    public AuthorEntity saveAuthor(Author author) {
        AuthorEntity authorEntity = new AuthorEntity();

        authorEntity.setName(author.getName());
        authorEntity.setNationality(author.getNationality());
        return authorRepository.save(authorEntity);

    }

}
