package com.library_management.service.system;

import com.library_management.dto.request.system.AuthorRequest;
import com.library_management.dto.request.system.AuthorUpdateRequest;
import com.library_management.dto.response.system.AuthorResponse;
import com.library_management.entity.system.Author;
import com.library_management.exception.AppException;
import com.library_management.exception.ErrorCode;
import com.library_management.mapper.AuthorMapper;
import com.library_management.repository.system.AuthorRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthorService {
    AuthorRepository authorRepository;
    AuthorMapper authorMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public AuthorResponse create(AuthorRequest request) {
        Author author = authorMapper.toAuthor(request);
        author = authorRepository.save(author);
        log.info("Service: Create Author");
        return authorMapper.toAuthorResponse(author);
    }

    public List<AuthorResponse> getAll() {
        var category = authorRepository.findAll();
        log.info("Service: Get all authors");
        return category.stream().map(authorMapper::toAuthorResponse).toList();
    }

    public AuthorResponse getAuthor(String id) {
        log.info("Service: In method get author by id");
        return authorMapper.toAuthorResponse(
                authorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public AuthorResponse updateAuthor(String id, AuthorUpdateRequest request) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        authorMapper.updateAuthor(author, request);
        log.info("Service: Update author");
        return authorMapper.toAuthorResponse(authorRepository.save(author));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String author) {
        authorRepository.deleteById(author);
        log.info("Service: Delete author");
    }
}
