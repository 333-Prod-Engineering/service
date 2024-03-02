package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ro.unibuc.hello.data.AuthorEntity;
import ro.unibuc.hello.data.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public AuthorEntity saveAuthor(String name, String nationality) {
        var authorEntity = AuthorEntity.builder().name(name).nationality(nationality).build();
        return authorRepository.save(authorEntity);
    }

}
