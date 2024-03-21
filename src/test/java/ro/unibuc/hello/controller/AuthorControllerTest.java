package ro.unibuc.hello.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.unibuc.hello.data.AuthorEntity;
import ro.unibuc.hello.service.AuthorService;

@ExtendWith(SpringExtension.class)
public class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_GetAuthors() throws Exception {
        // Arrange
        var authorEntity = AuthorEntity.builder().authorId("1").name("Ion Creanga").nationality("romanian")
                .birthDate(LocalDate.of(1837, 03, 1)).deathDate(LocalDate.of(1889, 12, 31))
                .build();
        var authorList = List.of(authorEntity);
        when(authorService.getAllAuthors()).thenReturn(authorList);

        // Act
        var result = mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(objectMapper.writeValueAsString(authorList),
                result.getResponse().getContentAsString());

    }
}
