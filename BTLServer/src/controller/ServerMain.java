package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import dao.UserDAO;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerMain {

    public static volatile ServerThreadBus serverThreadBus;
    public static Socket socketOfServer;
    public static int ROOM_ID;


    public static void main(String[] args) {
        ServerSocket listener = null;
        serverThreadBus = new ServerThreadBus();
        System.out.println("Server is waiting to accept user...");
        int clientNumber = 0;
        ROOM_ID = 1;

        try {
            listener = new ServerSocket(7777);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                100,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8)
        );
        try {
            while (true) {
                socketOfServer = listener.accept();
                System.out.println(socketOfServer.getInetAddress().getHostAddress());
                ServerThread serverThread = new ServerThread(socketOfServer, clientNumber++);
                serverThreadBus.add(serverThread);
                System.out.println("Số thread đang chạy là: " + serverThreadBus.getLength());
                executor.execute(serverThread);
            }
        } catch (IOException ex) {
            for(ServerThread serverThread:serverThreadBus.getListServerThreads()){
                    UserDAO userDAO = new UserDAO();
                    userDAO.updateToOffline(serverThread.getUser().getID());
                }
            ex.printStackTrace();
        } finally {
            try {
                for(ServerThread serverThread:serverThreadBus.getListServerThreads()){
                    UserDAO userDAO = new UserDAO();
                    userDAO.updateToOffline(serverThread.getUser().getID());
                }
                listener.close();
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
