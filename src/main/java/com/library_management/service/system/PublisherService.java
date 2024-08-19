package com.library_management.service.system;

import com.library_management.dto.request.system.PublisherRequest;
import com.library_management.dto.request.system.PublisherUpdateRequest;
import com.library_management.dto.response.system.PublisherResponse;
import com.library_management.entity.system.Publisher;
import com.library_management.exception.AppException;
import com.library_management.exception.ErrorCode;
import com.library_management.mapper.PublisherMapper;
import com.library_management.repository.system.PublisherRepository;
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
public class PublisherService {
    PublisherRepository publisherRepository;
    PublisherMapper publisherMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public PublisherResponse create(PublisherRequest request) {
        Publisher publisher = publisherMapper.toPublisher(request);
        publisher = publisherRepository.save(publisher);
        log.info("Service: Create publisher");
        return publisherMapper.toPublisherResponse(publisher);
    }

    public List<PublisherResponse> getAll() {
        var publisher = publisherRepository.findAll();
        log.info("Service: Get all publisher");
        return publisher.stream().map(publisherMapper::toPublisherResponse).toList();
    }

    public PublisherResponse getPublisher(String id) {
        log.info("Service: In method get publisher by id");
        return publisherMapper.toPublisherResponse(
                publisherRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PublisherResponse updatePublisher(String publisherId, PublisherUpdateRequest request) {
        Publisher publisher = publisherRepository.findById(publisherId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        publisherMapper.updatePublisher(publisher, request);
        log.info("Service: Update publisher");
        return publisherMapper.toPublisherResponse(publisherRepository.save(publisher));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String publisher) {
        publisherRepository.deleteById(publisher);
        log.info("Service: Delete publisher");
    }
}
