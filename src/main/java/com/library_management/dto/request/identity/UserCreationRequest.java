package com.library_management.dto.request.identity;

import com.library_management.validator.dob.DoBConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 4, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    String firstName;

    String lastName;

    @DoBConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;
}
