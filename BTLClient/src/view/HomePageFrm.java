package view;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import controller.Client;
import java.awt.Image;

import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
public class HomePageFrm extends javax.swing.JFrame {

    public HomePageFrm() throws MalformedURLException, IOException {
        initComponents();
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf"); 
            SwingUtilities.updateComponentTreeUI(this);  
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setTitle("Home");
        this.setIconImage(new ImageIcon("assets/logo.png").getImage());
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        jLabel4.setText(Client.user.getNickname());
        numberOfWinValue.setText(Integer.toString(Client.user.getNumberOfWin()));
        numberOfGameValue.setText(Integer.toString(Client.user.getNumberOfGame()));
        URL url = new URL(Client.user.getAvatar());
        Image image = ImageIO.read(url);
        if (image != null) {
            // Điều chỉnh kích thước ảnh phù hợp với JLabel
            Image scaledImage = image.getScaledInstance(jLabel8.getWidth(), jLabel8.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);
            jLabel8.setIcon(icon);
        } else {
            System.out.println("Ảnh không tải được từ URL");
        }
        messageTextArea.setEditable(false);
        if (Client.user.getNumberOfGame() == 0) {
            winRatioValue.setText("-");
        } else {
            winRatioValue.setText(String.format("%.2f", (float) Client.user.getNumberOfWin() / Client.user.getNumberOfGame() * 100) + "%");
        }
        drawValue.setText("" + Client.user.getNumberOfDraw());
        markValue.setText("" + (Client.user.getNumberOfDraw() + Client.user.getNumberOfWin() * 3));
        rankValue.setText("" + Client.user.getRank());
    }
    
    public void updateUserInfo(){
        numberOfWinValue.setText(Integer.toString(Client.user.getNumberOfWin()));
        numberOfGameValue.setText(Integer.toString(Client.user.getNumberOfGame()));
        drawValue.setText("" + Client.user.getNumberOfDraw());
        markValue.setText("" + (Client.user.getNumberOfDraw() + Client.user.getNumberOfWin() * 3));
        rankValue.setText("" + Client.user.getRank());
        if (Client.user.getNumberOfGame() == 0) {
            winRatioValue.setText("-");
        } else {
            winRatioValue.setText(String.format("%.2f", (float) Client.user.getNumberOfWin() / Client.user.getNumberOfGame() * 100) + "%");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jFileChooser1 = new javax.swing.JFileChooser();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel2 = new javax.swing.JPanel();
        goRoomButton = new javax.swing.JButton();
        createRoomButton = new javax.swing.JButton();
        scoreBotButton = new javax.swing.JButton();
        friendListButton = new javax.swing.JButton();
        exitGameButton = new javax.swing.JButton();
        findRoomButton = new javax.swing.JButton();
        quickGameButton = new javax.swing.JButton();
        scoreBoardButton = new javax.swing.JButton();
        historyButton = new javax.swing.JButton();
        listUserButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        numberOfWinLabel = new javax.swing.JLabel();
        numberOfWinValue = new javax.swing.JLabel();
        numberOfGameValue = new javax.swing.JLabel();
        numberOfGameLabel = new javax.swing.JLabel();
        markLabel = new javax.swing.JLabel();
        markValue = new javax.swing.JLabel();
        rankLabel = new javax.swing.JLabel();
        rankValue = new javax.swing.JLabel();
        winRatioLabel = new javax.swing.JLabel();
        winRatioValue = new javax.swing.JLabel();
        drawLabel = new javax.swing.JLabel();
        drawValue = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageTextArea = new javax.swing.JTextArea();
        messageInput = new javax.swing.JTextField();
        sendMessageButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1009, 1009));
        setPreferredSize(new java.awt.Dimension(920, 665));
        setSize(new java.awt.Dimension(920, 665));

        jPanel2.setMaximumSize(new java.awt.Dimension(1009, 627));
        jPanel2.setPreferredSize(new java.awt.Dimension(910, 627));
        jPanel2.setLayout(null);

        goRoomButton.setBackground(new java.awt.Color(102, 102, 102));
        goRoomButton.setFont(new java.awt.Font("FVF Fernando 08", 0, 16)); // NOI18N
        goRoomButton.setForeground(new java.awt.Color(255, 255, 255));
        goRoomButton.setText("Vào phòng");
        goRoomButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        goRoomButton.setMaximumSize(new java.awt.Dimension(250, 60));
        goRoomButton.setMinimumSize(new java.awt.Dimension(250, 60));
        goRoomButton.setOpaque(true);
        goRoomButton.setPreferredSize(new java.awt.Dimension(250, 60));
        goRoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goRoomButtonActionPerformed(evt);
            }
        });
        jPanel2.add(goRoomButton);
        goRoomButton.setBounds(350, 270, 250, 60);

        createRoomButton.setBackground(new java.awt.Color(102, 102, 102));
        createRoomButton.setFont(new java.awt.Font("FVF Fernando 08", 0, 16)); // NOI18N
        createRoomButton.setForeground(new java.awt.Color(255, 255, 255));
        createRoomButton.setText("Tạo Phòng");
        createRoomButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        createRoomButton.setMaximumSize(new java.awt.Dimension(250, 60));
        createRoomButton.setMinimumSize(new java.awt.Dimension(250, 60));
        createRoomButton.setOpaque(true);
        createRoomButton.setPreferredSize(new java.awt.Dimension(250, 60));
        createRoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createRoomButtonActionPerformed(evt);
            }
        });
        jPanel2.add(createRoomButton);
        createRoomButton.setBounds(350, 180, 250, 60);

        scoreBotButton.setFont(new java.awt.Font("FVF Fernando 08", 1, 14)); // NOI18N
        scoreBotButton.setText("Đăng xuất");
        scoreBotButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        scoreBotButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scoreBotButtonActionPerformed(evt);
            }
        });
        jPanel2.add(scoreBotButton);
        scoreBotButton.setBounds(450, 550, 128, 38);

        friendListButton.setBackground(new java.awt.Color(102, 102, 102));
        friendListButton.setFont(new java.awt.Font("FVF Fernando 08", 0, 16)); // NOI18N
        friendListButton.setForeground(new java.awt.Color(255, 255, 255));
        friendListButton.setText("Bạn bè");
        friendListButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        friendListButton.setMaximumSize(new java.awt.Dimension(250, 60));
        friendListButton.setMinimumSize(new java.awt.Dimension(250, 60));
        friendListButton.setOpaque(true);
        friendListButton.setPreferredSize(new java.awt.Dimension(250, 60));
        friendListButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                friendListButtonActionPerformed(evt);
            }
        });
        jPanel2.add(friendListButton);
        friendListButton.setBounds(40, 360, 250, 60);

        exitGameButton.setBackground(new java.awt.Color(204, 204, 204));
        exitGameButton.setFont(new java.awt.Font("FVF Fernando 08", 1, 14)); // NOI18N
        exitGameButton.setText("Thoát Game");
        exitGameButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitGameButtonActionPerformed(evt);
            }
        });
        jPanel2.add(exitGameButton);
        exitGameButton.setBounds(250, 550, 160, 38);

        findRoomButton.setBackground(new java.awt.Color(102, 102, 102));
        findRoomButton.setFont(new java.awt.Font("FVF Fernando 08", 0, 16)); // NOI18N
        findRoomButton.setForeground(new java.awt.Color(255, 255, 255));
        findRoomButton.setText("Tìm phòng");
        findRoomButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        findRoomButton.setMaximumSize(new java.awt.Dimension(250, 60));
        findRoomButton.setMinimumSize(new java.awt.Dimension(250, 60));
        findRoomButton.setOpaque(true);
        findRoomButton.setPreferredSize(new java.awt.Dimension(250, 60));
        findRoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findRoomButtonActionPerformed(evt);
            }
        });
        jPanel2.add(findRoomButton);
        findRoomButton.setBounds(40, 270, 250, 60);

        quickGameButton.setBackground(new java.awt.Color(102, 102, 102));
        quickGameButton.setFont(new java.awt.Font("FVF Fernando 08", 0, 16)); // NOI18N
        quickGameButton.setForeground(new java.awt.Color(255, 255, 255));
        quickGameButton.setText("Chơi nhanh");
        quickGameButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        quickGameButton.setMaximumSize(new java.awt.Dimension(250, 60));
        quickGameButton.setMinimumSize(new java.awt.Dimension(250, 60));
        quickGameButton.setOpaque(true);
        quickGameButton.setPreferredSize(new java.awt.Dimension(250, 60));
        quickGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quickGameButtonActionPerformed(evt);
            }
        });
        jPanel2.add(quickGameButton);
        quickGameButton.setBounds(40, 180, 250, 60);

        scoreBoardButton.setBackground(new java.awt.Color(102, 102, 102));
        scoreBoardButton.setFont(new java.awt.Font("FVF Fernando 08", 0, 16)); // NOI18N
        scoreBoardButton.setForeground(new java.awt.Color(255, 255, 255));
        scoreBoardButton.setText("Bảng xếp hạng");
        scoreBoardButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        scoreBoardButton.setMaximumSize(new java.awt.Dimension(250, 60));
        scoreBoardButton.setMinimumSize(new java.awt.Dimension(250, 60));
        scoreBoardButton.setOpaque(true);
        scoreBoardButton.setPreferredSize(new java.awt.Dimension(250, 60));
        scoreBoardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scoreBoardButtonActionPerformed(evt);
            }
        });
        jPanel2.add(scoreBoardButton);
        scoreBoardButton.setBounds(40, 450, 250, 60);

        historyButton.setBackground(new java.awt.Color(102, 102, 102));
        historyButton.setFont(new java.awt.Font("FVF Fernando 08", 0, 16)); // NOI18N
        historyButton.setForeground(new java.awt.Color(255, 255, 255));
        historyButton.setText("Lịch sử đấu");
        historyButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        historyButton.setMaximumSize(new java.awt.Dimension(250, 60));
        historyButton.setMinimumSize(new java.awt.Dimension(250, 60));
        historyButton.setOpaque(true);
        historyButton.setPreferredSize(new java.awt.Dimension(250, 60));
        historyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyButtonActionPerformed(evt);
            }
        });
        jPanel2.add(historyButton);
        historyButton.setBounds(350, 450, 250, 60);

        listUserButton.setBackground(new java.awt.Color(102, 102, 102));
        listUserButton.setFont(new java.awt.Font("FVF Fernando 08", 0, 16)); // NOI18N
        listUserButton.setForeground(new java.awt.Color(255, 255, 255));
        listUserButton.setText("Danh sách người chơi");
        listUserButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        listUserButton.setMaximumSize(new java.awt.Dimension(250, 60));
        listUserButton.setMinimumSize(new java.awt.Dimension(250, 60));
        listUserButton.setOpaque(true);
        listUserButton.setPreferredSize(new java.awt.Dimension(250, 60));
        jPanel2.add(listUserButton);
        listUserButton.setBounds(350, 360, 250, 60);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("FVF Fernando 08", 1, 18)); // NOI18N
        jLabel4.setText("Input tên");

        numberOfWinLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        numberOfWinLabel.setText("Số trận thắng");

        numberOfWinValue.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        numberOfWinValue.setText("{day la so van thang}");

        numberOfGameValue.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        numberOfGameValue.setText("{day la so van da choi}");

        numberOfGameLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        numberOfGameLabel.setText("Số trận");

        markLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        markLabel.setText("Điểm");

        markValue.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        markValue.setText("{day la diem}");

        rankLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        rankLabel.setText("Xếp hạng");

        rankValue.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        rankValue.setText("{day la thu hang}");

        winRatioLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        winRatioLabel.setText("Tỉ lệ thắng");

        winRatioValue.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        winRatioValue.setText("{day la ti le thang}");

        drawLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        drawLabel.setText("Số trận hòa");

        drawValue.setFont(new java.awt.Font("FVF Fernando 08", 0, 12)); // NOI18N
        drawValue.setText("{day la so van hoa}");

        jLabel8.setBackground(new java.awt.Color(204, 204, 204));
        jLabel8.setFont(new java.awt.Font("FVF Fernando 08", 1, 14)); // NOI18N

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setFont(new java.awt.Font("FVF Fernando 08", 0, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Đổi Avatar");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(drawLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(winRatioLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rankLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(markLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(markValue, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(winRatioValue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                .addComponent(drawValue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(rankValue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(numberOfGameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numberOfWinLabel, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(numberOfWinValue, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numberOfGameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)))
                .addGap(0, 24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberOfGameLabel)
                    .addComponent(numberOfGameValue))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberOfWinValue)
                    .addComponent(numberOfWinLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(drawLabel)
                    .addComponent(drawValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(winRatioLabel)
                    .addComponent(winRatioValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(markValue)
                    .addComponent(markLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rankLabel)
                    .addComponent(rankValue))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel1);
        jPanel1.setBounds(630, 0, 270, 410);

        messageTextArea.setBackground(new java.awt.Color(250, 250, 250));
        messageTextArea.setColumns(20);
        messageTextArea.setRows(5);
        jScrollPane1.setViewportView(messageTextArea);

        messageInput.setMinimumSize(new java.awt.Dimension(66, 22));
        messageInput.setPreferredSize(new java.awt.Dimension(66, 22));
        messageInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                messageInputKeyPressed(evt);
            }
        });

        sendMessageButton.setBackground(new java.awt.Color(245, 241, 241));
        sendMessageButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/sendPlane.png"))); // NOI18N
        sendMessageButton.setBorderPainted(false);
        sendMessageButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sendMessageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessageButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(messageInput, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(sendMessageButton, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(174, 174, 174)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(messageInput, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sendMessageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel3);
        jPanel3.setBounds(630, 410, 272, 220);

        jLabel3.setFont(new java.awt.Font("FVF Fernando 08", 1, 14)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/CARD GAME.png"))); // NOI18N
        jLabel3.setMaximumSize(new java.awt.Dimension(270, 60));
        jLabel3.setMinimumSize(new java.awt.Dimension(270, 60));
        jLabel3.setPreferredSize(new java.awt.Dimension(270, 60));
        jPanel2.add(jLabel3);
        jLabel3.setBounds(0, 0, 627, 627);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 897, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createRoomButtonActionPerformed
        int res = JOptionPane.showConfirmDialog(rootPane, "Bạn có muốn đặt mật khẩu cho phòng không?", "Tạo phòng", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
//            Client.closeView(Client.View.HOMEPAGE);
            try {
                Client.closeAllViews();
                Client.openView(Client.View.CREATE_ROOM_PASSWORD);
            } catch (IOException ex) {
                Logger.getLogger(HomePageFrm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (res == JOptionPane.NO_OPTION) {
            try {
                Client.socketHandle.write("create-room,");
//                Client.closeView(Client.View.HOMEPAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }
    }//GEN-LAST:event_createRoomButtonActionPerformed

    private void findRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findRoomButtonActionPerformed
        try {
            Client.closeView(Client.View.HOMEPAGE);
            Client.openView(Client.View.ROOM_LIST);
            Client.socketHandle.write("view-room-list,");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_findRoomButtonActionPerformed

    private void scoreBoardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scoreBoardButtonActionPerformed
        try {
            Client.openView(Client.View.RANK);
        } catch (IOException ex) {
            Logger.getLogger(HomePageFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_scoreBoardButtonActionPerformed

    private void scoreBotButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scoreBotButtonActionPerformed
        try {
            Client.socketHandle.write("offline," + Client.user.getID());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
        Client.closeView(Client.View.HOMEPAGE);
        try {
            Client.openView(Client.View.LOGIN);
        } catch (IOException ex) {
            Logger.getLogger(HomePageFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_scoreBotButtonActionPerformed

    private void exitGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitGameButtonActionPerformed
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_exitGameButtonActionPerformed

    private void friendListButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_friendListButtonActionPerformed
        Client.closeView(Client.View.HOMEPAGE);
        try {
            Client.openView(Client.View.FRIEND_LIST);
        } catch (IOException ex) {
            Logger.getLogger(HomePageFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_friendListButtonActionPerformed

    private void quickGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quickGameButtonActionPerformed
        Client.closeView(Client.View.HOMEPAGE);
        try {
            Client.openView(Client.View.FIND_ROOM);
        } catch (IOException ex) {
            Logger.getLogger(HomePageFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_quickGameButtonActionPerformed

    private void goRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goRoomButtonActionPerformed
        try {
            Client.openView(Client.View.ROOM_NAME_FRM);
        } catch (IOException ex) {
            Logger.getLogger(HomePageFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_goRoomButtonActionPerformed

    private void sendMessageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMessageButtonActionPerformed
        sendMessage();
    }//GEN-LAST:event_sendMessageButtonActionPerformed

    private void messageInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageInputKeyPressed
        if (evt.getKeyCode() == 10) {
            sendMessage();
        }
    }//GEN-LAST:event_messageInputKeyPressed

    private void historyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyButtonActionPerformed
        try {
            Client.socketHandle.write("getHistory,");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_historyButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "ddr3axv38",
            "api_key", "147594271812811",
            "api_secret", "KlZCzS0-PsCdv2TdvJf6Zu-pk-I"
        ));
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = fileChooser.showOpenDialog(this);
        if(returnValue==JFileChooser.APPROVE_OPTION){
            try {
                File file = fileChooser.getSelectedFile();
                Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
                String urlStr = (String) uploadResult.get("url");
                URL url = new URL(urlStr);
                Client.user.setAvatar(urlStr);
                Client.socketHandle.write("updateAvatar,"+url);
                Image image = ImageIO.read(url);
                Image scaledImage = image.getScaledInstance(jLabel8.getWidth(), jLabel8.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(scaledImage);
                jLabel8.setIcon(icon);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void sendMessage() {
        try {
            if (messageInput.getText().isEmpty()) {
                throw new Exception("Vui lòng nhập nội dung tin nhắn");
            }
            String temp = messageTextArea.getText();
            temp += "Tôi: " + messageInput.getText() + "\n";
            messageTextArea.setText(temp);
            Client.socketHandle.write("chat-server," + messageInput.getText());
            messageInput.setText("");
            messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }

    public void addMessage(String message) {
        String temp = messageTextArea.getText();
        temp += message + "\n";
        messageTextArea.setText(temp);
        messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createRoomButton;
    private javax.swing.JLabel drawLabel;
    private javax.swing.JLabel drawValue;
    private javax.swing.JButton exitGameButton;
    private javax.swing.JButton findRoomButton;
    private javax.swing.JButton friendListButton;
    private javax.swing.JButton goRoomButton;
    private javax.swing.JButton historyButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton listUserButton;
    private javax.swing.JLabel markLabel;
    private javax.swing.JLabel markValue;
    private javax.swing.JTextField messageInput;
    private javax.swing.JTextArea messageTextArea;
    private javax.swing.JLabel numberOfGameLabel;
    private javax.swing.JLabel numberOfGameValue;
    private javax.swing.JLabel numberOfWinLabel;
    private javax.swing.JLabel numberOfWinValue;
    private javax.swing.JButton quickGameButton;
    private javax.swing.JLabel rankLabel;
    private javax.swing.JLabel rankValue;
    private javax.swing.JButton scoreBoardButton;
    private javax.swing.JButton scoreBotButton;
    private javax.swing.JButton sendMessageButton;
    private javax.swing.JLabel winRatioLabel;
    private javax.swing.JLabel winRatioValue;
    // End of variables declaration//GEN-END:variables
}
