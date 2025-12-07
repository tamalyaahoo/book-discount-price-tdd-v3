package com.bnpp.kata.book.price.mapper;

import com.bnpp.kata.book.price.dto.BookResponse;
import com.bnpp.kata.book.price.store.BookEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class BookMapperTest {

    private final BookMapper mapper = Mappers.getMapper(BookMapper.class);

    @ParameterizedTest
    @EnumSource(BookEnum.class)
    @DisplayName("MapStruct → should map each BookEnum to BookResponse correctly")
    void testToResponseMapping(BookEnum bookEnum) {

        BookResponse response = mapper.toResponse(bookEnum);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(bookEnum.id);
        assertThat(response.title()).isEqualTo(bookEnum.title);
        assertThat(response.author()).isEqualTo(bookEnum.author);
        assertThat(response.year()).isEqualTo(bookEnum.year);
        assertThat(response.price()).isEqualTo(bookEnum.price);
    }

    @Test
    @DisplayName("Mapper should return null when input enum is null")
    void testNullEnum_returnsNull() {
        assertNull(mapper.toResponse(null));
    }
}