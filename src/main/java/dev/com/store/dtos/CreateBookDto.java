package dev.com.store.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookDto {
    private String title;
    private String author;
    private String isbn;
    private BigDecimal price;
    private String description;
    private Long stock;
}
