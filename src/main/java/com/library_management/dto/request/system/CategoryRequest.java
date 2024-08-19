package com.library_management.dto.request.system;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequest {
    @Size(min = 1, message = "INVALID_INPUT")
    String name;

    String description;
}
