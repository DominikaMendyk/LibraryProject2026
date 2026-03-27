package com.example.library.project.demo.controller;

import com.example.library.project.demo.entity.Book;
import com.example.library.project.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED) //code 201
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @DeleteMapping("/remove/{isbn}")
    public String removeBook(@PathVariable String isbn) {
        return bookService.deleteBook(isbn);
    }

    @DeleteMapping("/remove/{isbn}/{copies}")
    public Book removeBook(@PathVariable String isbn, @PathVariable Integer copies) {
        return bookService.removeCopies(isbn, copies);
    }

    @GetMapping("/getAll")
    public Iterable<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn);
    }

    @PutMapping("/update/{isbn}")
    public Book updateBook(@PathVariable String isbn, @RequestBody Book updatedBook) {
        return bookService.updateBook(isbn, updatedBook);
    }
}
