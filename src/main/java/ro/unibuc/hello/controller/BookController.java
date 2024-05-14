package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.dto.BookCreationRequestDto;
import ro.unibuc.hello.service.BookService;


import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private MeterRegistry meterRegistry;

    @PostMapping("/books")
    @ResponseBody
    public ResponseEntity<BookEntity> createBook(@RequestBody BookCreationRequestDto bookCreationRequestDto) {
        // Timer.Sample sample = Timer.start(meterRegistry);
        var newBook = bookService.saveBook(bookCreationRequestDto);
        // sample.stop(meterRegistry.timer("book.create.execution.time"));
        return ResponseEntity.ok(newBook);
    }

    @GetMapping("/books/authors/{authorId}")
    @ResponseBody
    public ResponseEntity<List<BookEntity>> getBooksByAuthor(@PathVariable String authorId) {
        // Timer.Sample sample = Timer.start(meterRegistry);
        var books = bookService.getBooksByAuthor(authorId);
        // sample.stop(meterRegistry.timer("book.get.by.author.execution.time"));
        return ResponseEntity.ok(books);
    }

    @GetMapping("/books/readers/{readerId}")
    @ResponseBody
    public ResponseEntity<List<BookEntity>> getBooksByReader(@PathVariable String readerId) {
        // Timer.Sample sample = Timer.start(meterRegistry);
        var books = bookService.getBooksByReader(readerId);
        // sample.stop(meterRegistry.timer("book.get.by.reader.execution.time"));
        return ResponseEntity.ok(books);
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable String bookId) {
        // Timer.Sample sample = Timer.start(meterRegistry);
        bookService.deleteBookAndReadingRecords(bookId);
        // sample.stop(meterRegistry.timer("book.delete.execution.time"));
        return ResponseEntity.ok("Book and associated records deleted successfully");
    }

}
