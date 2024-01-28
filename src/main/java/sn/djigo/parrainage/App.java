package sn.djigo.parrainage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sn.djigo.parrainage.entities.Utilisateur;

public class App extends Application {

    public static Utilisateur auth = new Utilisateur();

    public static Utilisateur getAuth() {
        return auth;
    }

    public static void setAuth(Utilisateur auth) {
        App.auth = auth;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/pages/connexion.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Page de connnexion");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
