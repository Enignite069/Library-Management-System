package com.library_management.dto.request.identity;

import com.library_management.validator.dob.DoBConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    String firstName;

    String lastName;

    @DoBConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;

    @NonNull
    List<String> roles;
}
