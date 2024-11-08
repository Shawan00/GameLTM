
package dao;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.History;
import model.Pikachu;

public class PikachuDao extends connectToDatabase {

    public PikachuDao() {
        super();
    }

    public void addPikachu(String avatarUrl){
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "INSERT INTO `btlviet`.`pikachu` (`avatar`) VALUES (?);");
            preparedStatement.setString(1, avatarUrl);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deletedPikachu(int id){
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "DELETE FROM `btlviet`.`pikachu` WHERE (`id` = ?);");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public List<Pikachu> getListPikachu(){
        try {
            String sql = "select * from pikachu";
            PreparedStatement prepareStatement = con.prepareStatement(sql);
            List<Pikachu> listPikachu = new ArrayList<>();
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){  
                listPikachu.add(new Pikachu(rs.getInt(1), rs.getString(2)));  
            }
            return listPikachu;
        } catch (SQLException ex) {
            Logger.getLogger(PikachuDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
