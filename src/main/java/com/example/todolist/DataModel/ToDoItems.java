package com.example.todolist.DataModel;

import java.time.LocalDate;

public class ToDoItems {
    private String shortDescritption;
    private String details;
    private LocalDate deadline;

    public String getShortDescritption() {
        return shortDescritption;
    }

    public void setShortDescritption(String shortDescritption) {
        this.shortDescritption = shortDescritption;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public ToDoItems(String shortDescritption, String details, LocalDate deadline) {
        this.shortDescritption = shortDescritption;
        this.details = details;
        this.deadline = deadline;


    }

//    @Override
//    public String toString() {
//        return shortDescritption;
//    }
}
