package com.spring.firstthymeleafapp.dto;

import com.spring.firstthymeleafapp.model.TransactionResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(source="transactionType", target="transactionType")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "amount" , target = "amount")
    @Mapping(source = "id",target = "id")
    TransactionEntity toEntity(TransactionResource transactionResource);

    @Mapping(source="transactionType", target="transactionType")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "amount" , target = "amount")
    @Mapping(source = "id",target = "id")
    TransactionResource toDto(TransactionEntity transaction);

}
