package com.management.library.service;

import com.management.library.dto.BookDTO;
import com.management.library.entity.Book;
import com.management.library.exception.ResourceNotFoundException;
import com.management.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<BookDTO> getAllBooks(String search, String sortBy, String order) {
        Specification<Book> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (search != null && !search.isEmpty()) {
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("title")), "%" + search.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("author")), "%" + search.toLowerCase() + "%")
                ));
            }
            query.orderBy(order.equalsIgnoreCase("asc") ?
                    cb.asc(root.get(sortBy)) : cb.desc(root.get(sortBy)));
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return bookRepository.findAll(spec).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return convertToDTO(book);
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        return convertToDTO(bookRepository.save(book));
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setQuantity(bookDTO.getQuantity());
        existingBook.setPublicationDate(bookDTO.getPublicationDate());

        return convertToDTO(bookRepository.save(existingBook));
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        bookRepository.delete(book);
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setQuantity(book.getQuantity());
        bookDTO.setPublicationDate(book.getPublicationDate());
        return bookDTO;
    }

    private Book convertToEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setQuantity(bookDTO.getQuantity());
        book.setPublicationDate(bookDTO.getPublicationDate());
        return book;
    }
}