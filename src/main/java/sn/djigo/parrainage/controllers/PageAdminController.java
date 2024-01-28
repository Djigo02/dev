package sn.djigo.parrainage.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sn.djigo.parrainage.dao.DBConnexion;
import sn.djigo.parrainage.dao.IRole;
import sn.djigo.parrainage.dao.RoleImpl;
import sn.djigo.parrainage.entities.Notification;
import sn.djigo.parrainage.entities.Role;
import sn.djigo.parrainage.entities.Utilisateur;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PageAdminController  implements Initializable {

    private Utilisateur auth;

    public Utilisateur getAuth() {
        return auth;
    }

    public void setAuth(Utilisateur auth) {
        this.auth = auth;
    }

    private DBConnexion db = new DBConnexion();

    @FXML
    private Label nbrElecteur;

    @FXML
    private TableColumn<Utilisateur, String> loginCol;

    @FXML
    private TableColumn<Utilisateur, Integer> etatCol;

    @FXML
    private Label nbrCandidat;

    @FXML
    private TableColumn<Utilisateur, Integer> idCol;

    @FXML
    private TableColumn<Utilisateur, String> profilCol;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSelectionner;

    @FXML
    private TableColumn<Utilisateur, String> nomCol;

    @FXML
    private TableColumn<Utilisateur, String> prenomCol;

    @FXML
    private TableView<Utilisateur> tabUtilisateurs;
    @FXML
    private TextField txtNom;

    @FXML
    private ComboBox<String> cbbProfil;

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtMotdepasse;

    @FXML
    private TextField txtActived;

    @FXML
    private TextField txtPrenom;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnSupprimer;

    @FXML
    void btnAjouter_clicked(ActionEvent event) {
        if (txtPrenom.getText().trim().isEmpty() || txtNom.getText().trim().isEmpty() || txtLogin.getText().trim().isEmpty() ||
                txtMotdepasse.getText().trim().isEmpty() || txtActived.getText().trim().isEmpty()
        ){
            Notification.NotifError("Erreur", "Tous les champs sont obligatoires");
        }else {
            String sql = "INSERT INTO utilisateurs (prenom, nom, login, motdepasse, actived, profil) VALUES (?,?,?,?,?,?)";
            try{
                if( cbbProfil.getSelectionModel().getSelectedItem().toString().equals("ROLE_ELECTEUR")){
                    db.initPrepare(sql);
                    db.getPstm().setString(1,txtPrenom.getText().trim());
                    db.getPstm().setString(2,txtNom.getText().trim());
                    db.getPstm().setString(3,txtLogin.getText().trim());
                    db.getPstm().setString(4,txtMotdepasse.getText().trim());
                    db.getPstm().setInt(5,Integer.parseInt(txtActived.getText().trim()));
                    db.getPstm().setInt(6, 3);
                    db.executeMaj();
                    Notification.NotifSuccess("Succès", " Utilisateur ajouté avec succès !");
                    loadNbrUser();
                    loadTableUsers();
                    loadCbbProfil();
                    db.closeConnection();
                    vider();
                }else if(cbbProfil.getSelectionModel().getSelectedItem().toString().equals("ROLE_CANDIDAT") &&
                        Integer.parseInt(nbrCandidat.getText())<5){
                    db.initPrepare(sql);
                    db.getPstm().setString(1,txtPrenom.getText().trim());
                    db.getPstm().setString(2,txtNom.getText().trim());
                    db.getPstm().setString(3,txtLogin.getText().trim());
                    db.getPstm().setString(4,txtMotdepasse.getText().trim());
                    db.getPstm().setInt(5,Integer.parseInt(txtActived.getText().trim()));
                    db.getPstm().setInt(6, 2);
                    db.executeMaj();
                    Notification.NotifSuccess("Succès", " Utilisateur ajouté avec succès !");
                    loadNbrUser();
                    loadTableUsers();
                    loadCbbProfil();
                    db.closeConnection();
                    vider();
                }else{
                    Notification.NotifError("Candidat", "Vous ne pouvez plus ajouté de candidat (MAX 5)! ");
                }
            }catch (SQLException e){
                Notification.NotifError("Erreur", "Erreur lors de l'ajout");
                e.printStackTrace();
            }
        }

    }

    @FXML
    void btnModifier_clicked(ActionEvent event) {
        btnAjouter.setDisable(false);
        vider();
    }

    @FXML
    void btnSelectionner_clicked(ActionEvent event) {
        btnAjouter.setDisable(false);
        vider();
    }

    @FXML
    void btnSupprimer_clicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSelectionner.setText("Vider");
        // chargé la table des utilisateurs
        loadTableUsers();
        // chargé le nombre de candidats et d'electeurs
        loadNbrUser();
        // chargé les profils dans le combobox
        loadCbbProfil();
    }

    ///Recuperer la liste des utilisateurs dans la base puis les retourne
    public ObservableList<Utilisateur> getUtilisateurs(){
        // Recuperer tous les utilisateurs sauf l'admin
        String sql = "SELECT * FROM utilisateurs u, roles r where u.profil=r.idR AND u.profil != 1 ORDER BY u.idU ASC";
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

    /// Recupere les utilisateurs de la table a partir de la methode `getUtilisateurs`
    // puis les charges dans le tableView
    public void loadTableUsers(){
        ObservableList<Utilisateur> users  = getUtilisateurs();
        tabUtilisateurs.setItems(users);
        idCol.setCellValueFactory(new PropertyValueFactory<Utilisateur, Integer>("id"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("prenom"));
        nomCol.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("nom"));
        loginCol.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("login"));
        profilCol.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("profilName"));
        etatCol.setCellValueFactory(new PropertyValueFactory<Utilisateur, Integer>("actived"));
    }

    ///Recupere le nombre de condidat et d'electeurs dans cet ordre puis le retourne sous forme
    // d'ArrayList
    public ArrayList<Integer> getNbrUser(){
        String sql = "SELECT COUNT(profil), profil FROM utilisateurs where profil != 1 GROUP BY profil ORDER BY profil ASC";
        ArrayList <Integer> nbrElement = new ArrayList<Integer>();
        try{
            db.initPrepare(sql);
            ResultSet rs = db.executeSelect();
            while (rs.next()){
                int s = rs.getInt(1);
                nbrElement.add(s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return nbrElement;
    }

    /// Modifie l'apercu sur la page admin le nombre de candidats et d'electeurs
    /// a partir de la methode `getNbrUser`
    public void loadNbrUser(){
        ArrayList<Integer> u = getNbrUser();
        nbrCandidat.setText(""+u.get(0));
        nbrElecteur.setText(""+u.get(1));
    }

    // Charger les profils candidats et electeurs dans le combobox
    public void loadCbbProfil(){
        IRole iRole = new RoleImpl();
        ArrayList<Role> roles = iRole.getAllRole();
        ObservableList<String> listeProfil = FXCollections.observableArrayList(roles.get(0).getNomprofil(),roles.get(1).getNomprofil());
        cbbProfil.setItems(listeProfil);
    }

    //Permet de vider le formulaire
    public void vider(){
        txtPrenom.setText("");
        txtNom.setText("");txtLogin.setText("");
        txtMotdepasse.setText("");txtActived.setText("");
    }
    //
    @FXML
    void getUserInfo(MouseEvent event) {
        Utilisateur u = tabUtilisateurs.getSelectionModel().getSelectedItem();
        txtLogin.setText(u.getLogin());
        txtPrenom.setText(u.getPrenom());
        txtNom.setText(u.getNom());
        txtMotdepasse.setText((u.getMotdepasse()));
        txtActived.setText(""+u.getActived());
        btnAjouter.setDisable(true);


    }
}