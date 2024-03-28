package ro.unibuc.hello.data;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class ReadingRecordEntityTest {

    @Test
    public void testSetReadingRecordId() {
        var readingRecordEntity = new ReadingRecordEntity();
        var readingRecordId = "123";
        readingRecordEntity.setReadingRecordId(readingRecordId);
        assertEquals(readingRecordId, readingRecordEntity.getReadingRecordId());
    }

    @Test
    public void testSetBook() {
        var readingRecordEntity = new ReadingRecordEntity();
        var bookEntity = new BookEntity();
        readingRecordEntity.setBook(bookEntity);
        assertEquals(bookEntity, readingRecordEntity.getBook());
    }

    @Test
    public void testSetReader() {
        var readingRecordEntity = new ReadingRecordEntity();
        var readerEntity = new ReaderEntity();
        readingRecordEntity.setReader(readerEntity);
        assertEquals(readerEntity, readingRecordEntity.getReader());
    }

    @Test
    public void testSetDateStarted() {
        var readingRecordEntity = new ReadingRecordEntity();
        var dateStarted = LocalDate.of(2024, 3, 28);
        readingRecordEntity.setDateStarted(dateStarted);
        assertEquals(dateStarted, readingRecordEntity.getDateStarted());
    }

    @Test
    public void testSetDateFinished() {
        var readingRecordEntity = new ReadingRecordEntity();
        var dateFinished = LocalDate.of(2024, 4, 10);
        readingRecordEntity.setDateFinished(dateFinished);
        assertEquals(dateFinished, readingRecordEntity.getDateFinished());
    }
}
