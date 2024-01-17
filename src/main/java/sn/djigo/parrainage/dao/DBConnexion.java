package sn.djigo.parrainage.dao;

import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DBConnexion {

    //Pour la connexion
    private Connection conn;

    //Pour les requetes SELECT
    private ResultSet rs;

    //Pour les requettes preparés

    private PreparedStatement pstm;

    //Pour les requetes de mise a jour (INSERT INTO, UPDATE, DELETE)
    private int ok ;


    public void getConnection(){
        String url = "jdbc:postgresql://localhost:5432/sponsorship_db";
        String user = "postgres";
        String pass = "passer";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url,user,pass);
        }catch (Exception e){
            System.out.println("Connexion echoue");
            e.printStackTrace();
        }
    }

    public void initPrepare(String sql){
        try{
            getConnection();
            pstm = conn.prepareStatement(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ResultSet executeSelect(){
        rs = null;
        try{
            rs = pstm.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    public int executeMaj(){
        try{
            ok = pstm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok;
    }

    public void closeConnection(){
        try{
            if(conn != null)
                conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public PreparedStatement getPstm() {
        return pstm;
    }
}
