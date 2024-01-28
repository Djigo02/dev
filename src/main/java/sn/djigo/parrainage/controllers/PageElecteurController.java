package sn.djigo.parrainage.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sn.djigo.parrainage.App;
import sn.djigo.parrainage.dao.DBConnexion;
import sn.djigo.parrainage.entities.Notification;
import sn.djigo.parrainage.entities.Outils;
import sn.djigo.parrainage.entities.Utilisateur;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class PageElecteurController implements Initializable {
    private Utilisateur candidat;

    public Utilisateur getCandidat() {
        return candidat;
    }

    public void setCandidat(Utilisateur candidat) {
        this.candidat = candidat;
    }

    private DBConnexion db = new DBConnexion();

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtPrenom;

    @FXML
    private Button btnParrainer;

    @FXML
    private Button btnDeconnexion;

    @FXML
    private TableView<Utilisateur> tabCandidat;

    @FXML
    private TableColumn<Utilisateur, Integer> idCol;

    @FXML
    private TableColumn<Utilisateur, String> nomCol;

    @FXML
    private TableColumn<Utilisateur, String> prenomCol;


    @FXML
    private TextField txtNbrParrainage;

    @FXML
    private TextField txtNom;

    @FXML
    void btnParrainer_clicked(ActionEvent event) {
        if (txtNbrParrainage.getText().isEmpty()){
            Notification.NotifError("Erreur", "Veuillez selectionner un candidat !");
        }else{
            if(getNbrParainElecteur()!=0){
                Notification.NotifError("Erreur", "Vous avez deja parrainer un candidat !");
            }
            else {
                LocalDate currentDateTime = LocalDate.now();
                String sql = "INSERT INTO parrainer (date_parrainage, electeur, candidat) VALUES (?,?,?)";
                try {
                    db.initPrepare(sql);
//                    db.getPstm().setDate(1, Date.valueOf(currentDateTime));
                    db.getPstm().setDate(1, null);
                    db.getPstm().setInt(2,App.getAuth().getId());
                    db.getPstm().setInt(3,getCandidat().getId());
                    db.executeMaj();
                    db.closeConnection();
                    Notification.NotifSuccess("Parrainage réussi","Vous venez de parrainer un candidat");
                    loadTableCandidat();
                    txtNbrParrainage.setText("");

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    @FXML
    void getCandidatData(MouseEvent event) {
        setCandidat(tabCandidat.getSelectionModel().getSelectedItem());
        String sql = "SELECT COUNT(candidat) FROM parrainer WHERE candidat = ?";
        try {
            db.initPrepare(sql);
            db.getPstm().setInt(1,getCandidat().getId());
            ResultSet rs = db.executeSelect();
            if(rs.next()){
                txtNbrParrainage.setText(""+rs.getInt(1));
            }
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    ///Recuperer la liste des utilisateurs dans la base puis les retourne
    public ObservableList<Utilisateur> getUtilisateurs(){
        // Recuperer tous les candidats
        String sql = "SELECT * FROM utilisateurs u, roles r where u.profil=r.idR AND u.profil = 2 ORDER BY u.idU ASC";
        ResultSet rs = null;
        ObservableList<Utilisateur> users = FXCollections.observableArrayList();
        try {
            db.initPrepare(sql);
            rs = db.executeSelect();
            while (rs.next()){
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt("idU"));
                u.setActived(rs.getInt("actived"));
                u.setPrenom(rs.getString("prenom"));
                u.setNom(rs.getString("nom"));
                u.setLogin(rs.getString("login"));
                u.setProfilName(rs.getString("nomprofil"));
                users.add(u);
            }
            db.closeConnection();
        }catch (Exception exception){
            exception.printStackTrace();
        }

        return users;
    }

    public void loadTableCandidat(){
        ObservableList<Utilisateur> users = getUtilisateurs();
        tabCandidat.setItems(users);
        idCol.setCellValueFactory(new PropertyValueFactory<Utilisateur, Integer>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("prenom"));
    }

    @FXML
    void btnDeconnexion_clicked(ActionEvent event) throws IOException {
        Notification.NotifSuccess("Deconnexion","Bye a la prochaine !");
        Outils.load(event,"Page de connexion", "/pages/connexion.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTableCandidat();
        // Recuperer les
        txtPrenom.setText(App.getAuth().getPrenom());
        txtLogin.setText(App.getAuth().getLogin());
        txtNom.setText(App.getAuth().getNom());
    }

    private int getNbrParainElecteur(){
        int nbr = 0;
        String sql = "SELECT COUNT(electeur) FROM parrainer WHERE electeur ="+ App.getAuth().getId() ;
        try {
            db.initPrepare(sql);
            ResultSet rs = db.executeSelect();
            if(rs.next()){
                nbr = rs.getInt(1);
            }
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return nbr;
    }
}
