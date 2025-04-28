package com.management.library.service;

import com.management.library.dto.BorrowDTO;
import com.management.library.entity.Book;
import com.management.library.entity.Borrow;
import com.management.library.exception.ResourceNotFoundException;
import com.management.library.repository.BookRepository;
import com.management.library.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BookRepository bookRepository;

    public BorrowDTO borrowBook(BorrowDTO borrowDTO) {
        Book book = bookRepository.findById(borrowDTO.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + borrowDTO.getBookId()));

        if (book.getQuantity() <= 0) {
            throw new IllegalStateException("No copies available to borrow");
        }

        Borrow borrow = new Borrow();
        borrow.setBook(book);
        borrow.setBorrowerName(borrowDTO.getBorrowerName());
        borrow.setBorrowDate(borrowDTO.getBorrowDate());

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        return convertToDTO(borrowRepository.save(borrow));
    }

    public BorrowDTO returnBook(BorrowDTO borrowDTO) {
        Book book = bookRepository.findById(borrowDTO.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + borrowDTO.getBookId()));

        Borrow borrow = new Borrow();
        borrow.setBook(book);
        borrow.setBorrowerName(borrowDTO.getBorrowerName());
        borrow.setBorrowDate(borrowDTO.getBorrowDate());
        borrow.setReturnDate(borrowDTO.getReturnDate());

        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        return convertToDTO(borrowRepository.save(borrow));
    }

    private BorrowDTO convertToDTO(Borrow borrow) {
        BorrowDTO borrowDTO = new BorrowDTO();
        borrowDTO.setBookId(borrow.getBook().getId());
        borrowDTO.setBorrowerName(borrow.getBorrowerName());
        borrowDTO.setBorrowDate(borrow.getBorrowDate());
        borrowDTO.setReturnDate(borrow.getReturnDate());
        return borrowDTO;
    }
}