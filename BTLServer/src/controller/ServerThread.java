package controller;

import Bin.PlayDAO;
import dao.HistoryDao;
import dao.PikachuDao;
import dao.UserDAO;
import model.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.History;
import model.Pikachu;
import model.Play;

public class ServerThread implements Runnable {

    private User user;
    private final Socket socketOfServer;
    private final int clientNumber;
    private BufferedReader is;
    private BufferedWriter os;
    private boolean isClosed;
    private Room room;
    private final UserDAO userDAO;
    private final PlayDAO playDao;
    private final HistoryDao historyDao;
    private final PikachuDao pikachuDao;
    private final String clientIP;
    private boolean checkUpdateHistory;

    public ServerThread(Socket socketOfServer, int clientNumber) {
        this.socketOfServer = socketOfServer;
        this.clientNumber = clientNumber;
        System.out.println("Server thread number " + clientNumber + " Started");
        userDAO = new UserDAO();
        playDao = new PlayDAO();
        historyDao = new HistoryDao();
        pikachuDao = new PikachuDao();
        this.checkUpdateHistory = false;
        isClosed = false;
        room = null;

        if (this.socketOfServer.getInetAddress().getHostAddress().equals("127.0.0.1")) {
            clientIP = "127.0.0.1";
        } else {
            clientIP = this.socketOfServer.getInetAddress().getHostAddress();
        }

    }

    public BufferedReader getIs() {
        return is;
    }

    public BufferedWriter getOs() {
        return os;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getClientIP() {
        return clientIP;
    }

    public String getStringFromUser(User user1) {
        return user1.getID() + "," + user1.getUsername()
                + "," + user1.getPassword() + "," + user1.getNickname() + "," +
                user1.getAvatar() + "," + user1.getNumberOfGame() + "," +
                user1.getNumberOfWin() + "," + user1.getNumberOfDraw() + "," + user1.getRank();
    }

    public void goToOwnRoom() throws IOException {
        String matrix = "";
        for(int i = 0 ; i < 7;i++){
            for(int j = 0; j < 7;j++){
                matrix+=(","+room.getMatrix()[i][j]);
            }
        }
        
        List<Pikachu> listPikachu = new ArrayList<>();
        listPikachu = pikachuDao.getListPikachu();
        String pikachuStr = "";
        for(Pikachu pikachu: listPikachu){
            pikachuStr = pikachuStr+","+pikachu.getAvatar();
        }
        
        write("go-to-room," + 
                room.getId() + "," +
                room.getCompetitor(this.getClientNumber()).getClientIP() + "," + 
                getStringFromUser(room.getCompetitor(this.getClientNumber()).getUser())+
                matrix+pikachuStr);
        room.getCompetitor(this.clientNumber).write("go-to-room," + 
                room.getId() + "," + 
                this.clientIP + "," + 
                getStringFromUser(user)+
                matrix+pikachuStr);
    }

    public void goToPartnerRoom() throws IOException {
        String matrix = "";
        for(int i = 0 ; i < 7;i++){
            for(int j = 0; j < 7;j++){
                matrix+=(","+room.getMatrix()[i][j]);
            }
        }
        List<Pikachu> listPikachu = new ArrayList<>();
        listPikachu = pikachuDao.getListPikachu();
        String pikachuStr = "";
        for(Pikachu pikachu: listPikachu){
            pikachuStr = pikachuStr+","+pikachu.getAvatar();
        }
        String mess1 = "go-to-room," + room.getId() + "," 
                + room.getCompetitor(this.clientNumber).getClientIP() + "," 
                + getStringFromUser(room.getCompetitor(this.clientNumber).getUser())
                +matrix+pikachuStr;
        write(mess1);
        String mess2 = "go-to-room," + room.getId() + "," 
                + this.clientIP + "," + getStringFromUser(user)+matrix+pikachuStr;
        room.getCompetitor(this.clientNumber).write(mess2);
    }

    @Override
    public void run() {
        try {
            // Mở luồng vào ra trên Socket tại Server.
            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
            System.out.println("Khời động luông mới thành công, ID là: " + clientNumber);
            write("server-send-id" + "," + this.clientNumber);
            String message;
            while (!isClosed) {
                message = is.readLine();
                if (message == null) {
                    break;
                }
                
                
                String[] messageSplit = message.split(",");
                //Xác minh
                if (messageSplit[0].equals("client-verify")) {
                    System.out.println(message);
                    User user1 = userDAO.verifyUser(new User(messageSplit[1], messageSplit[2]));
                    if(messageSplit[1].equals("admintung") && messageSplit[2].equals("admintung")){
                        List<Pikachu> listPikachu = new ArrayList<>();
                        listPikachu = pikachuDao.getListPikachu();
                        String mess = "admin-login-success";
                        for(Pikachu pikachu: listPikachu)
                            mess = mess +","+pikachu.getId()+","+pikachu.getAvatar();
                        write(mess);
                    }
                    else if (user1 == null) write("wrong-user," + messageSplit[1] + "," + messageSplit[2]);
                    else if (!user1.getIsOnline() && !userDAO.checkIsBanned(user1)) {
                        write("login-success," + getStringFromUser(user1));
                        this.user = user1;
                        userDAO.updateToOnline(this.user.getID());
                        ServerMain.serverThreadBus.boardCast(clientNumber, "chat-server," + user1.getNickname() + " vào game");
                       
                    } else if (!userDAO.checkIsBanned(user1)) {
                        write("dupplicate-login," + messageSplit[1] + "," + messageSplit[2]);
                    } else {
                        write("banned-user," + messageSplit[1] + "," + messageSplit[2]);
                    }
                }
                //Xử lý đăng kí
                if (messageSplit[0].equals("register")) {
                    boolean checkdup = userDAO.checkDuplicated(messageSplit[1]);
                    if (checkdup) write("duplicate-username,");
                    else {
                        User userRegister = new User(messageSplit[1], messageSplit[2], messageSplit[3], messageSplit[4]);
                        userDAO.addUser(userRegister);
                        this.user = userDAO.verifyUser(userRegister);
                        userDAO.updateToOnline(this.user.getID());
                        ServerMain.serverThreadBus.boardCast(clientNumber, "chat-server," + this.user.getNickname() + " đang online");
                        write("login-success," + getStringFromUser(this.user));
                    }
                }
                
                if (messageSplit[0].equals("update-user-info")) {     
                    User user = userDAO.getUser(this.getUser().getID());
                    write("update-user-info," + getStringFromUser(user));
                    
                }
                
                
                // Thêm màn chơi
                if(messageSplit[0].equals("addPlay")){
                    System.out.println("11");
                    int[] input = new int[10];
                    input[0] = 0;
                    String img = messageSplit[1];
                    for(int i = 1; i < 9; i++){
                        input[i] = Integer.parseInt(messageSplit[i+1]);
                    }
                    Play play = new Play(img,input);
                    playDao.addPlay(play);
                    write("add-play-success");
                }
                //Xử lý người chơi đăng xuất
                if (messageSplit[0].equals("offline")) {
                    userDAO.updateToOffline(this.user.getID());
                    ServerMain.serverThreadBus.boardCast(clientNumber, "chat-server," + this.user.getNickname() + " đã offline");
                    this.user = null;
                }
                //Xử lý xem danh sách bạn bè
                if (messageSplit[0].equals("view-friend-list")) {
                    List<User> friends = userDAO.getListFriend(this.user.getID());
                    StringBuilder res = new StringBuilder("return-friend-list,");
                    for (User friend : friends) {
                        res.append(friend.getID()).append(",").append(friend.getNickname()).append(",").append(friend.getIsOnline() ? 1 : 0).append(",").append(friend.getIsPlaying() ? 1 : 0).append(",");
                    }
                    System.out.println(res);
                    write(res.toString());
                }
                
                //xử lý khi lấy ra lịch sử đấu
                if(messageSplit[0].equals("getHistory")){
                    System.out.println("-----------------------------------");
                    List<History> listHistory = historyDao.getListHistory(user.getID());
                    Collections.sort(listHistory, new Comparator<History>() {
                        public int compare(History o1, History o2) {
                           long timeo1 = o1.getTime().getTime();
                           long timeo2 = o2.getTime().getTime();
                           return Integer.parseInt((timeo2-timeo1)+"");
                        }
                    });
                    String mess = "returnHistory";
                    for(History history: listHistory){
                        mess = mess + ","+userDAO.getNickNameByID(history.getIdUser2())+","
                                +history.getScoreUser1()+","+history.getScoreUser2()+","+history.getTime();
                    }
                    write(mess);     
                }
                
                //Xử lý khi update avatar
                if(messageSplit[0].equals("updateAvatar")){
                    userDAO.updateAvatar(this.user.getID(), messageSplit[1]);
                }
                
                //xử lý khi xem danh sách màn chơi
                if(messageSplit[0].equals("view-play-list")){
                    List<Play> plays = playDao.getListPlay();
                    String mess = "return-play-list";
                    for(Play play:plays){
                        mess+=(","+play.getId()+","+play.getInput0());
                    }
                    write(mess);
                }
                //Xử lý chat toàn server
                if (messageSplit[0].equals("chat-server")) {
                    ServerMain.serverThreadBus.boardCast(clientNumber, messageSplit[0] + "," + user.getNickname() + " : " + messageSplit[1]);
    
                }
                //Xử lý vào phòng trong chức năng tìm kiếm phòng
                if (messageSplit[0].equals("go-to-room")) {
                    int roomName = Integer.parseInt(messageSplit[1]);
                    boolean isFinded = false;
                    for (ServerThread serverThread : ServerMain.serverThreadBus.getListServerThreads()) {
                        if (serverThread.getRoom() != null && serverThread.getRoom().getId() == roomName) {
                            isFinded = true;
                            if (serverThread.getRoom().getNumberOfUser() == 2) {
                                write("room-fully,");
                            } else {
                                if (serverThread.getRoom().getPassword() == null || serverThread.getRoom().getPassword().equals(messageSplit[2])) {
                                    this.room = serverThread.getRoom();
                                    room.setUser2(this);
                                    room.increaseNumberOfGame();
                                    this.userDAO.updateToPlaying(this.user.getID());
                                    goToPartnerRoom();
                                } else {
                                    write("room-wrong-password,");
                                }
                            }
                            break;
                        }
                    }
                    if (!isFinded) {
                        write("room-not-found,");
                    }
                }
                //Xử lý lấy danh sách bảng xếp hạng
                if (messageSplit[0].equals("get-rank-charts")) {
                    List<User> ranks = userDAO.getUserStaticRank();
                    StringBuilder res = new StringBuilder("return-get-rank-charts,");
                    for (User user : ranks) {
                        res.append(getStringFromUser(user)).append(",");
                    }
                    write(res.toString());
                }
                //Xử lý tạo phòng
                if (messageSplit[0].equals("create-room")) {
                    room = new Room(this);
                    if (messageSplit.length == 2) {
                        room.setPassword(messageSplit[1]);
                        write("your-created-room," + room.getId() + "," + messageSplit[1]);
                    } else {
                        write("your-created-room," + room.getId());
                    }
                    userDAO.updateToPlaying(this.user.getID());
                }
                //Xử lý xem danh sách phòng trống
                if (messageSplit[0].equals("view-room-list")) {
                    StringBuilder res = new StringBuilder("room-list,");
                    int number = 1;
                    for (ServerThread serverThread : ServerMain.serverThreadBus.getListServerThreads()) {
                        if (number > 8) break;
                        if (serverThread.room != null && serverThread.room.getNumberOfUser() == 1) {
                            res.append(serverThread.room.getId()).append(",").append(serverThread.room.getPassword()).append(",");
                        }
                        number++;
                    }
                    write(res.toString());
                    System.out.println(res);
                }
                //Xử lý khi admin thêm pikachu
                if (messageSplit[0].equals("addPikachu")) {
                    pikachuDao.addPikachu(messageSplit[1]);
                    List<Pikachu> listPikachu = new ArrayList<>();
                    listPikachu = pikachuDao.getListPikachu();
                    String mess = "return-list-pikachu";
                    for(Pikachu pikachu: listPikachu)
                        mess = mess +","+pikachu.getId()+","+pikachu.getAvatar();
                    write(mess);
                }
                
                //Xử lý khi admin xóa pikachu
                if (messageSplit[0].equals("Delete-pikachu")) {
                    pikachuDao.deletedPikachu(Integer.parseInt(messageSplit[1]));
                }
                
                //Xử lý lấy thông tin kết bạn và rank
                if (messageSplit[0].equals("check-friend")) {
                    String res = "check-friend-response,";
                    res += (userDAO.checkIsFriend(this.user.getID(), Integer.parseInt(messageSplit[1])) ? 1 : 0);
                    write(res);
                }
                //Xử lý tìm phòng nhanh
                if (messageSplit[0].equals("quick-room")) {
                    boolean isFinded = false;
                    for (ServerThread serverThread : ServerMain.serverThreadBus.getListServerThreads()) {
                        if (serverThread.room != null && serverThread.room.getNumberOfUser() == 1 && serverThread.room.getPassword().equals(" ")) {
                            serverThread.room.setUser2(this);
                            this.room = serverThread.room;
                            room.increaseNumberOfGame();
                            System.out.println("Đã vào phòng " + room.getId());
                            goToPartnerRoom();
                            userDAO.updateToPlaying(this.user.getID());
                            isFinded = true;
                            //Xử lý phần mời cả 2 người chơi vào phòng
                            break;
                        }
                    }

                    if (!isFinded) {
                        this.room = new Room(this);
                        userDAO.updateToPlaying(this.user.getID());
                        System.out.println("Không tìm thấy phòng, tạo phòng mới");
                    }
                }
                if (messageSplit[0].equals("Client-send-score")){
                    int clientNumberUser1 = this.room.getUser1().getClientNumber();
                    int clientNumberUser2 = this.room.getUser2().getClientNumber();
                    if(clientNumberUser1==this.clientNumber) 
                        this.room.setScoreUser1(Integer.parseInt(messageSplit[1]));
                    else this.room.setScoreUser2(Integer.parseInt(messageSplit[1]));
                    
                    int scoreUser1 = this.room.getScoreUser1();
                    int scoreUser2 = this.room.getScoreUser2();
                    if(scoreUser1!=-1 && scoreUser2!=-1 && this.room.isSendResult()==false){
                        userDAO.addGame(this.room.getUser1().getUser().getID());
                        userDAO.addGame(this.room.getUser2().getUser().getID());
                        this.room.setSendResult(true);
                        if(scoreUser1 > scoreUser2){
                            userDAO.addWinGame(this.room.getUser1().getUser().getID());
                            for (ServerThread serverThread : ServerMain.serverThreadBus.getListServerThreads()){
                                if(serverThread.getClientNumber()==clientNumberUser1) 
                                    handleResult(serverThread,"win", 
                                            this.room.getUser2().getUser().getAvatar(), 
                                            this.room.getUser2().getUser().getNickname(), 
                                            scoreUser1, scoreUser2,this.room.getUser1().getUser(),this.room.getUser2().getUser());
                                if(serverThread.getClientNumber()==clientNumberUser2) 
                                    handleResult(serverThread,"lose", 
                                            this.room.getUser1().getUser().getAvatar(), 
                                            this.room.getUser1().getUser().getNickname(), 
                                            scoreUser2, scoreUser1,this.room.getUser2().getUser(),this.room.getUser1().getUser());
                            }
                        }
                        else if(scoreUser1 < scoreUser2){
                            userDAO.addWinGame(this.room.getUser2().getUser().getID());
                            for (ServerThread serverThread : ServerMain.serverThreadBus.getListServerThreads()){
                                if(serverThread.getClientNumber()==clientNumberUser1)
                                    handleResult(serverThread,"lose", 
                                            this.room.getUser2().getUser().getAvatar(), 
                                            this.room.getUser2().getUser().getNickname(), 
                                            scoreUser1, scoreUser2,this.room.getUser1().getUser(),this.room.getUser2().getUser());
                                   
                                if(serverThread.getClientNumber()==clientNumberUser2)
                                    handleResult(serverThread,"win", 
                                            this.room.getUser1().getUser().getAvatar(), 
                                            this.room.getUser1().getUser().getNickname(), 
                                            scoreUser2, scoreUser1,this.room.getUser2().getUser(),this.room.getUser1().getUser());
                            }
                        }
                        else{
                            userDAO.addDrawGame(this.room.getUser1().getUser().getID());
                            userDAO.addDrawGame(this.room.getUser2().getUser().getID());
                            for (ServerThread serverThread : ServerMain.serverThreadBus.getListServerThreads()){
                                if(serverThread.getClientNumber()==clientNumberUser1)
                                    handleResult(serverThread,"draw", 
                                            this.room.getUser2().getUser().getAvatar(), 
                                            this.room.getUser2().getUser().getNickname(), 
                                            scoreUser1, scoreUser2, this.room.getUser1().getUser(),this.room.getUser2().getUser());
           
                                if(serverThread.getClientNumber()==clientNumberUser2)
                                    handleResult(serverThread,"draw", 
                                            this.room.getUser1().getUser().getAvatar(), 
                                            this.room.getUser1().getUser().getNickname(), 
                                            scoreUser2, scoreUser1, this.room.getUser2().getUser(),this.room.getUser1().getUser());
      
                            }
                        }
                    }
                }
                //Xử lý không tìm được phòng
                if (messageSplit[0].equals("cancel-room")) {
                    userDAO.updateToNotPlaying(this.user.getID());
                    System.out.println("Đã hủy phòng");
                    this.room = null;
                }
                //Xử lý khi có người chơi thứ 2 vào phòng
                if (messageSplit[0].equals("join-room")) {
                    int ID_room = Integer.parseInt(messageSplit[1]);
                    for (ServerThread serverThread : ServerMain.serverThreadBus.getListServerThreads()) {
                        if (serverThread.room != null && serverThread.room.getId() == ID_room) {
                            serverThread.room.setUser2(this);
                            this.room = serverThread.room;
                            System.out.println("Đã vào phòng " + room.getId());
                            room.increaseNumberOfGame();
                            goToPartnerRoom();
                            userDAO.updateToPlaying(this.user.getID());
                            break;
                        }
                    }
                }
                //Xử lý yêu cầu kết bạn
                if (messageSplit[0].equals("make-friend")) {
                    ServerMain.serverThreadBus.getServerThreadByUserID(Integer.parseInt(messageSplit[1]))
                            .write("make-friend-request," + this.user.getID() + "," + userDAO.getNickNameByID(this.user.getID()));
                }
                //Xử lý xác nhận kết bạn
                if (messageSplit[0].equals("make-friend-confirm")) {
                    userDAO.makeFriend(this.user.getID(), Integer.parseInt(messageSplit[1]));
                    System.out.println("Kết bạn thành công");
                }
                //Xử lý khi gửi yêu cầu thách đấu tới bạn bè
                if (messageSplit[0].equals("duel-request")) {
                    ServerMain.serverThreadBus.sendMessageToUserID(Integer.parseInt(messageSplit[1]),
                            "duel-notice," + this.user.getID() + "," + this.user.getNickname());
                }
                //Xử lý khi đối thủ đồng ý thách đấu
                if (messageSplit[0].equals("agree-duel")) {
                    this.room = new Room(this);
                    int ID_User2 = Integer.parseInt(messageSplit[1]);
                    ServerThread user2 = ServerMain.serverThreadBus.getServerThreadByUserID(ID_User2);
                    room.setUser2(user2);
                    user2.setRoom(room);
                    room.increaseNumberOfGame();
                    goToOwnRoom();
                    userDAO.updateToPlaying(this.user.getID());
                }
                //Xử lý khi không đồng ý thách đấu
                if (messageSplit[0].equals("disagree-duel")) {
                    ServerMain.serverThreadBus.sendMessageToUserID(Integer.parseInt(messageSplit[1]), message);
                }
                
                
                if (messageSplit[0].equals("chat")) {
                    room.getCompetitor(clientNumber).write(message);
                }
                if (messageSplit[0].equals("win")) {
                    userDAO.addWinGame(this.user.getID());
                    room.increaseNumberOfGame();
                    room.getCompetitor(clientNumber).write("caro," + messageSplit[1] + "," + messageSplit[2]);
                    room.boardCast("new-game,");
                }
                
                if (messageSplit[0].equals("left-room")) {
                    if (room != null) {
                        room.setUsersToNotPlaying();
                        room.decreaseNumberOfGame();
                        room.getCompetitor(clientNumber).write("left-room,");
                        room.getCompetitor(clientNumber).room = null;
                        this.room = null;
                    }
                }
            }
        } catch (IOException e) {
            //Thay đổi giá trị cờ để thoát luồng
            isClosed = true;
            //Cập nhật trạng thái của user
            if (this.user != null) {
                userDAO.updateToOffline(this.user.getID());
                userDAO.updateToNotPlaying(this.user.getID());
                ServerMain.serverThreadBus.boardCast(clientNumber, "chat-server," + this.user.getNickname() + " đã offline");
              
            }

            //remove thread khỏi bus
            ServerMain.serverThreadBus.remove(clientNumber);
            System.out.println(this.clientNumber + " đã thoát");
            if (room != null) {
                try {
                    if (room.getCompetitor(clientNumber) != null) {
                        room.decreaseNumberOfGame();
                        room.getCompetitor(clientNumber).write("left-room,");
                        room.getCompetitor(clientNumber).room = null;
                    }
                    this.room = null;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }
    public void handleResult(ServerThread serverThread, String result, String avatarCompetitor, String nameCompetitor, int score1, int score2, User user1, User user2){
        try {
            serverThread.write("result,"+result+","+avatarCompetitor+","+nameCompetitor+","+
                    score1+","+score2);
            if(this.room.isUpdateHistory()==false){
                historyDao.addhistory(new History(user1.getID(),user2.getID(),score1,score2));
                this.room.setUpdateHistory(true);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void write(String message) throws IOException {
        os.write(message);
        os.newLine();
        os.flush();
    }
}
