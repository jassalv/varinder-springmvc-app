package com.spring.firstthymeleafapp.model;
import com.spring.firstthymeleafapp.validator.NotZero;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionResource {

    TransactionType transactionType;

    @NotBlank(message = "name is compulsory")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "Description should contain only alphabets and spaces!")
    String name = null;

    @NotNull(message = "Amount cannot be null")
    @NotZero(message = "Amount cannot be zero")
    Double amount;

    Integer id;

}
