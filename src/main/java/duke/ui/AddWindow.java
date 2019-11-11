package duke.ui;


import duke.Duke;
import duke.enums.Numbers;
import duke.task.TaskList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;


//@@author talesrune
/**
 * Controller for AddWindow. Provides the layout for the other controls.
 */
public class AddWindow extends AnchorPane {
    
    private MainWindow mainWindow;

    @FXML
    private ComboBox<String> cbType;

    @FXML
    private TextField tfDesc;

    @FXML
    private TextField tfDateTime;

    @FXML
    private TextField tfDuration;

    @FXML
    private TextField tfUnit;

    @FXML
    private ComboBox<String> cbExistingTask;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnReset;

    /**
     * Setting up Add Window Interface.
     *
     * @param d The object of Duke.
     * @param mainWindow The main window that runs DUKE Manager.
     */
    @FXML
    public void setAddWindow(Duke d, MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        cbType.getItems().addAll(
                "Todo",
                "Deadline",
                "Fixed Duration"
        );
        TaskList items = d.getTaskList();
        for (int i = Numbers.ZERO.value; i < items.size(); i++) {
            cbExistingTask.getItems().add(items.get(i).getDescription());
        }
    }

    @FXML
    void onMouseClickAdd() {
        System.out.println("clicked on " + cbType.getSelectionModel().getSelectedItem());

        if (cbType.getSelectionModel().getSelectedItem().equals("Todo")) {
            mainWindow.handleUserEvent("todo " + tfDesc.getText().trim());
        } else if (cbType.getSelectionModel().getSelectedItem().equals("Deadline")) {
            mainWindow.handleUserEvent("deadline " + tfDesc.getText().trim() + " /by " + tfDateTime.getText().trim());
        } else if (cbType.getSelectionModel().getSelectedItem().equals("Fixed Duration")) {
            mainWindow.handleUserEvent("fixedduration " + tfDesc.getText().trim() + " /for "
                    +  tfDuration.getText().trim() + " " +  tfUnit.getText().trim());
        }
        mainWindow.listViewRefresh();
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onMouseClickReset() {
        cbType.getSelectionModel().clearSelection();
        tfDesc.clear();
        tfDateTime.clear();
        tfDuration.clear();
        tfUnit.clear();
        cbExistingTask.getSelectionModel().clearSelection();
    }
}