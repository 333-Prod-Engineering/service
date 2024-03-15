package ro.unibuc.hello.utils;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import ro.unibuc.hello.service.AuthorService;
import ro.unibuc.hello.service.ReaderService;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Seeder implements ApplicationRunner {

    private final AuthorService authorService;
    private final ReaderService readerService;

    @Override
    public void run(ApplicationArguments args) {
        // authorService.saveAuthor("Ion Creanga", "romanian", LocalDate.of(1837, 03, 1), LocalDate.of(1889, 12, 31));
        // authorService.saveAuthor("Mihai Eminescu", "romanian", LocalDate.of(1850, 01, 15), LocalDate.of(1889, 06, 15));
        // authorService.saveAuthor("Hector Malot", "french", LocalDate.of(1830, 05, 20), LocalDate.of(1907, 07, 18));
        // authorService.saveAuthor("Victor Hugo", "french", LocalDate.of(1802, 02, 26), LocalDate.of(1885, 05, 22));

        // readerService.saveReader("Ionescu Ana", "romanian", "ionescuana@mail.com", "0765382789", LocalDate.of(2002, 11, 24), LocalDate.now());
        // readerService.saveReader("Smith John", "english", "smithjohn@mail.com", "0786543890", LocalDate.of(1998, 12, 04), LocalDate.now());
        // readerService.saveReader("Popescu Mihai", "romanian", "popescumihai@mail.com", "0765443239", LocalDate.of(2004, 10, 11), LocalDate.now());
        // readerService.saveReader("Pierre Hugo", "french", "pierrehugo@mail.com", "0786889423", LocalDate.of(2000, 01, 25), LocalDate.now());
    }
}
