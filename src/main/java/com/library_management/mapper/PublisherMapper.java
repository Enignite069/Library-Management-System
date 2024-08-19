package com.library_management.mapper;

import com.library_management.dto.request.system.PublisherRequest;
import com.library_management.dto.request.system.PublisherUpdateRequest;
import com.library_management.dto.response.system.PublisherResponse;
import com.library_management.entity.system.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    Publisher toPublisher(PublisherRequest request);

    PublisherResponse toPublisherResponse(Publisher publisher);

    void updatePublisher(@MappingTarget Publisher publisher, PublisherUpdateRequest request);
}
