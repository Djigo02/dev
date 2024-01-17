package sn.djigo.parrainage.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sn.djigo.parrainage.entities.Notification;
import sn.djigo.parrainage.entities.Outils;

import java.io.IOException;

public class PageElecteurController {

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtPrenom;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> nomCol;

    @FXML
    private TableColumn<?, ?> prenomCol;

    @FXML
    private Button btnParrainer;

    @FXML
    private Button btnDeconnexion;

    @FXML
    private TableView<?> tabCandidat;

    @FXML
    private TextField txtNbrParrainage;

    @FXML
    private TextField txtNom;

    @FXML
    void btnParrainer_clicked(ActionEvent event) {

    }

    @FXML
    void btnDeconnexion_clicked(ActionEvent event) throws IOException {
        Notification.NotifSuccess("Deconnexion","Bye a la prochaine !");
        Outils.load(event,"Page de connexion", "/pages/connexion.fxml");
    }

}
