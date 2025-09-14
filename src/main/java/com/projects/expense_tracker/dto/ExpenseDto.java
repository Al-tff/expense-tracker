package com.projects.expense_tracker.dto;

import java.time.LocalDate;


public class ExpenseDto {
    private Long id;
    private Double amount;
    private String description;
    private LocalDate date;
    private String category;

    public ExpenseDto() {}

    public ExpenseDto(Long id, Double amount, String description, LocalDate date, String category) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.category = category;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
