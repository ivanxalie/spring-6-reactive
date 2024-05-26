package guru.springframework.spring6reactive.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerDTO {
    private Long id;
    @NotEmpty
    @Size(min = 1, max = 255)
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}