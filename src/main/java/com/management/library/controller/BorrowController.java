package com.management.library.controller;

import com.management.library.dto.BorrowDTO;
import com.management.library.service.BorrowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @PostMapping("/borrow")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BorrowDTO> borrowBook(@Valid @RequestBody BorrowDTO borrowDTO) {
        return ResponseEntity.ok(borrowService.borrowBook(borrowDTO));
    }

    @PostMapping("/return")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BorrowDTO> returnBook(@RequestBody BorrowDTO borrowDTO) {
        return ResponseEntity.ok(borrowService.returnBook(borrowDTO));
    }
}