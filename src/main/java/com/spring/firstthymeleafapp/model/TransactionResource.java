package com.spring.firstthymeleafapp.model;
import com.spring.firstthymeleafapp.dto.TransactionEntity;
import com.spring.firstthymeleafapp.validator.NotZero;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TransactionResource {

    TransactionType transactionType;

    @NotBlank(message = "name is compulsory")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "Description should contain only alphabets and spaces!")
    String name = null;

    @NotNull(message = "Amount cannot be null")
    @NotZero(message = "Amount cannot be zero")
    Double amount;

    Integer id=null;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionResource that = (TransactionResource) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
