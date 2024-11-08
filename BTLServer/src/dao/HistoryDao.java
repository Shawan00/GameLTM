
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

public class HistoryDao extends connectToDatabase {

    public HistoryDao() {
        super();
    }

    public void addhistory(History history){
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "INSERT INTO `btlviet`.`history` (`idUser1`, `idUser2`, `scoreUser1`, `scoreUser2`)"
                    + "VALUES (?, ?, ?, ?);");
            preparedStatement.setInt(1, history.getIdUser1());
            preparedStatement.setInt(2, history.getIdUser2());
            preparedStatement.setInt(3, history.getScoreUser1());
            preparedStatement.setInt(4, history.getScoreUser2());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public List<History> getListHistory(int id){
        try {
            String sql = "select * from history where idUser1=? or idUser2=?";
            PreparedStatement prepareStatement = con.prepareStatement(sql);
            prepareStatement.setInt(1, id);
            prepareStatement.setInt(2, id);
            List<History> listHistory = new ArrayList<>();
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                if(rs.getInt(2)==id)
                    listHistory.add(new History(rs.getInt(1),rs.getInt(2),
                            rs.getInt(3),rs.getInt(4),
                            rs.getInt(5),rs.getTimestamp(6))); 
                else if(rs.getInt(3)==id)
                    listHistory.add(new History(rs.getInt(1),rs.getInt(3),
                            rs.getInt(2),rs.getInt(5),
                            rs.getInt(4),rs.getTimestamp(6)));
            }
            return listHistory;
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
