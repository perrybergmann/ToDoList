package com.example.todolist;

import com.example.todolist.DataModel.ToDoData;
import com.example.todolist.DataModel.ToDoItems;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Controller {
    private List<ToDoItems> toDoItems;

    @FXML
    private ListView<ToDoItems> toDoListView;

    @FXML
    private TextArea itemDetailsTextArea;

    @FXML
    private Label deadlineLabel;

    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {


        toDoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItems>() {
            @Override
            public void changed(ObservableValue<? extends ToDoItems> observableValue, ToDoItems toDoItems, ToDoItems t1) {
                if(t1 != null) {
                    ToDoItems item = toDoListView.getSelectionModel().getSelectedItem();
                    itemDetailsTextArea.setText(item.getDetails());
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
                    deadlineLabel.setText(df.format(item.getDeadline()));
                }
            }
        });

        toDoListView.getItems().setAll(ToDoData.getInstance().getTodoItems());
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        toDoListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void showNewItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        try {
            Parent root = FXMLLoader.load(getClass().getResource("todoItemDialog.FXML"));
            dialog.getDialogPane().setContent(root);

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

    }


    @FXML
    public void handleCLickListView() {

        ToDoItems item = toDoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getDetails());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        deadlineLabel.setText(df.format(item.getDeadline()));
    }


}