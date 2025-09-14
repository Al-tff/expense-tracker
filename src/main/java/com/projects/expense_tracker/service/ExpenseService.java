package com.projects.expense_tracker.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projects.expense_tracker.dto.ExpenseDto;
import com.projects.expense_tracker.entity.Expense;
import com.projects.expense_tracker.entity.User;
import com.projects.expense_tracker.exception.ResourceNotFoundException;
import com.projects.expense_tracker.repository.ExpenseRepository;
import com.projects.expense_tracker.repository.UserRepository;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    // helper to map entity<->dto
    private ExpenseDto toDto(Expense e) {
        return new ExpenseDto(e.getId(), e.getAmount(), e.getDescription(), e.getDate(), e.getCategory());
    }

    private Expense fromDto(ExpenseDto dto, User user) {
        Expense e = new Expense();
        e.setAmount(dto.getAmount());
        e.setDescription(dto.getDescription());
        e.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());
        e.setCategory(dto.getCategory());
        e.setUser(user);
        return e;
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));
    }

    //Create Expense
    @Transactional
    public ExpenseDto createExpense(ExpenseDto dto, String userEmail) {
        
        if(dto.getAmount() == null || dto.getAmount() <= 0 )
             throw new ResourceNotFoundException("Amount should be greater than zero");

        if(dto.getCategory() == null || dto.getCategory().trim().isEmpty()) 
             throw new ResourceNotFoundException("Category can't be empty");

        if(dto.getDate() == null) 
             throw new ResourceNotFoundException("Enter a valid date");

        if(dto.getDescription() == null || dto.getDescription().trim().isEmpty()) 
             throw new ResourceNotFoundException("Description can't be empty");
      
        User user = getUserByEmail(userEmail);
        Expense saved = expenseRepository.save(fromDto(dto, user));
        return toDto(saved);
    }

    //List Expenses
    public List<ExpenseDto> listExpenses(String userEmail) {
        User user = getUserByEmail(userEmail);
        return expenseRepository.findByUser(user).stream().map(this::toDto).collect(Collectors.toList());
    }

    //Get Expense by expense ID
    public ExpenseDto getExpense(Long id, String userEmail) {
        if(id <= 0) {
            throw new ResourceNotFoundException("ID can't be less than zero");
        }
        User user = getUserByEmail(userEmail);
        Expense expense = expenseRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
        return toDto(expense);
    }

    //Update Expense
    @Transactional
    public ExpenseDto updateExpense(Long id, ExpenseDto dto, String userEmail) {
        if(id <= 0) {
            throw new ResourceNotFoundException("ID can't be less than zero");
        }
        User user = getUserByEmail(userEmail);
        Expense expense = expenseRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found or not owned by user"));
        
        if (dto.getAmount() != null) expense.setAmount(dto.getAmount());
        if (dto.getDescription() != null) expense.setDescription(dto.getDescription());
        if (dto.getDate() != null) expense.setDate(dto.getDate());
        if (dto.getCategory() != null) expense.setCategory(dto.getCategory());
        Expense updated = expenseRepository.save(expense);
        return toDto(updated);
    }

    //Delete Expense
    public void deleteExpense(Long id, String userEmail) {
        if(id <= 0) {
            throw new ResourceNotFoundException("ID can't be less than zero");
        }
        User user = getUserByEmail(userEmail);
        Expense expense = expenseRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found or not owned by user"));
        expenseRepository.delete(expense);
    }
}