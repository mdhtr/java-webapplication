package mdhtr.webapplication.endpoint.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@JsonDeserialize(converter = TestValidationDto.Validator.class)
public class TestValidationDto {
    @Min(value = 0, message = "Number should be greater or equal to 0")
    @Max(value = 99, message = "Number should be less than or equal to 99")
    @NotNull
    private Integer number;
    @NotNull
    private String text;

    static class Validator extends DataValidator<TestValidationDto> {
    }
}
