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
import static org.mockito.ArgumentMatchers.any;
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

    @BeforeEach
    public void setup() {
        reviewRepository.deleteAll();

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

        readingRecord = ReadingRecordEntity.builder()
                .readingRecordId("65fbeeb5748071114fff8d59")
                .book(book)
                .reader(reader)
                .dateStarted(LocalDate.now())
                .build();
    }

    @Test
    public void test_SaveReviewOk() throws Exception {
        // Given
        var review = ReviewEntity.builder()
                .reviewId("65fbffb5748071114eae8d59")
                .rating(5)
                .datePosted(LocalDate.now())
                .reviewBody("E marfa, frate!")
                .readingRecord(readingRecord)
                .build();

        var reviewCreationRequestDto = ReviewCreationRequestDto.builder()
                .rating(5)
                .reviewBody("E marfa, frate!")
                .readingRecordId("65fbeeb5748071114fff8d59")
                .build();

        when(readingRecordRepository.findById(reviewCreationRequestDto.getReadingRecordId()))
                .thenReturn(Optional.of(readingRecord));
        when(reviewRepository.save(any())).thenReturn(review);

        // When
        var response = reviewService.saveReview(reviewCreationRequestDto);

        // Then
        Assertions.assertEquals(review, response);
    }

}
