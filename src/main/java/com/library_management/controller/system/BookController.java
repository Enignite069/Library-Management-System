package com.library_management.controller.system;

import com.library_management.dto.request.system.BookRequest;
import com.library_management.dto.response.global.ApiResponse;
import com.library_management.dto.response.system.BookResponse;
import com.library_management.service.system.BookService;
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
@RequestMapping("/books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Book Management")
@Slf4j
public class BookController {
    BookService bookService;

    @Operation(
            summary = "Creating new Book",
            description = "Input required fields to create new book"
    )
    @PostMapping
    ApiResponse<BookResponse> create(@RequestBody @Valid BookRequest request) {
        log.info("Controller: Creating Book..........");
        return ApiResponse.<BookResponse>builder()
                .result(bookService.create(request))
                .build();
    }

    @Operation(
            summary = "Get all books",
            description = "Get all books in the library"
    )
    @GetMapping
    ApiResponse<List<BookResponse>> getAll() {
        log.info("Controller: Getting All Book..........");
        return ApiResponse.<List<BookResponse>>builder()
                .result(bookService.getAll())
                .build();
    }

    @Operation(
            summary = "Get book by ID",
            description = "Input ID to see detail of a book"
    )
    @GetMapping("/{book}")
    ApiResponse<BookResponse> getById(@PathVariable("book") String book) {
        log.info("Controller: Getting Book By ID..........");
        return ApiResponse.<BookResponse>builder()
                .result(bookService.getBook(book))
                .build();
    }

    @Operation(
            summary = "Update book by ID",
            description = "Input ID to update detail of a book"
    )
    @PutMapping("/{bookId}")
    ApiResponse<BookResponse> update(@PathVariable String bookId, @RequestBody BookRequest request) {
        log.info("Controller: Updating Book By ID..........");
        return ApiResponse.<BookResponse>builder()
                .result(bookService.update(bookId, request))
                .build();
    }

    @Operation(
            summary = "Delete book by ID",
            description = "Input ID to delete a book"
    )
    @DeleteMapping("/{bookId}")
    ApiResponse<Void> delete(@PathVariable String bookId) {
        bookService.delete(bookId);
        log.info("Controller: Deleting Book By ID..........");
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/find-by-name/{title}")
    ApiResponse<BookResponse> getBookByTitle(@PathVariable("title") String title) {
        log.info("Controller: Getting book with title:" + title + "..........");
        return ApiResponse.<BookResponse>builder()
                .result(bookService.getBookByTitle(title))
                .build();
    }

    @GetMapping("/find-by-category/{id}")
    ApiResponse<List<BookResponse>> getBookByCategory(@PathVariable("id") String id) {
        log.info("Controller: Sorting books by category..............");
        return ApiResponse.<List<BookResponse>>builder()
                .result(bookService.getBookByCategory(id))
                .build();
    }
}
