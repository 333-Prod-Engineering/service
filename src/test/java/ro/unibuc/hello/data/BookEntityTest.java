package ro.unibuc.hello.data;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class BookEntityTest {

    @Test
    public void testSetBookId() {
        var bookEntity = new BookEntity();
        var bookId = "123";
        bookEntity.setBookId(bookId);
        assertEquals(bookId, bookEntity.getBookId());
    }

    @Test
    public void testSetTitle() {
        var bookEntity = new BookEntity();
        var title = "Test Title";
        bookEntity.setTitle(title);
        assertEquals(title, bookEntity.getTitle());
    }

    @Test
    public void testSetGenre() {
        var bookEntity = new BookEntity();
        var genre = "Test Genre";
        bookEntity.setGenre(genre);
        assertEquals(genre, bookEntity.getGenre());
    }

    @Test
    public void testSetPublicationDate() {
        var bookEntity = new BookEntity();
        var publicationDate = LocalDate.of(2022, 3, 28);
        bookEntity.setPublicationDate(publicationDate);
        assertEquals(publicationDate, bookEntity.getPublicationDate());
    }

    @Test
    public void testSetPublisher() {
        var bookEntity = new BookEntity();
        var publisher = "Test Publisher";
        bookEntity.setPublisher(publisher);
        assertEquals(publisher, bookEntity.getPublisher());
    }

    @Test
    public void testSetAuthor() {
        var bookEntity = new BookEntity();
        var authorEntity = new AuthorEntity();
        bookEntity.setAuthor(authorEntity);
        assertEquals(authorEntity, bookEntity.getAuthor());
    }
}

