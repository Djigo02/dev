package sn.djigo.parrainage.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sn.djigo.parrainage.App;
import sn.djigo.parrainage.entities.Notification;
import sn.djigo.parrainage.entities.Outils;
import sn.djigo.parrainage.entities.Utilisateur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PageCandidatController implements Initializable {
    private Utilisateur auth;

    public Utilisateur getAuth() {
        return auth;
    }

    public void setAuth(Utilisateur auth) {
        this.auth = auth;
    }

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtPrenom;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableView<?> tabElecteurs;

    @FXML
    private TableColumn<?, ?> nomCol;

    @FXML
    private TableColumn<?, ?> loginCol;

    @FXML
    private TableColumn<?, ?> prenomCol;

    @FXML
    private Button btnDeconnexion;

    @FXML
    private TextField txtNbrParrainage;

    @FXML
    private TextField txtNom;

    @FXML
    void btnDeconnexion_clicked(ActionEvent event) throws IOException {
        Notification.NotifSuccess("Deconnexion","Bye a la prochaine !");
        Outils.load(event,"Page de connexion", "/pages/connexion.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtLogin.setText(App.getAuth().getLogin());
    }
}
