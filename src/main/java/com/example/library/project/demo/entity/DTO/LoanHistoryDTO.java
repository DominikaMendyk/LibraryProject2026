package com.example.library.project.demo.entity.DTO;
import java.time.LocalDate;

public class LoanHistoryDTO {
    private String title;
    private String author;
    private String publisher;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public LoanHistoryDTO(String title, String author, String publisher, LocalDate loanDate, LocalDate returnDate) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getReturnDate() { return returnDate; }
}
