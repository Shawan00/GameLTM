
package Bin;

import dao.connectToDatabase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Play;

public class PlayDAO extends connectToDatabase{
    public PlayDAO(){
        super();
    }
    
    public void addPlay(Play play){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO `btl2`.`play` (`input0`, `input1`, `input2`, `input3`, `input4`, `input5`, `input6`, `input7`, `input8`, `input9`)\n"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, play.getInput0());
            preparedStatement.setInt(2, play.getInput()[1]);
            preparedStatement.setInt(3, play.getInput()[2]);
            preparedStatement.setInt(4, play.getInput()[3]);
            preparedStatement.setInt(5, play.getInput()[4]);
            preparedStatement.setInt(6, play.getInput()[5]);
            preparedStatement.setInt(7, play.getInput()[6]);
            preparedStatement.setInt(8, play.getInput()[7]);
            preparedStatement.setInt(9, play.getInput()[8]);
            preparedStatement.setInt(10, play.getInput()[9]);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
     public List<Play> getListPlay() {
        List<Play> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement("Select * from play");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(new Play(rs.getInt(1),rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
