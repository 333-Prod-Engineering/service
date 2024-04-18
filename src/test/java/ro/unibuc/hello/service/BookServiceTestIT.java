package ro.unibuc.hello.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ro.unibuc.hello.data.AuthorRepository;
import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.data.BookRepository;
import ro.unibuc.hello.data.ReaderEntity;
import ro.unibuc.hello.data.ReaderRepository;
import ro.unibuc.hello.data.ReadingRecordEntity;
import ro.unibuc.hello.data.ReadingRecordRepository;

@SpringBootTest
public class BookServiceTestIT {

    @MockBean
    ReadingRecordRepository readingRecordRepository;

    @MockBean
    ReaderRepository readerRepository;

    @Autowired
    BookService bookService;

    @Test
    public void multipleBooksShouldBePresentForReader() {

        // Arrange
        var readerId = "8683";

        var reader = new ReaderEntity(
                readerId,
                "John Doe",
                "American",
                "john.doe@example.com",
                "+1234567890",
                LocalDate.of(1990, 5, 15),
                LocalDate.now());

        var readingRecords = new ArrayList<>(List.of(
                new ReadingRecordEntity(),
                new ReadingRecordEntity(),
                new ReadingRecordEntity()));

        var optionalReader = Optional.of(reader);
        when(readerRepository.findById(readerId)).thenReturn(optionalReader);
        when(readingRecordRepository.findByReader(reader)).thenReturn(readingRecords);

        // Act
        var books = bookService.getBooksByReader(readerId);

        // Assert
        Assertions.assertEquals(3, books.size());
    }

}
