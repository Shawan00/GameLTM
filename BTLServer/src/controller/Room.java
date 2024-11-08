
package controller;

import dao.UserDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Room {
    private final int id;
    private final ServerThread user1;
    private ServerThread user2;
    private String password;
    private final UserDAO userDAO;
    private int scoreUser1, scoreUser2;
    private int cardSelectedN1, cardSelectedN2;
    private boolean sendResult, updateHistory;
    private List<String> listCard;

    public Room(ServerThread user1) {
        System.out.println("Tạo phòng thành công, ID là: " + ServerMain.ROOM_ID);
        sendResult = false;
        updateHistory = false;
        this.password = " ";
        this.id = ServerMain.ROOM_ID++;
        userDAO = new UserDAO();
        this.user1 = user1;
        this.user2 = null;
        cardSelectedN2 = 0;
        cardSelectedN1 = 0;
        scoreUser1 = -1;
        scoreUser2 = -1;
        listCard = new ArrayList<>();
        addListCard();
    }
    
    private void addListCard(){
        for(int i = 1; i <= 9; i++){
            String str = "rcbn";
            for(Character x: str.toCharArray()){
                this.listCard.add(i+x.toString());
            }
        }
        Collections.shuffle(listCard);
    }

    public List<String> getListCard() {
        return listCard;
    }

    public void setListCard(List<String> listCard) {
        this.listCard = listCard;
    }

    public int getCardSelectedN1() {
        return cardSelectedN1;
    }

    public void setCardSelectedN1(int cardSeletedN1) {
        this.cardSelectedN1 = cardSelectedN1;
    }

    public int getCardSelectedN2() {
        return cardSelectedN2;
    }

    public void setCardSelectedN2(int cardSelectedN2) {
        this.cardSelectedN2 = cardSelectedN2;
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
