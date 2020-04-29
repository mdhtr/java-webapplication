package mdhtr.webapplication.endpoint.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
