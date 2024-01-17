package sn.djigo.parrainage.dao;

import sn.djigo.parrainage.entities.Utilisateur;

public interface IUtilisateur {
    public Utilisateur seConnecter(String login, String password);
}
