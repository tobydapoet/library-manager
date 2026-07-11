package com.example.LibraryManager.mappers;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.StreamSupport;

public interface EntityMapper<E, R> {
    R toResponse(E entity);

    default List<R> toResponses(Iterable<E> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::toResponse)
                .toList();
    }

    default Page<R> toResponsePage(Page<E> entities) {
        return entities.map(this::toResponse);
    }
}
