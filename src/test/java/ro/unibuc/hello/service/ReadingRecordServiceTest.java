package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.data.BookRepository;
import ro.unibuc.hello.data.ReaderEntity;
import ro.unibuc.hello.data.ReaderRepository;
import ro.unibuc.hello.data.ReadingRecordEntity;
import ro.unibuc.hello.data.ReadingRecordRepository;
import ro.unibuc.hello.dto.ReadingRecordCreationRequestDto;
import ro.unibuc.hello.exception.DuplicateEntityException;
import ro.unibuc.hello.exception.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ReadingRecordServiceTest {

    @Mock
    private ReadingRecordRepository readingRecordRepository;

    @Mock
    private ReaderRepository readerRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    ReadingRecordService readingRecordService;

    private ReaderEntity reader;

    private BookEntity book;

    @BeforeEach
    public void setup() {
        readingRecordRepository.deleteAll();

        reader = ReaderEntity.builder()
                .readerId("65fbeeb6748071114eae8d5c")
                .name("John Doe")
                .nationality("American")
                .phoneNumber("1234567890")
                .birthDate(LocalDate.of(1980, 1, 1))
                .registrationDate(LocalDate.now())
                .build();

        book = BookEntity.builder()
                .bookId("65fbeeb5748071114eae8d59")
                .title("Example Book")
                .genre("Fiction")
                .publicationDate(LocalDate.of(2020, 1, 1))
                .publisher("Example Publisher")
                .build();
    }

    @Test
    public void test_getAllReadingRecords() throws Exception {
        // Given
        var readingRecord = ReadingRecordEntity.builder()
                .readingRecordId("65fbeeb5748071114fff8d59")
                .book(book)
                .reader(reader)
                .dateStarted(LocalDate.now())
                .build();
        var readingRecordList = List.of(readingRecord);
        when(readingRecordRepository.findAll()).thenReturn(readingRecordList);

        // When
        var response = readingRecordService.getAllReadingRecords();

        // Then
        Assertions.assertEquals(readingRecordList, response);
    }

    @Test
    public void test_SaveReadingRecordOk() throws Exception {
        // Given
        var readingRecordCreationRequestDto = ReadingRecordCreationRequestDto.builder()
                .bookId("65fbeeb5748071114eae8d59")
                .readerId("65fbeeb6748071114eae8d5c")
                .build();

        var readingRecord = ReadingRecordEntity.builder()
                .readingRecordId("65fbeeb5748071114fff8d59")
                .book(book)
                .reader(reader)
                .dateStarted(LocalDate.now())
                .build();

        when(bookRepository.findById(readingRecordCreationRequestDto.getBookId())).thenReturn(Optional.of(book));
        when(readerRepository.findById(readingRecordCreationRequestDto.getReaderId())).thenReturn(Optional.of(reader));
        when(readingRecordRepository.findByReaderAndBook(any(), any())).thenReturn(null);
        when(readingRecordRepository.save(any())).thenReturn(readingRecord);

        // When
        var response = readingRecordService.saveReadingRecord(readingRecordCreationRequestDto);

        // Then
        Assertions.assertEquals(readingRecord, response);
    }

    @Test
    public void test_SaveReadingRecordThrowsEntityNotFoundExceptionForBook() {
        // Given
        var readingRecordCreationRequestDto = ReadingRecordCreationRequestDto.builder()
                .bookId("65fbeeb5748071114eae8d59")
                .readerId("65fbeeb6748071114eae8d5c")
                .build();

        // When
        when(bookRepository.findById(readingRecordCreationRequestDto.getBookId()))
                .thenReturn(Optional.empty());

        // Then
        var exception = assertThrows(EntityNotFoundException.class,
                () -> readingRecordService.saveReadingRecord(readingRecordCreationRequestDto));
        Assertions.assertEquals(
                "Entity: BookEntity not found for id: " + readingRecordCreationRequestDto.getBookId()
                        + " was not found",
                exception.getMessage());
    }

    @Test
    public void test_SaveReadingRecordThrowsEntityNotFoundExceptionForReader() {
        // Given
        var readingRecordCreationRequestDto = ReadingRecordCreationRequestDto.builder()
                .bookId("65fbeeb5748071114eae8d59")
                .readerId("65fbeeb6748071114eae8d5c")
                .build();

        // When
        when(readerRepository.findById(readingRecordCreationRequestDto.getBookId()))
                .thenReturn(Optional.empty());

        // Then
        var exception = assertThrows(EntityNotFoundException.class,
                () -> readingRecordService.saveReadingRecord(readingRecordCreationRequestDto));
        Assertions.assertEquals(
                "Entity: BookEntity not found for id: " + readingRecordCreationRequestDto.getBookId()
                        + " was not found",
                exception.getMessage());
    }

    @Test
    public void test_SaveReadingRecordThrowsDuplicateEntityException() throws Exception {
        // Given
        var readingRecordCreationRequestDto = ReadingRecordCreationRequestDto.builder()
                .bookId("65fbeeb5748071114eae8d59")
                .readerId("65fbeeb6748071114eae8d5c")
                .build();

        var readingRecord = ReadingRecordEntity.builder()
                .readingRecordId("65fbeeb5748071114fff8d59")
                .book(book)
                .reader(reader)
                .dateStarted(LocalDate.now())
                .build();

        // When
        when(bookRepository.findById(readingRecordCreationRequestDto.getBookId())).thenReturn(Optional.of(book));
        when(readerRepository.findById(readingRecordCreationRequestDto.getReaderId())).thenReturn(Optional.of(reader));
        when(readingRecordRepository.findByReaderAndBook(reader, book)).thenReturn(readingRecord);

        // Then
        var exception = Assertions.assertThrows(DuplicateEntityException.class,
                () -> readingRecordService.saveReadingRecord(readingRecordCreationRequestDto));
        Assertions.assertEquals("A reading record already exists for the given ids.", exception.getMessage());
    }

}