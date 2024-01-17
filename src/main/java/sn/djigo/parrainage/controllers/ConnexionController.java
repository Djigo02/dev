package sn.djigo.parrainage.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sn.djigo.parrainage.dao.DBConnexion;
import sn.djigo.parrainage.dao.IUtilisateur;
import sn.djigo.parrainage.dao.UtilisateurImpl;
import sn.djigo.parrainage.entities.Notification;
import sn.djigo.parrainage.entities.Outils;
import sn.djigo.parrainage.entities.Utilisateur;

public class ConnexionController {
    private DBConnexion db = new DBConnexion();

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtMotdepasse;

    @FXML
    private Button btnConnexion;

    @FXML
    void onClickConnexion(ActionEvent event) {
        IUtilisateur iUtilisateur = new UtilisateurImpl();
        if (txtLogin.getText().trim().equals("") || txtMotdepasse.getText().trim().equals("")){
            Notification.NotifError("Erreur", "Tous les champs sont obligatoires !");
        }else{
            try{
                Utilisateur u = null;
                u = iUtilisateur.seConnecter(txtLogin.getText().trim(), txtMotdepasse.getText().trim());
                if(u!=null){
                    Notification.NotifSuccess("Succès", "Connexion reussie !!");
                    switch (u.getProfil()){
                        case 1:
                            Outils.load(event,"Page d'administrateur", "/pages/page-admin.fxml");
                            break;
                        case 2:
                            Outils.load(event,"Page du Candidat", "/pages/page-candidat.fxml");
                            break;
                        case 3:
                            Outils.load(event,"Page de l'electeur", "/pages/page-electeur.fxml");
                            break;
                    }
                }else{
                    Notification.NotifError("Erreur", "Email et/ou mot de passe incorrect !!");
                }
                txtLogin.setText("");
                txtMotdepasse.setText("");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}