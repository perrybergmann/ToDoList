package com.example.todolist;

import com.example.todolist.DataModel.ToDoData;
import com.example.todolist.DataModel.ToDoItems;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {

    @FXML
    private TextField shortDescriptionField;

    @FXML
    private TextArea detailsArea;

    @FXML
    private DatePicker deadlinePicker;

    public ToDoItems processResults() {
        String shortDescription = shortDescriptionField.getText().trim();
        String details = detailsArea.getText().trim();
        LocalDate deadlineValue = deadlinePicker.getValue();

        ToDoItems newItem = new ToDoItems(shortDescription, details, deadlineValue);
        ToDoData.getInstance().addTodoItem(newItem);
        return newItem;
    }
}
