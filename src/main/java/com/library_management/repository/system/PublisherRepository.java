package com.library_management.repository.system;

import com.library_management.entity.system.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, String> {
    Publisher findPublisherById(String id);
}
