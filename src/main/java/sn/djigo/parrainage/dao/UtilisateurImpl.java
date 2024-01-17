package sn.djigo.parrainage.dao;

import sn.djigo.parrainage.entities.Utilisateur;

import java.sql.ResultSet;

public class UtilisateurImpl implements IUtilisateur {

    private DBConnexion db = new DBConnexion();

    @Override
    public Utilisateur seConnecter(String login, String password) {
        Utilisateur u = new Utilisateur();
        String sql = "SELECT * FROM utilisateurs WHERE login = ? AND motdepasse = ?";
        ResultSet rs = null;

        try{
            db.initPrepare(sql);
            db.getPstm().setString(1, login);
            db.getPstm().setString(2, password);
            rs = db.executeSelect();
            if (rs.next()){
                u.setId(rs.getInt("idU"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setLogin(rs.getString("login"));
                u.setMotdepasse(rs.getString("motdepasse"));
                u.setActived(rs.getInt("actived"));
                u.setProfil(rs.getInt("profil"));
                return u;
            }
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
