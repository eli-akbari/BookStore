package com.tosan.bookstore.model.mapper;

import com.tosan.bookstore.model.dto.CustomerDTO;
import com.tosan.bookstore.model.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "id", ignore = true)
    CustomerEntity toCustomerEntity(CustomerDTO customerDTO);

    void updateCustomerEntity(CustomerDTO customerDTO, @org.mapstruct.MappingTarget CustomerEntity customerEntity);
}
