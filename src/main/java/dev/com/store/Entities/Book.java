package dev.com.store.Entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books", uniqueConstraints = { @UniqueConstraint(columnNames = { "isbn" }) })
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private @NotBlank String title;

    @Column
    private @NotBlank String author;

    @Column
    private @NotBlank String isbn;

    @Column
    private BigDecimal price;

    @Column
    private @NotBlank String description;

    @Column
    private LocalDate publishedDate = LocalDate.now();

    @Column
    private Long stock;
}