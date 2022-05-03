package com.example.todolist.DataModel;

import javafx.collections.FXCollections;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class ToDoData {
    private static ToDoData instance = new ToDoData();
    private static String filename = "TodoListItems.txt";

    private List<ToDoItems> todoItems;
    private DateTimeFormatter formatter;

    public static ToDoData getInstance() {
        return instance;
    }

    private ToDoData() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public List<ToDoItems> getTodoItems() {
        return todoItems;
    }

    public void setTodoItems(List<ToDoItems> todoItems) {
        this.todoItems = todoItems;
    }

    public void loadToDoItems() throws IOException {
        todoItems = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split("\t");

                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];

                LocalDate date = LocalDate.parse(dateString, formatter);
                ToDoItems todoItem = new ToDoItems(shortDescription, details, date);
                todoItems.add(todoItem);

            }
        } finally {
            if (br != null) {
                br.close();
            }
        }

    }

    public void storeToDoItems() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            Iterator<ToDoItems> iter = todoItems.iterator();
            while (iter.hasNext()) {
                ToDoItems item = iter.next();
                bw.write(String.format("%s\t%s\t%s",
                        item.getShortDescritption(),
                        item.getDetails(),
                        item.getDeadline().format(formatter)));

                bw.newLine();
            }


        } finally {
            if (bw != null) {
                bw.close();
            }
        }

    }

}