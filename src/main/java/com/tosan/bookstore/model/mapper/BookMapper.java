package com.tosan.bookstore.model.mapper;

import com.tosan.bookstore.model.dto.BookDTO;
import com.tosan.bookstore.model.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "id", ignore = true)
    BookEntity mapDtoToEntity(BookDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateBookEntityFromDto(BookDTO dto, @MappingTarget BookEntity entity);
}
