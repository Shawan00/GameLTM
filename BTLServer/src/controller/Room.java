
package controller;

import dao.UserDAO;

import java.io.IOException;
import java.util.Random;

public class Room {
    private final int id;
    private final ServerThread user1;
    private ServerThread user2;
    private String password;
    private final UserDAO userDAO;
    private int [][] matrix;
    private int scoreUser1, scoreUser2;
    private boolean sendResult, updateHistory;

    public Room(ServerThread user1) {
        System.out.println("Tạo phòng thành công, ID là: " + ServerMain.ROOM_ID);
        sendResult = false;
        updateHistory = false;
        this.password = " ";
        this.id = ServerMain.ROOM_ID++;
        userDAO = new UserDAO();
        this.user1 = user1;
        this.user2 = null;
        Random rand = new Random();
        matrix = new int[7][7];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                matrix[i][j] = rand.nextInt(9) + 1; // Generate values from 1 to 9
            }
        }
        scoreUser1 = -1;
        scoreUser2 = -1;
    }
    
    public void updateMatrix(){
        Random rand = new Random();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                matrix[i][j] = rand.nextInt(9) + 1; // Generate values from 1 to 9
            }
        }
    }

    public int[][] getMatrix() {
        return matrix;
    }
    
    
    public int getId() {
        return id;
    }

    public ServerThread getUser2() {
        return user2;
    }

    public void setUser2(ServerThread user2) {
        this.user2 = user2;
    }

    public ServerThread getUser1() {
        return user1;
    }

    public int getScoreUser1() {
        return scoreUser1;
    }

    public void setScoreUser1(int scoreUser1) {
        this.scoreUser1 = scoreUser1;
    }

    public int getScoreUser2() {
        return scoreUser2;
    }

    public void setScoreUser2(int scoreUser2) {
        this.scoreUser2 = scoreUser2;
    }

    public boolean isSendResult() {
        return sendResult;
    }

    public void setSendResult(boolean sendResult) {
        this.sendResult = sendResult;
    }

    public boolean isUpdateHistory() {
        return updateHistory;
    }

    public void setUpdateHistory(boolean updateHistory) {
        this.updateHistory = updateHistory;
    }
    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNumberOfUser() {
        return user2 == null ? 1 : 2;
    }

    public void boardCast(String message) {
        try {
            user1.write(message);
            user2.write(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int getCompetitorID(int ID_ClientNumber) {
        if (user1.getClientNumber() == ID_ClientNumber)
            return user2.getUser().getID();
        return user1.getUser().getID();
    }

    public ServerThread getCompetitor(int ID_ClientNumber) {
        if (user1.getClientNumber() == ID_ClientNumber)
            return user2;
        return user1;
    }

    public void setUsersToPlaying() {
        userDAO.updateToPlaying(user1.getUser().getID());
        if (user2 != null) {
            userDAO.updateToPlaying(user2.getUser().getID());
        }
    }

    public void setUsersToNotPlaying() {
        userDAO.updateToNotPlaying(user1.getUser().getID());
        if (user2 != null) {
            userDAO.updateToNotPlaying(user2.getUser().getID());
        }
    }


    public void increaseNumberOfGame() {
        userDAO.addGame(user1.getUser().getID());
        userDAO.addGame(user2.getUser().getID());
    }

    public void increaseNumberOfDraw() {
        userDAO.addDrawGame(user1.getUser().getID());
        userDAO.addDrawGame(user2.getUser().getID());
    }

    public void decreaseNumberOfGame() {
        userDAO.decreaseGame(user1.getUser().getID());
        userDAO.decreaseGame(user2.getUser().getID());
    }


}
