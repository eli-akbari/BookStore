package com.tosan.bookstore.model.mapper;

import com.tosan.bookstore.model.dto.OrderItemDTO;
import com.tosan.bookstore.model.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "book.id", target = "bookId")
    OrderItemDTO orderItemEntityToOrderItemDTO(OrderItemEntity orderItemEntity);

    List<OrderItemDTO> orderItemEntitiesToOrderItemDTOs(List<OrderItemEntity> orderItemEntities);
}
