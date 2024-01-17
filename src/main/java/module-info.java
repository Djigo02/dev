module sn.djigo.parrainage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires TrayNotification;


    opens sn.djigo.parrainage to javafx.fxml;
    exports sn.djigo.parrainage;
    exports sn.djigo.parrainage.controllers;
    opens sn.djigo.parrainage.controllers to javafx.fxml;
    exports sn.djigo.parrainage.dao;
    opens sn.djigo.parrainage.dao to javafx.fxml;
    exports sn.djigo.parrainage.entities;
    opens sn.djigo.parrainage.entities to javafx.fxml;
}