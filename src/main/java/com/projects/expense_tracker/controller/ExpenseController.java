package com.projects.expense_tracker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.expense_tracker.dto.ExpenseDto;
import com.projects.expense_tracker.service.ExpenseService;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) { this.expenseService = expenseService; }

    // Create
    @PostMapping
    public ResponseEntity<ExpenseDto> createExpense(@RequestBody ExpenseDto dto,
                                                    Authentication authentication) {
        String email = authentication.getName();
        ExpenseDto created = expenseService.createExpense(dto, email);
        return ResponseEntity.ok(created);
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<ExpenseDto>> listExpenses(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(expenseService.listExpenses(email));
    }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> getExpense(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(expenseService.getExpense(id, email));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable Long id,
                                                    @RequestBody ExpenseDto dto,
                                                    Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(expenseService.updateExpense(id, dto, email));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        expenseService.deleteExpense(id, email);
        return ResponseEntity.noContent().build();
    }
}

