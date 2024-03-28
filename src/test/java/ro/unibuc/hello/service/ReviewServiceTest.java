package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.data.ReaderEntity;
import ro.unibuc.hello.data.ReadingRecordEntity;
import ro.unibuc.hello.data.ReadingRecordRepository;
import ro.unibuc.hello.data.ReviewEntity;
import ro.unibuc.hello.data.ReviewRepository;
import ro.unibuc.hello.dto.ReviewCreationRequestDto;
import ro.unibuc.hello.dto.ReviewUpdateRequestDto;
import ro.unibuc.hello.exception.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ReviewServiceTest {

        @Mock
        private ReadingRecordRepository readingRecordRepository;

        @Mock
        private ReviewRepository reviewRepository;

        @InjectMocks
        ReviewService reviewService;

        private BookEntity book;

        private ReaderEntity reader;

        private ReadingRecordEntity readingRecord;

        private ReviewEntity review;

        @BeforeEach
        public void setup() {
                reviewRepository.deleteAll();

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
                                .rating(5)
                                .datePosted(LocalDate.now())
                                .reviewBody("E marfa, frate!")
                                .readingRecord(readingRecord)
                                .build();
        }

        @Test
        public void test_SaveReviewOk() throws Exception {
                // Given
                var reviewCreationRequestDto = ReviewCreationRequestDto.builder()
                                .rating(5)
                                .reviewBody("E marfa, frate!")
                                .readingRecordId("1")
                                .build();

                when(readingRecordRepository.findById(reviewCreationRequestDto.getReadingRecordId()))
                                .thenReturn(Optional.of(readingRecord));
                when(reviewRepository.save(any())).thenReturn(review);

                // When
                var response = reviewService.saveReview(reviewCreationRequestDto);

                // Then
                Assertions.assertEquals(review, response);
        }

        @Test
        public void test_saveReview_EntityNotFound() {

                // Given
                var reviewCreationRequestDto = ReviewCreationRequestDto.builder()
                                .rating(5)
                                .reviewBody("E marfa, frate!")
                                .readingRecordId("1")
                                .build();

                when(readingRecordRepository.findById(any()))
                                .thenThrow(new EntityNotFoundException("ReadingRecordEntity not found for id: 1"));

                // When
                // Then
                var exception = assertThrows(EntityNotFoundException.class,
                                () -> reviewService.saveReview(reviewCreationRequestDto));
                Assertions.assertEquals("Entity: ReadingRecordEntity not found for id: 1 was not found",
                                exception.getMessage());
        }

        @Test
        public void test_updateReview_ok() throws Exception {
                // Given
                var reviewUpdateRequestDto = ReviewUpdateRequestDto.builder().reviewBody("buna").rating(3)
                                .build();

                when(reviewRepository.findById(any())).thenReturn(Optional.of(review));
                when(reviewRepository.save(any())).thenReturn(review);

                // When
                var response = reviewService.updateReview("1", reviewUpdateRequestDto);

                // Then
                Assertions.assertEquals(reviewUpdateRequestDto.getReviewBody(), response.getReviewBody());
                Assertions.assertEquals(reviewUpdateRequestDto.getRating(), response.getRating());
        }

        @Test
        public void test_updateReader_EntityNotFoundException() {
                // Given
                var reviewUpdateRequestDto = ReviewUpdateRequestDto.builder().reviewBody("buna").rating(3)
                                .build();

                when(reviewRepository.findById("1")).thenReturn(Optional.empty());

                // When
                // Then
                var exception = assertThrows(EntityNotFoundException.class,
                                () -> reviewService.updateReview("1", reviewUpdateRequestDto));
                Assertions.assertEquals("Entity: 1 was not found", exception.getMessage());
        }

        @Test
        public void test_deleteReview_ok() throws Exception {
                // Given
                doNothing().when(reviewRepository).deleteById("1");

                // When
                // Then
                assertDoesNotThrow(() -> {
                        reviewService.deleteReview("1");
                });
        }

}
