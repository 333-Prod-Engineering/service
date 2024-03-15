package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.unibuc.hello.data.AuthorEntity;
import ro.unibuc.hello.data.AuthorRepository;
import ro.unibuc.hello.dto.AuthorCreationRequestDto;
import ro.unibuc.hello.dto.UpdateAuthorRequestDto;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public AuthorEntity saveAuthor(AuthorCreationRequestDto authorCreationRequestDto) {
        log.debug("Creating a new author '{}'", authorCreationRequestDto.getName());
        var authorEntity = mapToAuthorEntity(authorCreationRequestDto);
        return authorRepository.save(authorEntity);
    }

    public AuthorEntity updateAuthor(String id, UpdateAuthorRequestDto updateAuthorRequestDto) {
        log.debug("Updating the author with id '{}', setting death date '{}'", id,
                updateAuthorRequestDto.getDeathDate());
        var author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        author.setDeathDate(updateAuthorRequestDto.getDeathDate());
        return authorRepository.save(author);
    }

    private AuthorEntity mapToAuthorEntity(AuthorCreationRequestDto dto) {
        log.debug("Map authorCreationRequestDto to authorEntity");
        return AuthorEntity.builder()
                .name(dto.getName())
                .nationality(dto.getNationality())
                .birthDate(dto.getBirthDate())
                .deathDate(dto.getDeathDate())
                .build();
    }
}
