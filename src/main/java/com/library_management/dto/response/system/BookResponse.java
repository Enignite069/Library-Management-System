package com.library_management.dto.response.system;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    String id;

    String title;

    String summary;

    CategoryResponse category;

    PublisherResponse publisher;

    List<AuthorResponse> authors;
}
