package sn.djigo.parrainage.dao;

import sn.djigo.parrainage.entities.Role;

import java.sql.ResultSet;
import java.util.ArrayList;

public class RoleImpl implements IRole{
    private DBConnexion db = new DBConnexion();
    @Override
    public ArrayList<Role> getAllRole() {
        String sql = "SELECT * FROM roles WHERE idR != 1";
        ArrayList<Role> roles = null;
        try{
            db.initPrepare(sql);
            ResultSet rs = db.executeSelect();
            roles = new ArrayList<Role>();
            while (rs.next()){
                Role role = new Role();
                role.setId(rs.getInt("idR"));
                role.setNomprofil(rs.getString("nomprofil"));
                roles.add(role);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return roles;
    }
}
