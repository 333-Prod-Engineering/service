package ro.unibuc.hello.data;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class AuthorEntityTest {

    @Test
    public void testSetAuthorId() {
        var authorEntity = new AuthorEntity();
        var authorId = "123";
        authorEntity.setAuthorId(authorId);
        assertEquals(authorId, authorEntity.getAuthorId());
    }

    @Test
    public void testSetName() {
        var authorEntity = new AuthorEntity();
        var name = "Test Author";
        authorEntity.setName(name);
        assertEquals(name, authorEntity.getName());
    }

    @Test
    public void testSetNationality() {
        var authorEntity = new AuthorEntity();
        var nationality = "US";
        authorEntity.setNationality(nationality);
        assertEquals(nationality, authorEntity.getNationality());
    }

    @Test
    public void testSetBirthDate() {
        var authorEntity = new AuthorEntity();
        var birthDate = LocalDate.of(1990, 5, 15);
        authorEntity.setBirthDate(birthDate);
        assertEquals(birthDate, authorEntity.getBirthDate());
    }

    @Test
    public void testSetDeathDate() {
        var authorEntity = new AuthorEntity();
        var deathDate = LocalDate.of(2020, 10, 20);
        authorEntity.setDeathDate(deathDate);
        assertEquals(deathDate, authorEntity.getDeathDate());
    }
}

