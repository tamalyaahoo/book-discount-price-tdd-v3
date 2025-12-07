package com.bnpp.kata.book.price.mapper;

import com.bnpp.kata.book.price.dto.BookResponse;
import com.bnpp.kata.book.price.store.BookEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BookMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "year", source = "year")
    @Mapping(target = "price", source = "price")
    BookResponse toResponse(BookEnum bookEnum);
}
