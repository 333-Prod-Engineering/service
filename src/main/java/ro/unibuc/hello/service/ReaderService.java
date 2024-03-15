package ro.unibuc.hello.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import ro.unibuc.hello.data.ReaderRepository;
import ro.unibuc.hello.dto.ReaderCreationRequestDto;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.data.ReaderEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReaderService {

    @Autowired
    private ReaderRepository readerRepository;

    public ReaderEntity saveReader(ReaderCreationRequestDto readerCreationRequestDto) {
        log.debug("Creating a new reader '{}' with email '{}'", readerCreationRequestDto.getName(),
                readerCreationRequestDto.getEmail());
        var readerEntity = mapToReaderEntity(readerCreationRequestDto);
        return readerRepository.save(readerEntity);
    }

    public List<ReaderEntity> getAllReaders() {
        log.debug("Getting all readers");
        return readerRepository.findAll();
    }

    public ReaderEntity updateReader(String id, ReaderCreationRequestDto readerCreationRequestDto) {
        log.debug("Updating the reader with id '{}', setting email '{}' and phone '{}'", id,
                readerCreationRequestDto.getEmail(), readerCreationRequestDto.getPhoneNumber());
        var reader = readerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        reader.setEmail(readerCreationRequestDto.getEmail());
        reader.setPhoneNumber(readerCreationRequestDto.getPhoneNumber());
        return readerRepository.save(reader);
    }

    private ReaderEntity mapToReaderEntity(ReaderCreationRequestDto dto) {
        log.debug("Map readerCreationRequestDto to readerEntity");
        return ReaderEntity.builder()
                .name(dto.getName())
                .nationality(dto.getNationality())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .birthDate(dto.getBirthDate())
                .registrationDate(dto.getRegistrationDate())
                .build();

    }
}