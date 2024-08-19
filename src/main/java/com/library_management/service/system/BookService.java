package com.library_management.service.system;

import com.library_management.dto.request.system.BookRequest;
import com.library_management.dto.response.system.BookResponse;
import com.library_management.entity.system.Book;
import com.library_management.entity.system.Category;
import com.library_management.entity.system.Publisher;
import com.library_management.exception.AppException;
import com.library_management.exception.ErrorCode;
import com.library_management.mapper.BookMapper;
import com.library_management.repository.system.AuthorRepository;
import com.library_management.repository.system.BookRepository;
import com.library_management.repository.system.CategoryRepository;
import com.library_management.repository.system.PublisherRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookService {
    BookRepository bookRepository;
    BookMapper bookMapper;
    CategoryRepository categoryRepository;
    PublisherRepository publisherRepository;
    AuthorRepository authorRepository;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public BookResponse create(BookRequest request) {
        Book book = bookMapper.toBook(request);
        Category category = categoryRepository.findCategoryById(request.getCategory());
        book.setCategory(category);
        Publisher publisher = publisherRepository.findPublisherById(request.getPublisher());
        book.setPublisher( publisher);
        var authors = authorRepository.findAllById(request.getAuthors());
        book.setAuthors(new HashSet<>(authors));
        try {
            book = bookRepository.save(book);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.BOOK_EXISTED);
        }
        log.info("Service: Create Book");
        return bookMapper.toBookResponse(book);
    }

    public List<BookResponse> getAll() {
        log.info("Service: In method get all books");
        return bookRepository.findAll().stream().map(bookMapper::toBookResponse).toList();
    }

    public BookResponse getBook(String id) {
        log.info("Service: In method get Book by id");
        return bookMapper.toBookResponse(
                bookRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse update(String bookId, BookRequest request) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));
        bookMapper.updateBook(book, request);
        String category = request.getCategory();
        book.setCategory(new Category(category));
        String publisher = request.getPublisher();
        book.setPublisher(new Publisher(publisher));
        var authors = authorRepository.findAllById(request.getAuthors());
        book.setAuthors(new HashSet<>(authors));
        log.info("Service: Update book");
        return bookMapper.toBookResponse(bookRepository.save(book));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String id) {
        bookRepository.deleteById(id);
        log.info("Service: Delete book");
    }

    public BookResponse getBookByTitle(String title) {
        log.info("Service: Return book with title: "+ title);
        return bookMapper.toBookResponse(bookRepository.findAllByTitle(title));
    }

    public List<BookResponse> getBookByCategory(String id) {
        log.info("Service: Sort books according to Category");
        return bookRepository.findAllByCategoryId(id).stream().map(bookMapper::toBookResponse).toList();
    }
}

