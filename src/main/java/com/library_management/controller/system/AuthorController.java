package com.library_management.controller.system;

import com.library_management.dto.request.system.AuthorRequest;
import com.library_management.dto.request.system.AuthorUpdateRequest;
import com.library_management.dto.response.global.ApiResponse;
import com.library_management.dto.response.system.AuthorResponse;
import com.library_management.service.system.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Author Management")
@Slf4j
public class AuthorController {
    AuthorService authorService;

    @Operation(
            summary = "Create new book"
    )
    @PostMapping
    ApiResponse<AuthorResponse> create(@RequestBody @Valid AuthorRequest request) {
        log.info("Controller: Creating Author..........");
        return ApiResponse.<AuthorResponse>builder()
                .result(authorService.create(request))
                .build();
    }

    @Operation(
            summary = "Get all books"
    )
    @GetMapping
    ApiResponse<List<AuthorResponse>> getAll() {
        log.info("Controller: Getting All Author..........");
        return ApiResponse.<List<AuthorResponse>>builder()
                .result(authorService.getAll())
                .build();
    }

    @Operation(
            summary = "Get author's information"
    )
    @GetMapping("/{author}")
    ApiResponse<AuthorResponse> getById(@PathVariable("author") String author) {
        log.info("Controller: Getting Author By ID..........");
        return ApiResponse.<AuthorResponse>builder()
                .result(authorService.getAuthor(author))
                .build();
    }

    @Operation(
            summary = "Update author"
    )
    @PutMapping("/{authorId}")
    ApiResponse<AuthorResponse> update(@PathVariable String authorId, @RequestBody AuthorUpdateRequest request) {
        log.info("Controller: Updating Author By ID..........");
        return ApiResponse.<AuthorResponse>builder()
                .result(authorService.updateAuthor(authorId, request))
                .build();
    }

    @Operation(
            summary = "Delete author"
    )
    @DeleteMapping("/{authorId}")
    ApiResponse<Void> delete(@PathVariable String authorId) {
        authorService.delete(authorId);
        log.info("Controller: Deleting Author By ID..........");
        return ApiResponse.<Void>builder().build();
    }
}
