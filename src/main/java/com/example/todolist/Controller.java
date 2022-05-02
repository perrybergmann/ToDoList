package com.example.todolist;

import com.example.todolist.DataModel.ToDoItems;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<ToDoItems> toDoItems;

    @FXML
    private ListView<ToDoItems> toDoListView;

    @FXML
    private TextArea itemDetailsTextArea;

    public void initialize() {
        ToDoItems item1 = new ToDoItems("Mail Birthday card", "Buy a 21st birthday card",
                LocalDate.of(2022, Month.MAY, 25));
        ToDoItems item2 = new ToDoItems("Dr Appointment", "See dr about inflamed scrotum",
                LocalDate.of(2022, Month.MAY, 18));
        ToDoItems item3 = new ToDoItems("Win the lottery", "win the lottery and do nothing",
                LocalDate.of(2022, Month.MAY, 20));
        ToDoItems item4 = new ToDoItems("Finish design of house", "send out finished designs of home",
                LocalDate.of(2022, Month.MAY, 13));

        toDoItems = new ArrayList<>();
        toDoItems.add(item1);
        toDoItems.add(item2);
        toDoItems.add(item3);
        toDoItems.add(item4);

        toDoListView.getItems().setAll(toDoItems);
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void handleCLickListView() {

        ToDoItems item = toDoListView.getSelectionModel().getSelectedItem();
//        System.out.println("The Selected item is: " + item);

        StringBuilder sb = new StringBuilder(item.getDetails());
        sb.append("\n\n\n\n");
        sb.append("Due: ");
        sb.append(item.getDeadline().toString());
        itemDetailsTextArea.setText(sb.toString());

        }


}