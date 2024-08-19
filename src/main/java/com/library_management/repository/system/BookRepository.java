package com.library_management.repository.system;

import com.library_management.entity.system.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Book findAllByTitle(String title);

    List<Book> findAllByCategoryId(String categoryId);
}
