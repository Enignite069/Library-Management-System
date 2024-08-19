package com.library_management.dto.request.identity;

import java.util.Set;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    @Size(min = 1, message = "INVALID_INPUT")
    String name;

    String description;

    Set<String> permissions;
}
