package ro.unibuc.hello.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ro.unibuc.hello.data.AuthorEntity;
import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.dto.BookCreationRequestDto;
import ro.unibuc.hello.service.BookService;

@ExtendWith(SpringExtension.class)
public class BookControllerTest {

        @Mock
        private BookService bookService;

        @InjectMocks
        private BookController bookController;

        private MockMvc mockMvc;

        private ObjectMapper objectMapper;

        private BookEntity book;

        @BeforeEach
        public void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
                objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                book = BookEntity.builder().bookId("1").title("Baltagul").genre("roman")
                                .publicationDate(LocalDate.of(1930, 11, 1))
                                .publisher("Cartea Romaneasca")
                                .author(AuthorEntity.builder().name("Mihail Sadoveanu").build())
                                .build();
        }

        @Test
        void test_GetBooksByAuthor() throws Exception {
                // Given
                var bookList = List.of(book);
                var authorId = "1";
                when(bookService.getBooksByAuthor(anyString())).thenReturn(bookList);

                // When
                var result = mockMvc.perform(get("/books/authors/{authorId}", authorId))
                                .andExpect(status().isOk())
                                .andReturn();

                // Then
                Assertions.assertEquals(objectMapper.writeValueAsString(bookList),
                                result.getResponse().getContentAsString());

        }

        @Test
        void test_GetBooksByReader() throws Exception {
                // Given
                var bookList = List.of(book);
                var readerId = "1";
                when(bookService.getBooksByReader(anyString())).thenReturn(bookList);

                // When
                var result = mockMvc.perform(get("/books/readers/{readerId}", readerId))
                                .andExpect(status().isOk())
                                .andReturn();

                // Then
                Assertions.assertEquals(objectMapper.writeValueAsString(bookList),
                                result.getResponse().getContentAsString());

        }

        @Test
        void test_PostBook() throws Exception {
                // Given
                var bookCreationRequestDto = BookCreationRequestDto.builder().title("Baltagul")
                                .genre("roman")
                                .publicationDate(LocalDate.of(1930, 11, 1))
                                .publisher("Cartea Romaneasca")
                                .authorId("1")
                                .build();
                when(bookService.saveBook(any())).thenReturn(book);

                // When
                var result = mockMvc.perform(post("/books")
                                .content(objectMapper.writeValueAsString(bookCreationRequestDto))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andReturn();

                // Then
                Assertions.assertEquals(objectMapper.writeValueAsString(book),
                                result.getResponse().getContentAsString());
        }

        @Test
        void test_DeleteBook() throws Exception {
                // Given
                var bookId = "1";
                doNothing().when(bookService).deleteBookAndReadingRecords(anyString());

                // When
                var result = mockMvc.perform(delete("/books/{bookId}", bookId))
                                .andExpect(status().isOk())
                                .andReturn();

                // Then
                Assertions.assertEquals("Book and associated records deleted successfully",
                                result.getResponse().getContentAsString());
        }

}