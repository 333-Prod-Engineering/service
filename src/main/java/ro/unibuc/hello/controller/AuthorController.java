package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ro.unibuc.hello.data.AuthorEntity;
import ro.unibuc.hello.service.AuthorService;
import ro.unibuc.hello.dto.AuthorCreationRequestDto;
import ro.unibuc.hello.dto.AuthorDeleteRequestDto;
import ro.unibuc.hello.dto.AuthorUpdateRequestDto;


import io.micrometer.core.instrument.MeterRegistry;

@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;


    @Autowired
    private MeterRegistry meterRegistry;

    @GetMapping("/authors")
    @ResponseBody
    public List<AuthorEntity> getAllAuthors() {
        meterRegistry.counter("my_non_aop_metric_for_get_authors", "endpoint", "get_authors").increment();
        return authorService.getAllAuthors();
    }

    @PostMapping("/authors")
    @ResponseBody
    public ResponseEntity<AuthorEntity> createAuthor(@RequestBody AuthorCreationRequestDto authorCreationRequestDto) {
        meterRegistry.counter("my_non_aop_metric_for_post_authors", "endpoint", "post_authors").increment();
        var newAuthor = authorService.saveAuthor(authorCreationRequestDto);
        return ResponseEntity.ok(newAuthor);
    }

    @PatchMapping("/authors/{id}")
    @ResponseBody
    public ResponseEntity<AuthorEntity> updateAuthor(@PathVariable String id,
            @RequestBody AuthorUpdateRequestDto updateAuthorRequestDto) {
                
        meterRegistry.counter("my_non_aop_metric_for_patch_authors", "endpoint", "patch_authors").increment();
        var updatedAuthor = authorService.updateAuthor(id, updateAuthorRequestDto);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/authors")
    @ResponseBody
    public ResponseEntity<String> deleteAuthor(@RequestBody AuthorDeleteRequestDto authorDeleteRequestDto) {
        meterRegistry.counter("my_non_aop_metric_for_delete_authors", "endpoint", "delete_authors").increment();
        authorService.deleteAuthor(authorDeleteRequestDto);
        return ResponseEntity.ok("Author deleted successfully");
    }
}
