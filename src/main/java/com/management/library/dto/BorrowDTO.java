package com.management.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class BorrowDTO {
    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "Borrower name is required")
    private String borrowerName;

    @NotNull(message = "Borrow date is required")
    private LocalDate borrowDate;

    private LocalDate returnDate;
}