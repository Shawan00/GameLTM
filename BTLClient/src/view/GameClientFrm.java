package view;
import view.*;
import controller.Client;
import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import model.User;

public class GameClientFrm extends javax.swing.JFrame {
    private JButton[] buttons = new JButton[16];
    private List<String> listCard;
    private JButton buttonClick = null;
    
    private final User competitor;

    //if changing it you will need to redesign icon
    private final int size = 15;
    private final Timer timer;
    private Integer second;
    private Integer minute;

    private final boolean isSending;
    private final boolean isListening;
    private final String competitorIP;    
    private int score;
    
    private int selectCard;
    
    public GameClientFrm(User competitor, int room_ID, String competitorIP, List<String> listCard) throws MalformedURLException, IOException {
        initComponents();
        this.listCard = listCard;
        this.competitor = competitor;
        this.competitorIP = competitorIP;
        this.selectCard = 0;

        initializeGameBoard();
        
        mypanel.setLayout(new GridLayout(0,3,10,10));
        competitorPanel.setLayout(new GridLayout(0,3,10,10));
        competitorPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        
        
        isSending = false;
        isListening = false;

        //init score
        score = 0;
        textScore.setText(score+"");

        this.setTitle("Game");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("assets/logo.png").getImage());
        this.setResizable(false);
        this.getContentPane().setLayout(null);

        //Setup UI
        playerLabel.setFont(new Font("Arial", Font.BOLD, 15));
        competitorLabel.setFont(new Font("Arial", Font.BOLD, 15));
        roomNameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        roomNameLabel.setAlignmentX(JLabel.CENTER);
        sendButton.setBackground(Color.white);
        sendButton.setIcon(new ImageIcon("assets/sendPlane.png"));
        playerNicknameValue.setText(Client.user.getNickname());
        playerNumberOfGameValue.setText(Integer.toString(Client.user.getNumberOfGame()));
        playerNumberOfWinValue.setText(Integer.toString(Client.user.getNumberOfWin()));
        playerButtonImage.setIcon(new ImageIcon("assets/game/" + Client.user.getAvatar() + ".jpg"));
        roomNameLabel.setText("Phòng: " + room_ID);
        competitorNicknameValue.setText(competitor.getNickname());
        competotorNumberOfGameValue.setText(Integer.toString(competitor.getNumberOfGame()));
        competitorNumberOfWinValue.setText(Integer.toString(competitor.getNumberOfWin()));
        countDownLabel.setVisible(false);
        messageTextArea.setEditable(false);
        
        URL url = new URL(Client.user.getAvatar());
        Image image = ImageIO.read(url);
        if (image != null) {
            Image scaledImage = image.getScaledInstance(playerButtonImage.getWidth(), playerButtonImage.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);
            playerButtonImage.setIcon(icon);
        } else {
            System.out.println("Ảnh không tải được từ URL");
        }
        
        url = new URL(this.competitor.getAvatar());
        image = ImageIO.read(url);
        if (image != null) {
            Image scaledImage = image.getScaledInstance(competotorButtonImage.getWidth(), competotorButtonImage.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);
            competotorButtonImage.setIcon(icon);
        } else {
            System.out.println("Ảnh không tải được từ URL");
        }
        
        
        //Setup timer
        second = 0;
        minute = 0;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = minute.toString();
                String temp1 = second.toString();
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                if (temp1.length() == 1) {
                    temp1 = "0" + temp1;
                }
                if (second == 0) {
                    countDownLabel.setText("Thời Gian:" + temp + ":" + temp1);
                    second = 0;
                    minute = 0;

                    while(selectCard<3){
                        ClickRandom();
                    }
                    if(selectCard == 3){
                        jLayeredPane.setEnabled(false);
                        try {
                            Client.socketHandle.write("Client-send-score,"+score);
                        } catch (IOException ex) {
                            Logger.getLogger(GameClientFrm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println(score);
                        stopTimer();
                    }

                    jLayeredPane.setEnabled(false);
                    

                } else {
                    countDownLabel.setText("Thời Gian:" + temp + ":" + temp1);
                    second--;
                    if(selectCard == 3){
                        jLayeredPane.setEnabled(false);
                        try {
                            Client.socketHandle.write("Client-send-score,"+score);
                        } catch (IOException ex) {
                            Logger.getLogger(GameClientFrm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println(score);
                        stopTimer();
                    }
                    
                }

            }

        });
        startTimer();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    exitGame();
                } catch (IOException ex) {
                    Logger.getLogger(GameClientFrm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }
    
    private void ClickRandom(){
        List<JButton> visibleButtons = new ArrayList<>();
        for (JButton button : buttons) {
            if (button.isVisible()) {
                visibleButtons.add(button);
            }
        }

        if (!visibleButtons.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(visibleButtons.size()); 
            JButton randomVisibleCard = visibleButtons.get(randomIndex);
            randomVisibleCard.doClick(); 
        }
    }

    private void initializeGameBoard() throws IOException {
        jLayeredPane.setPreferredSize(new Dimension(720, 240));
        JPanel gridPanel = new JPanel(new GridLayout(2, 8));
        gridPanel.setBounds(0, 0, 960, 360);
        gridPanel.setOpaque(false);
        jLayeredPane.add(gridPanel, JLayeredPane.DEFAULT_LAYER);
        for(int i = 0 ; i < listCard.size();i++){
            JButton button = new JButton();
            button.putClientProperty("value", String.valueOf(listCard.get(i)));
            File file = new File("assets/Card/"+"sub.png");
            Image image = ImageIO.read(file);
            if (image != null) {
                Image scaledImage = image.getScaledInstance(120,180, Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(scaledImage);
                button.setIcon(icon);
            } else {
                System.out.println("Ảnh không tải được từ file");
            }
            button.addActionListener(new ButtonClickListener(i));
            buttons[i] = button;
            gridPanel.add(button);
        }
        
        
    }

    public void exitGame() throws IOException {
        try {
            timer.stop();
            Client.socketHandle.write("left-room,");
            Client.closeAllViews();
            Client.openView(Client.View.HOMEPAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
        Client.closeAllViews();
        Client.openView(Client.View.HOMEPAGE);
    }

    public void stopAllThread() {
        timer.stop();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jFrame4 = new javax.swing.JFrame();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        roomNameLabel = new javax.swing.JLabel();
        jLayeredPane = new javax.swing.JLayeredPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        playerLabel = new javax.swing.JLabel();
        playerButtonImage = new javax.swing.JLabel();
        playerNicknameValue = new javax.swing.JLabel();
        playerNumberOfGameLabel = new javax.swing.JLabel();
        playerNumberOfGameValue = new javax.swing.JLabel();
        playerNumberOfWinLabel = new javax.swing.JLabel();
        playerNumberOfWinValue = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageTextArea = new javax.swing.JTextArea();
        messageTextField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        textScore = new javax.swing.JTextField();
        mypanel = new javax.swing.JPanel();
        competitorPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        competitorLabel = new javax.swing.JLabel();
        competitorNicknameValue = new javax.swing.JLabel();
        competotorNumberOfGameLabel = new javax.swing.JLabel();
        competotorNumberOfGameValue = new javax.swing.JLabel();
        competitorNumberOfWinLabel = new javax.swing.JLabel();
        competitorNumberOfWinValue = new javax.swing.JLabel();
        competotorButtonImage = new javax.swing.JButton();
        countDownLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mainMenu = new javax.swing.JMenu();
        newGameMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame4Layout = new javax.swing.GroupLayout(jFrame4.getContentPane());
        jFrame4.getContentPane().setLayout(jFrame4Layout);
        jFrame4Layout.setHorizontalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame4Layout.setVerticalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);

        jPanel4.setBackground(new java.awt.Color(0, 102, 51));

        roomNameLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        roomNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        roomNameLabel.setText("{Tên Phòng}");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roomNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roomNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jLayeredPane.setPreferredSize(new java.awt.Dimension(650, 650));

        javax.swing.GroupLayout jLayeredPaneLayout = new javax.swing.GroupLayout(jLayeredPane);
        jLayeredPane.setLayout(jLayeredPaneLayout);
        jLayeredPaneLayout.setHorizontalGroup(
            jLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        jLayeredPaneLayout.setVerticalGroup(
            jLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(245, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setForeground(new java.awt.Color(0, 204, 204));

        playerLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        playerLabel.setForeground(new java.awt.Color(255, 255, 255));
        playerLabel.setText("Bạn");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(playerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(playerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        playerButtonImage.setBackground(new java.awt.Color(102, 102, 102));

        playerNicknameValue.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        playerNicknameValue.setText("//Tên");

        playerNumberOfGameLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        playerNumberOfGameLabel.setText("Số trận chơi");

        playerNumberOfGameValue.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        playerNumberOfGameValue.setText("{sovanchoi}");

        playerNumberOfWinLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        playerNumberOfWinLabel.setText("Số trận thắng");

        playerNumberOfWinValue.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        playerNumberOfWinValue.setText("{sovanthang}");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(playerButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(playerNicknameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(playerNumberOfGameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(playerNumberOfWinLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(playerNumberOfGameValue)
                            .addComponent(playerNumberOfWinValue, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(playerButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(playerNicknameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerNumberOfGameLabel)
                    .addComponent(playerNumberOfGameValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerNumberOfWinLabel)
                    .addComponent(playerNumberOfWinValue))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        messageTextArea.setBackground(new java.awt.Color(250, 250, 250));
        messageTextArea.setColumns(20);
        messageTextArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        messageTextArea.setRows(5);
        jScrollPane1.setViewportView(messageTextArea);

        messageTextField.setBackground(new java.awt.Color(250, 250, 250));
        messageTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        messageTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                messageTextFieldKeyPressed(evt);
            }
        });

        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        jLabel1.setText("SCORE:");

        textScore.setEditable(false);
        textScore.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        textScore.setText("0");

        mypanel.setPreferredSize(new java.awt.Dimension(320, 150));

        javax.swing.GroupLayout mypanelLayout = new javax.swing.GroupLayout(mypanel);
        mypanel.setLayout(mypanelLayout);
        mypanelLayout.setHorizontalGroup(
            mypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        mypanelLayout.setVerticalGroup(
            mypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        competitorPanel.setPreferredSize(new java.awt.Dimension(320, 150));

        javax.swing.GroupLayout competitorPanelLayout = new javax.swing.GroupLayout(competitorPanel);
        competitorPanel.setLayout(competitorPanelLayout);
        competitorPanelLayout.setHorizontalGroup(
            competitorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        competitorPanelLayout.setVerticalGroup(
            competitorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 240, 255));

        jPanel3.setBackground(new java.awt.Color(102, 0, 102));
        jPanel3.setForeground(new java.awt.Color(102, 102, 102));

        competitorLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        competitorLabel.setForeground(new java.awt.Color(255, 255, 255));
        competitorLabel.setText("Đối thủ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(competitorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(competitorLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        competitorNicknameValue.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        competitorNicknameValue.setText("//tên");

        competotorNumberOfGameLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        competotorNumberOfGameLabel.setText("Số trận chơi");

        competotorNumberOfGameValue.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        competotorNumberOfGameValue.setText("{sovanchoi}");

        competitorNumberOfWinLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        competitorNumberOfWinLabel.setText("Số trận thắng");

        competitorNumberOfWinValue.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        competitorNumberOfWinValue.setText("{sovanthang}");

        competotorButtonImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                competotorButtonImageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(competotorButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(competitorNicknameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(competitorNumberOfWinLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(competotorNumberOfGameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(competitorNumberOfWinValue, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(competotorNumberOfGameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 73, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(competotorButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(competitorNicknameValue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(competotorNumberOfGameLabel)
                            .addComponent(competotorNumberOfGameValue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(competitorNumberOfWinLabel)
                            .addComponent(competitorNumberOfWinValue))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        countDownLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        countDownLabel.setForeground(new java.awt.Color(255, 0, 0));
        countDownLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        countDownLabel.setText("Thời gian:00:60");

        mainMenu.setText("Menu");
        mainMenu.setToolTipText("");

        newGameMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        newGameMenuItem.setText("Game mới");
        newGameMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameMenuItemActionPerformed(evt);
            }
        });
        mainMenu.add(newGameMenuItem);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        exitMenuItem.setText("Thoát");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        mainMenu.add(exitMenuItem);

        jMenuBar1.add(mainMenu);

        helpMenu.setText("Help");
        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textScore, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35)
                                        .addComponent(countDownLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(mypanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(messageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jLayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 960, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(255, 255, 255)
                                .addComponent(competitorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(textScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(countDownLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(mypanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(messageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(32, 32, 32))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jLayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(competitorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLayeredPane.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newGameMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameMenuItemActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Thông báo", "Tính năng đang được phát triển", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_newGameMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        try {
            exitGame();
        } catch (IOException ex) {
            Logger.getLogger(GameClientFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        try {
            if (messageTextField.getText().isEmpty()) {
                throw new Exception("Vui lòng nhập nội dung tin nhắn");
            }
            String temp = messageTextArea.getText();
            temp += "Tôi: " + messageTextField.getText() + "\n";
            messageTextArea.setText(temp);
            Client.socketHandle.write("chat," + messageTextField.getText());
            messageTextField.setText("");
            messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_sendButtonActionPerformed

    private void competotorButtonImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_competotorButtonImageActionPerformed

        Client.openView(Client.View.COMPETITOR_INFO, competitor);

    }//GEN-LAST:event_competotorButtonImageActionPerformed

    private void messageTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageTextFieldKeyPressed
        if (evt.getKeyCode() == 10) {
            try {
                if (messageTextField.getText().isEmpty()) {
                    return;
                }
                String temp = messageTextArea.getText();
                temp += "Tôi: " + messageTextField.getText() + "\n";
                messageTextArea.setText(temp);
                Client.socketHandle.write("chat," + messageTextField.getText());
                messageTextField.setText("");
                messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }
    }//GEN-LAST:event_messageTextFieldKeyPressed

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(rootPane, message);
    }

    public void stopTimer() {
        timer.stop();
    }

    int not(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 0) {
            return 1;
        }
        return 0;
    }

    public void startTimer() {
        countDownLabel.setVisible(true);
        second = 30;
        minute = 0;
        timer.start();
    }

    public void addMessage(String message) {
        String temp = messageTextArea.getText();
        temp += competitor.getNickname() + ": " + message + "\n";
        messageTextArea.setText(temp);
        messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
    }


    
  
    private int getMax(byte[] bytes) {
        int max = bytes[0];
        for (int i = 1; i < bytes.length; i++) {
            if (bytes[i] > max) max = bytes[i];
        }
        return max;
    }

    public void updateNumberOfGame() {
        competitor.setNumberOfGame(competitor.getNumberOfGame() + 1);
        competotorNumberOfGameValue.setText(Integer.toString(competitor.getNumberOfGame()));
        Client.user.setNumberOfGame(Client.user.getNumberOfGame() + 1);
        playerNumberOfGameValue.setText(Integer.toString(Client.user.getNumberOfGame()));
    }
    
    public void updateAfterCompetitorSelect(String card) throws IOException{
        for(int i = 0 ; i < buttons.length;i++)
            if(buttons[i].getClientProperty("value").equals(card))
                buttons[i].setVisible(false);
        
        File file = new File("assets/Card/"+card+".png");
        Image image = ImageIO.read(file);
        JLabel label = null;
        if (image != null) {
            Image scaledImage = image.getScaledInstance(100,150, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);

            label = new JLabel(icon);
            label.setHorizontalAlignment(SwingConstants.CENTER);  

        } else {
            System.out.println("Ảnh không tải được từ file");
        }
        competitorPanel.add(label);
        
    }
    
    private class ButtonClickListener implements ActionListener {
        private int position;

        public ButtonClickListener(int i) {
            this.position = i;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(selectCard <=2){
                try {            
                    selectCard++;
                    JButton clickedButton = buttons[position];
                    String value = clickedButton.getClientProperty("value")+"";
                    Client.socketHandle.write("client-send-card-selected,"+value);
                    buttons[position].setVisible(false);

                    File file = new File("assets/Card/"+value+".png");
                    Image image = ImageIO.read(file);
                    JLabel label = null;
                    if (image != null) {
                        Image scaledImage = image.getScaledInstance(100,150, Image.SCALE_SMOOTH);
                        ImageIcon icon = new ImageIcon(scaledImage);

                        label = new JLabel(icon);
                        label.setHorizontalAlignment(SwingConstants.CENTER);  

                    } else {
                        System.out.println("Ảnh không tải được từ file");
                    }
                    mypanel.add(label);

                    score+=Integer.parseInt(value.toCharArray()[0]+"");
                    score%=10;
                    textScore.setText(score+"");
                } catch (IOException ex) {
                    Logger.getLogger(GameClientFrm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
        
            
        }

        
    }
    




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel competitorLabel;
    private javax.swing.JLabel competitorNicknameValue;
    private javax.swing.JLabel competitorNumberOfWinLabel;
    private javax.swing.JLabel competitorNumberOfWinValue;
    private javax.swing.JPanel competitorPanel;
    private javax.swing.JButton competotorButtonImage;
    private javax.swing.JLabel competotorNumberOfGameLabel;
    private javax.swing.JLabel competotorNumberOfGameValue;
    private javax.swing.JLabel countDownLabel;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JFrame jFrame4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLayeredPane jLayeredPane;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu mainMenu;
    private javax.swing.JTextArea messageTextArea;
    private javax.swing.JTextField messageTextField;
    private javax.swing.JPanel mypanel;
    private javax.swing.JMenuItem newGameMenuItem;
    private javax.swing.JLabel playerButtonImage;
    private javax.swing.JLabel playerLabel;
    private javax.swing.JLabel playerNicknameValue;
    private javax.swing.JLabel playerNumberOfGameLabel;
    private javax.swing.JLabel playerNumberOfGameValue;
    private javax.swing.JLabel playerNumberOfWinLabel;
    private javax.swing.JLabel playerNumberOfWinValue;
    private javax.swing.JLabel roomNameLabel;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextField textScore;
    // End of variables declaration//GEN-END:variables


}
