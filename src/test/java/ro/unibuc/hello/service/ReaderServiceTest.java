package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import ro.unibuc.hello.data.ReaderEntity;
import ro.unibuc.hello.dto.ReaderCreationRequestDto;
import ro.unibuc.hello.dto.ReaderUpdateRequestDto;
import ro.unibuc.hello.exception.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ro.unibuc.hello.data.ReaderRepository;
import ro.unibuc.hello.data.ReadingRecordRepository;

@ExtendWith(SpringExtension.class)
public class ReaderServiceTest {

    @Mock
    private ReaderRepository readerRepository;

    @Mock
    private ReadingRecordRepository readingRecordRepository;

    @InjectMocks
    ReaderService readerService = new ReaderService();

    @Mock
    BookService bookService = new BookService();

    @BeforeEach
    public void setup() {
        readerRepository.deleteAll();
    }

    @Test
    public void test_getAllReaders() throws Exception {
        // Given
        var reader = ReaderEntity.builder().readerId("1").name("Ionescu Ana").nationality("romanian")
                .phoneNumber("0764783987").birthDate(LocalDate.of(2002, 11, 24))
                .registrationDate(LocalDate.now())
                .build();
        var readerList = List.of(reader);
        when(readerRepository.findAll()).thenReturn(readerList);

        // When
        var response = readerService.getAllReaders();

        // Then
        Assertions.assertEquals(readerList, response);
    }

    @Test
    public void test_saveReader_ok() throws Exception {
        // Given
        var reader = ReaderEntity.builder().readerId("1").name("Ionescu Ana").nationality("romanian")
                .phoneNumber("0764783987").birthDate(LocalDate.of(2002, 11, 24))
                .registrationDate(LocalDate.now())
                .build();
        var readerCreationRequest = ReaderCreationRequestDto.builder().name("Ionescu Ana").nationality("romanian")
                .phoneNumber("0764783987").birthDate(LocalDate.of(2002, 11, 24))
                .registrationDate(LocalDate.now())
                .build();
        when(readerRepository.save(any())).thenReturn(reader);

        // When
        var response = readerService.saveReader(readerCreationRequest);

        // Then
        Assertions.assertEquals(reader, response);
    }

    @Test
    public void test_updateReader_ok() throws Exception {
        // Given
        var reader = ReaderEntity.builder().readerId("1").name("Ionescu Ana").nationality("romanian")
                .phoneNumber("0764783987").birthDate(LocalDate.of(2002, 11, 24))
                .registrationDate(LocalDate.now()).email("mail@mail.com")
                .build();
        var readerUpdateRequestDto = ReaderUpdateRequestDto.builder().phoneNumber("0764783987").email("mail@mail.ro")
                .build();

        when(readerRepository.findById(any())).thenReturn(Optional.of(reader));
        when(readerRepository.save(any())).thenReturn(reader);

        // When
        var response = readerService.updateReader("1", readerUpdateRequestDto);

        // Then
        Assertions.assertEquals(readerUpdateRequestDto.getEmail(), response.getEmail());
        Assertions.assertEquals(readerUpdateRequestDto.getPhoneNumber(), response.getPhoneNumber());
    }

    @Test
    public void test_updateReader_EntityNotFoundException() {
        // Given
        ReaderUpdateRequestDto readerUpdateRequestDto = ReaderUpdateRequestDto.builder().phoneNumber("0764783987")
                .email("mail@mail.ro")
                .build();

        when(readerRepository.findById("1")).thenReturn(Optional.empty());

        // When
        // Then
        var exception = assertThrows(EntityNotFoundException.class,
                () -> readerService.updateReader("1", readerUpdateRequestDto));
        Assertions.assertEquals("Entity: 1 was not found", exception.getMessage());
    }

    @Test
    public void test_deleteReader_ok() throws Exception {
        // Given
        var readerId = "1";
        var reader = ReaderEntity.builder().readerId("1").name("Ionescu Ana").nationality("romanian")
                .phoneNumber("0764783987").birthDate(LocalDate.of(2002, 11, 24))
                .registrationDate(LocalDate.now()).email("mail@mail.com")
                .build();
        when(readerRepository.findById(any())).thenReturn(Optional.of(reader));
        when(readingRecordRepository.findByReader(any())).thenReturn(List.of());
        doNothing().when(readingRecordRepository).delete(any());

        // When
        // Then
        assertDoesNotThrow(() -> {
            readerService.deleteReaderAndReadingRecords(readerId);
        });
    }

    @Test
    public void test_deleteReader_RuntimeException() throws Exception {
        // Given
        var readerId = "1";
        when(readerRepository.findById(any())).thenThrow(new RuntimeException("Reader not found with id: " + readerId));

        // When
        // Then
        var exception = assertThrows(RuntimeException.class,
                () -> readerService.deleteReaderAndReadingRecords(readerId));
        Assertions.assertEquals("Reader not found with id: 1", exception.getMessage());
    }

}
