package ro.unibuc.hello.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.extension.ExtendWith;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.data.ReaderEntity;
import ro.unibuc.hello.data.ReadingRecordEntity;
import ro.unibuc.hello.data.ReviewEntity;
import ro.unibuc.hello.dto.ReviewCreationRequestDto;
import ro.unibuc.hello.dto.ReviewUpdateRequestDto;
import ro.unibuc.hello.service.ReviewService;

@ExtendWith(SpringExtension.class)
public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private ReviewEntity review;

    private ReadingRecordEntity readingRecord;

    private ReaderEntity reader;

    private BookEntity book;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        reader = ReaderEntity.builder()
                .readerId("1")
                .name("John Doe")
                .nationality("American")
                .phoneNumber("1234567890")
                .birthDate(LocalDate.of(1980, 1, 1))
                .registrationDate(LocalDate.now())
                .build();

        book = BookEntity.builder()
                .bookId("1")
                .title("Example Book")
                .genre("Fiction")
                .publicationDate(LocalDate.of(2020, 1, 1))
                .publisher("Example Publisher")
                .build();

        readingRecord = ReadingRecordEntity.builder()
                .readingRecordId("1")
                .book(book)
                .reader(reader)
                .dateStarted(LocalDate.now())
                .build();

        review = ReviewEntity.builder()
                .reviewId("1")
                .readingRecord(readingRecord)
                .reviewBody("Bun")
                .rating(5)
                .datePosted(LocalDate.now())
                .build();
    }

    @Test
    public void test_PostReview() throws Exception {
        // Given
        var reviewCreationRequestDto = ReviewCreationRequestDto.builder().readingRecordId("1").rating(5)
                .reviewBody("bun").build();
        when(reviewService.saveReview(any())).thenReturn(review);

        // When
        var result = mockMvc.perform(post("/reviews")
                .content(objectMapper.writeValueAsString(reviewCreationRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        Assertions.assertEquals(objectMapper.writeValueAsString(review),
                result.getResponse().getContentAsString());
    }

    @Test
    public void test_UpdateReview() throws Exception {
        // Given
        var id = "1";
        var reviewUpdateRequestDto = ReviewUpdateRequestDto.builder().rating(5).reviewBody("buna").build();
        when(reviewService.updateReview(anyString(), any())).thenReturn(review);

        // When
        var result = mockMvc.perform(patch("/reviews/{id}", id)
                .content(objectMapper.writeValueAsString(reviewUpdateRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        Assertions.assertEquals(objectMapper.writeValueAsString(review),
                result.getResponse().getContentAsString());
    }

    @Test
    public void testDeleteReview() throws Exception {
        // Given
        var id = "1";
        doNothing().when(reviewService).deleteReview(anyString());

        // When/Then
        mockMvc.perform(delete("/reviews/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Review deleted successfully"));
    }
}
