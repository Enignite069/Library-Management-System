package com.library_management.mapper;

import com.library_management.dto.request.system.BookRequest;
import com.library_management.dto.response.system.BookResponse;
import com.library_management.entity.system.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    Book toBook(BookRequest request);

    BookResponse toBookResponse(Book book);

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    void updateBook(@MappingTarget Book book, BookRequest request);
}
