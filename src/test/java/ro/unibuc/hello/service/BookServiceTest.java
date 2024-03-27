package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.AuthorEntity;
import ro.unibuc.hello.data.AuthorRepository;
import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.data.BookRepository;
import ro.unibuc.hello.data.ReaderEntity;
import ro.unibuc.hello.data.ReaderRepository;
import ro.unibuc.hello.data.ReadingRecordEntity;
import ro.unibuc.hello.data.ReadingRecordRepository;
import ro.unibuc.hello.dto.BookCreationRequestDto;
import ro.unibuc.hello.exception.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ReaderRepository readerRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private ReadingRecordRepository readingRecordRepository;

    @InjectMocks
    BookService bookService = new BookService();

    @Mock
    AuthorService authorService = new AuthorService();

    @Mock
    ReaderService readerService = new ReaderService();

    @BeforeEach
    public void setup() {
        bookRepository.deleteAll();
    }

    @Test
    public void test_getAllBooksByReader_Ok() throws Exception {
        // Given
        var book = BookEntity.builder().bookId("1").title("Baltagul").genre("roman")
                .publicationDate(LocalDate.of(1930, 11, 1))
                .publisher("Cartea Romaneasca")
                .author(AuthorEntity.builder().name("Mihail Sadoveanu").build())
                .build();

        var reader = ReaderEntity.builder().name("Ionescu Ana").nationality("romanian")
                .phoneNumber("0764783987").birthDate(LocalDate.of(2002, 11, 24))
                .registrationDate(LocalDate.now())
                .readerId("1")
                .build();

        var readingRecord = ReadingRecordEntity.builder()
                .readingRecordId("1")
                .book(book)
                .reader(reader)
                .dateStarted(LocalDate.now())
                .build();

        var bookList = List.of(book);
        var readerId = "1";
        var readingRecordList = List.of(readingRecord);
        when(readerRepository.findById(anyString())).thenReturn(Optional.of(reader));
        when(readingRecordRepository.findByReader(any())).thenReturn(readingRecordList);

        // When
        var response = bookService.getBooksByReader(readerId);

        // Then
        Assertions.assertEquals(bookList, response);
    }

    @Test
    public void test_getAllBooksByReader_EntityNotFoundException() throws Exception {
        // Given
        var book = BookEntity.builder().bookId("1").title("Baltagul").genre("roman")
                .publicationDate(LocalDate.of(1930, 11, 1))
                .publisher("Cartea Romaneasca")
                .author(AuthorEntity.builder().name("Mihail Sadoveanu").build())
                .build();

        var reader = ReaderEntity.builder().name("Ionescu Ana").nationality("romanian")
                .phoneNumber("0764783987").birthDate(LocalDate.of(2002, 11, 24))
                .registrationDate(LocalDate.now())
                .readerId("1")
                .build();

        var readingRecord = ReadingRecordEntity.builder()
                .readingRecordId("1")
                .book(book)
                .reader(reader)
                .dateStarted(LocalDate.now())
                .build();

        var readerId = "1";
        var readingRecordList = List.of(readingRecord);
        when(readerRepository.findById(anyString()))
                .thenThrow(new EntityNotFoundException("Reader not found with id: " + readerId));
        when(readingRecordRepository.findByReader(any())).thenReturn(readingRecordList);

        // When
        var exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.getBooksByReader("1"));
        Assertions.assertEquals("Entity: Reader not found with id: 1 was not found", exception.getMessage());

    }

    @Test
    public void test_getAllBooksByAuthor_Ok() throws Exception {
        // Given
        var author = AuthorEntity.builder().authorId("1").name("Mircea Eliade").nationality("romanian")
                .birthDate(LocalDate.of(1837, 03, 1)).deathDate(LocalDate.of(1986, 4, 22))
                .build();

        var book = BookEntity.builder().bookId("1").title("Baltagul").genre("roman")
                .publicationDate(LocalDate.of(1930, 11, 1))
                .publisher("Cartea Romaneasca")
                .author(author)
                .build();

        var bookList = List.of(book);
        var authorId = "1";
        when(authorRepository.findById(anyString())).thenReturn(Optional.of(author));
        when(bookRepository.findByAuthor(any())).thenReturn(bookList);

        // When
        var response = bookService.getBooksByAuthor(authorId);

        // Then
        Assertions.assertEquals(bookList, response);
    }

    @Test
    public void test_getAllBooksByAuthor_EntityNotFoundException() throws Exception {
        
        // Given
        var authorId = "1";
        when(authorRepository.findById(anyString()))
                .thenThrow(new EntityNotFoundException("Author not found with id: " + authorId));

        // When
        var exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.getBooksByAuthor("1"));
        Assertions.assertEquals("Entity: Author not found with id: 1 was not found", exception.getMessage());

    }

    @Test
    public void test_saveBook_ok() throws Exception {

        // Given
        var author = AuthorEntity.builder().authorId("1").name("Mircea Eliade").nationality("romanian")
                .birthDate(LocalDate.of(1837, 03, 1)).deathDate(LocalDate.of(1986, 4, 22))
                .build();

        var book = BookEntity.builder().bookId("1").title("Baltagul").genre("roman")
                .publicationDate(LocalDate.of(1930, 11, 1))
                .publisher("Cartea Romaneasca")
                .author(author)
                .build();

        var bookCreationRequestDto = BookCreationRequestDto.builder().title("Baltagul")
                .genre("roman")
                .publicationDate(LocalDate.of(1930, 11, 1))
                .publisher("Cartea Romaneasca")
                .authorId("1")
                .build();

        when(bookRepository.save(any())).thenReturn(book);
        when(authorRepository.findById(anyString())).thenReturn(Optional.of(author));

        // When
        var response = bookService.saveBook(bookCreationRequestDto);

        // Then
        Assertions.assertEquals(book, response);
    }

    @Test
    public void test_deleteBook_ok() throws Exception {
        // Given
        var book = BookEntity.builder().bookId("1").title("Baltagul").genre("roman")
                .publicationDate(LocalDate.of(1930, 11, 1))
                .publisher("Cartea Romaneasca")
                .author(AuthorEntity.builder().authorId("1").name("Ion Creanga").nationality("romanian")
                        .birthDate(LocalDate.of(1837, 03, 1)).deathDate(LocalDate.of(1889, 12, 31))
                        .build())
                .build();

        var bookId = "1";
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        doNothing().when(readingRecordRepository).deleteReadingRecordsByBook(book);

        // When
        // Then
        assertDoesNotThrow(() -> {
            bookService.deleteBookAndReadingRecords(bookId);
        });
    }
}
