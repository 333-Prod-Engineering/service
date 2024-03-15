package ro.unibuc.hello.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AuthorDeleteRequestDto {
    private String name;
}
