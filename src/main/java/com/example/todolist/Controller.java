package com.example.todolist;

import com.example.todolist.DataModel.ToDoData;
import com.example.todolist.DataModel.ToDoItems;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

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

    public void initialize() {
//        ToDoItems item1 = new ToDoItems("Mail Birthday card", "Buy a 21st birthday card",
//                LocalDate.of(2022, Month.MAY, 25));
//        ToDoItems item2 = new ToDoItems("Dr Appointment", "See dr about inflamed scrotum",
//                LocalDate.of(2022, Month.MAY, 18));
//        ToDoItems item3 = new ToDoItems("Win the lottery", "win the lottery and do nothing",
//                LocalDate.of(2022, Month.MAY, 20));
//        ToDoItems item4 = new ToDoItems("Finish design of house", "send out finished designs of home",
//                LocalDate.of(2022, Month.MAY, 13));
//
//        toDoItems = new ArrayList<>();
//        toDoItems.add(item1);
//        toDoItems.add(item2);
//        toDoItems.add(item3);
//        toDoItems.add(item4);
//
//        ToDoData.getInstance().setTodoItems(toDoItems);

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
    public void handleCLickListView() {

        ToDoItems item = toDoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getDetails());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        deadlineLabel.setText(df.format(item.getDeadline()));
//        System.out.println("The Selected item is: " + item);

//        StringBuilder sb = new StringBuilder(item.getDetails());
//        sb.append("\n\n\n\n");
//        sb.append("Due: ");
//        sb.append(item.getDeadline().toString());
//        itemDetailsTextArea.setText(sb.toString());

    }


}