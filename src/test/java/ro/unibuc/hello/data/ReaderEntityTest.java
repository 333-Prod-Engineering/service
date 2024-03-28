package ro.unibuc.hello.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class ReaderEntityTest {

    @Test
    public void testSetName() {
        var readerEntity = new ReaderEntity();
        var name = "John Doe";
        readerEntity.setName(name);
        assertEquals(name, readerEntity.getName());
    }

    @Test
    public void testSetNationality() {
        var readerEntity = new ReaderEntity();
        var nationality = "US";
        readerEntity.setNationality(nationality);
        assertEquals(nationality, readerEntity.getNationality());
    }

    @Test
    public void testSetEmail() {
        var readerEntity = new ReaderEntity();
        var email = "john.doe@example.com";
        readerEntity.setEmail(email);
        assertEquals(email, readerEntity.getEmail());
    }

    @Test
    public void testSetPhoneNumber() {
        var readerEntity = new ReaderEntity();
        var phoneNumber = "1234567890";
        readerEntity.setPhoneNumber(phoneNumber);
        assertEquals(phoneNumber, readerEntity.getPhoneNumber());
    }

    @Test
    public void testSetBirthDate() {
        var readerEntity = new ReaderEntity();
        var birthDate = LocalDate.of(1990, 5, 15);
        readerEntity.setBirthDate(birthDate);
        assertEquals(birthDate, readerEntity.getBirthDate());
    }

    @Test
    public void testSetRegistrationDate() {
        var readerEntity = new ReaderEntity();
        var registrationDate = LocalDate.of(2022, 3, 28);
        readerEntity.setRegistrationDate(registrationDate);
        assertEquals(registrationDate, readerEntity.getRegistrationDate());
    }
}
