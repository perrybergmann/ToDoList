package com.example.todolist;

import com.example.todolist.DataModel.ToDoData;
import com.example.todolist.DataModel.ToDoItems;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

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

    @FXML
    private ContextMenu listContextMenu;

    @FXML
    private ToggleButton filterToggleButton;

    private FilteredList<ToDoItems> filterList;

    private Predicate<ToDoItems> wantAllItems;
    private Predicate<ToDoItems> wantTodaysItems;

    public void initialize() {

        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ToDoItems item = toDoListView.getSelectionModel().getSelectedItem();
                deleteItem(item);
            }
        });

        listContextMenu.getItems().addAll(deleteMenuItem);

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

        wantAllItems = new Predicate<ToDoItems>() {
            @Override
            public boolean test(ToDoItems toDoItems) {
                return true;
            }
        };

        wantTodaysItems = new Predicate<ToDoItems>() {
            @Override
            public boolean test(ToDoItems toDoItems) {
                return (toDoItems.getDeadline().equals(LocalDate.now()));
            }
        };

        filterList = new FilteredList<>(ToDoData.getInstance().getTodoItems(), wantAllItems);

                SortedList < ToDoItems > sortedList = new SortedList<ToDoItems>(filterList,
                        new Comparator<ToDoItems>() {
                            @Override
                            public int compare(ToDoItems o1, ToDoItems o2) {
                                return o1.getDeadline().compareTo(o2.getDeadline());
                            }

                        });

//        toDoListView.setItems(ToDoData.getInstance().getTodoItems());
        toDoListView.setItems(sortedList);
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        toDoListView.getSelectionModel().selectFirst();

        toDoListView.setCellFactory(new Callback<ListView<ToDoItems>, ListCell<ToDoItems>>() {
            @Override
            public ListCell<ToDoItems> call(ListView<ToDoItems> toDoItemsListView) {
                ListCell<ToDoItems> cell = new ListCell<>() {

                    @Override
                    protected void updateItem (ToDoItems item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty) {
                            setText(null);
                        } else {
                            setText(item.getShortDescritption());
                            if(item.getDeadline().isBefore(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.RED);
                            } else if (item.getDeadline().equals(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.PURPLE);
                            }
                        }
                    }

                };

                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if(isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        }

                );
                return cell;

            }
        });
    }

    @FXML
    public void showNewItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add New ToDo Item");
        dialog.setHeaderText("Use this dialog to creat a new todo item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            ToDoItems newItem = controller.processResults();
            toDoListView.getSelectionModel().select(newItem);

        }

    }
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        ToDoItems selectedItem = toDoListView.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            if(keyEvent.getCode().equals(KeyCode.DELETE)) {
                deleteItem(selectedItem);
            }
        }
    }


    @FXML
    public void handleCLickListView() {

        ToDoItems item = toDoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getDetails());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        deadlineLabel.setText(df.format(item.getDeadline()));
    }

    public void deleteItem(ToDoItems item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete ToDo Item");
        alert.setHeaderText("Delete Item: " + item.getShortDescritption());
        alert.setContentText("Are you sure? Press ok to confirm or cancel to back out.");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && (result.get() == ButtonType.OK)) {
            ToDoData.getInstance().deleteTodoItem(item);
        }
    }

    @FXML
    public void handleFilterButton() {
        ToDoItems selectedItem = toDoListView.getSelectionModel().getSelectedItem();
        if(filterToggleButton.isSelected()) {
            filterList.setPredicate(wantTodaysItems);
            if(filterList.isEmpty()) {
                itemDetailsTextArea.clear();
                deadlineLabel.setText("");
            } else if(filterList.contains(selectedItem)) {
                toDoListView.getSelectionModel().select(selectedItem);
            } {
                toDoListView.getSelectionModel().selectFirst();
            }
        } else {
            filterList.setPredicate(wantAllItems);
            toDoListView.getSelectionModel().select(selectedItem);

        }
    }

    @FXML
    public void handleExit() {
        Platform.exit();
    }

}