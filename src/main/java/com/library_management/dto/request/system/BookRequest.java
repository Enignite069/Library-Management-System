package com.library_management.dto.request.system;

import com.library_management.entity.system.Author;
import com.library_management.entity.system.Category;
import com.library_management.entity.system.Publisher;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {
    @Size(min = 1, message = "INVALID_INPUT")
    String title;

    String summary;

    String category;

    String publisher;

    List<String> authors;
}
