package ro.unibuc.hello.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;

import ro.unibuc.hello.data.AuthorRepository;
import ro.unibuc.hello.dto.AuthorCreationRequestDto;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
@Tag("IT")
class AuthorServiceTestIT {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorService authorService;

    @Test
    void test_getAuthor_returnsAuthor() {
        // Given
        var authorCreationRequestDto = AuthorCreationRequestDto.builder().name("Mircea Eliadee").nationality("romanian")
                .birthDate(LocalDate.of(1837, 03, 1)).deathDate(LocalDate.of(1986, 4, 22))
                .build();

        // When
        var savedAuthor = authorService.saveAuthor(authorCreationRequestDto);

        // Then
        Assertions.assertEquals("Mircea Eliadee", savedAuthor.getName());
        Assertions.assertEquals(LocalDate.of(1837, 03, 1), savedAuthor.getBirthDate());
        Assertions.assertEquals(LocalDate.of(1986, 4, 22), savedAuthor.getDeathDate());
        Assertions.assertEquals("romanian", savedAuthor.getNationality());
    }
}
