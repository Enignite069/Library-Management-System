package com.library_management.dto.response.system;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublisherResponse {
    String id;
    String name;
    String detail;
}
