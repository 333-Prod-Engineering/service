package ro.unibuc.hello.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreationRequestDto {
    private int rating;
    private String reviewBody;
    private String readingRecordId;
}
