package ro.unibuc.hello.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import io.micrometer.core.instrument.MeterRegistry;
import ro.unibuc.hello.data.ReviewEntity;
import ro.unibuc.hello.dto.ReviewCreationRequestDto;
import ro.unibuc.hello.dto.ReviewUpdateRequestDto;
import ro.unibuc.hello.service.ReviewService;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MeterRegistry metricsRegistry;

    private final AtomicLong createReviewCounter = new AtomicLong();
    private final AtomicLong updateReviewCounter = new AtomicLong();
    private final AtomicLong deleteReviewCounter = new AtomicLong();

    @PostMapping("/reviews")
    @ResponseBody
    public ResponseEntity<ReviewEntity> createReview(@RequestBody ReviewCreationRequestDto reviewDto) {
        ReviewEntity savedReview = reviewService.saveReview(reviewDto);
        metricsRegistry.counter("controller_create_review_counter", "endpoint", "createReview").increment(createReviewCounter.incrementAndGet());
        return ResponseEntity.ok(savedReview);
    }

    @PatchMapping("/reviews/{id}")
    @ResponseBody
    public ResponseEntity<ReviewEntity> updateAuthor(@PathVariable String id,
            @RequestBody ReviewUpdateRequestDto updateReviewRequestDto) {
        var updatedReview = reviewService.updateReview(id, updateReviewRequestDto);
        metricsRegistry.counter("controller_update_review_counter", "endpoint", "updateReview").increment(updateReviewCounter.incrementAndGet());
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable String id) {
        reviewService.deleteReview(id);
        metricsRegistry.counter("controller_delete_review_counter", "endpoint", "deleteReview").increment(deleteReviewCounter.incrementAndGet());
        return ResponseEntity.ok("Review deleted successfully");
    }
}
