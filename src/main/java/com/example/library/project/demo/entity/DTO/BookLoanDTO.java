package com.example.library.project.demo.entity.DTO;
import java.time.LocalDate;

public class BookLoanDTO {
    private Integer userId;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public BookLoanDTO(Integer userId, LocalDate loanDate, LocalDate returnDate) {
        this.userId = userId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }
    public Integer getUserId() { return userId; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getReturnDate() { return returnDate; }
}
