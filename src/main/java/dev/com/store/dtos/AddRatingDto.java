package dev.com.store.dtos;

import dev.com.store.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddRatingDto {
    private Long bookId;
    private String content;
    private User user;
    private int rating;
}
