package com.library_management.mapper;

import com.library_management.dto.request.system.AuthorRequest;
import com.library_management.dto.request.system.AuthorUpdateRequest;
import com.library_management.dto.response.system.AuthorResponse;
import com.library_management.entity.system.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toAuthor(AuthorRequest request);

    AuthorResponse toAuthorResponse(Author author);

    void updateAuthor(@MappingTarget Author author, AuthorUpdateRequest request);
}
