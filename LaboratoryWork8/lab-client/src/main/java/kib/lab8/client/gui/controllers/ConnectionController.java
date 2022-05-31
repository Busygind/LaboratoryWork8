package kib.lab8.client.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import kib.lab8.client.gui.GUIConfig;
import kib.lab8.client.gui.abstractions.AbstractController;
import kib.lab8.client.gui.localization.LanguagesEnum;
import kib.lab8.client.utils.ConnectionModel;
import kib.lab8.client.utils.UserException;
import kib.lab8.common.entities.enums.Mood;


public class ConnectionController extends AbstractController {

    @FXML
    private Button connectButton;

    @FXML
    private TextField portField;

    @FXML
    private TextField hostField;

    @FXML
    private ChoiceBox<LanguagesEnum> languagesChoiceBox;

    @FXML
    private Text text;
    private final ConnectionModel model;
    private final ObservableList<LanguagesEnum> languages = FXCollections.observableArrayList(LanguagesEnum.values());

    @FXML
    private void initialize() {
        languagesChoiceBox.setItems(languages);
        languagesChoiceBox.setValue(LanguagesEnum.ENGLISH);
        portField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                connect();
            }
        });
    }

    public ConnectionController() {
        this.model = new ConnectionModel();
    }

    @FXML
    private void connect() {
        try {
            model.initializeConnectionHandler(hostField.getText(), portField.getText());
            changeScene(GUIConfig.AUTHORIZATION_PATH, controllerClass -> new AuthorizationController(model.getConnectionHandler()));
        } catch (UserException e) {
            e.showAlert();
            hostField.clear();
            portField.clear();
        }
    }
}
