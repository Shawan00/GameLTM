
package view;
import controller.Client;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class WaitingRoomFrm extends javax.swing.JFrame {
    private boolean isOpenning;
    private Timer timer;

    public WaitingRoomFrm() {
        initComponents();
        this.setTitle("Sảnh chờ");
        isOpenning = false;
        this.setIconImage(new ImageIcon("assets/logo.png").getImage());
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        loadingButtonImage.setIcon(new ImageIcon(new ImageIcon("assets/sign-out.png")
                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        roomPasswordLabel.setVisible(false);
        
        
        timer = new Timer(1000, new ActionListener() {
            int minute = 0;
            int second = 0;
            public void actionPerformed(ActionEvent e) {
                second++;
                if(second == 60){
                    minute+=1;
                    second = 0;
                }
                String minuteStr= minute+"", secondStr = second+"";
                if(minute < 10) minuteStr = "0"+minute;
                if(second < 10) secondStr = "0"+second;
                countDouwnTimeLabel.setText(minuteStr+":"+secondStr);
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    public void setRoomName(String roomName) {
        roomNameLabel.setText(roomName);
    }

    public void setRoomPassword(String password) {
        roomPasswordLabel.setText(password);
        roomPasswordLabel.setVisible(true);
    }

    public void showFoundCompetitor() {
        isOpenning = true;
        pendingMessageLabel.setText("Đã tìm thấy đối thủ");
        pendingMessageLabel.setForeground(Color.BLUE);
        loadingButtonImage.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        pendingMessageLabel = new javax.swing.JLabel();
        loadingButtonImage = new javax.swing.JButton();
        roomNameLabel = new javax.swing.JLabel();
        roomPasswordLabel = new javax.swing.JLabel();
        frameLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        countDouwnTimeLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(java.awt.SystemColor.activeCaption);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cardlogo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(112, Short.MAX_VALUE))
        );

        pendingMessageLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 18)); // NOI18N
        pendingMessageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pendingMessageLabel.setText("Đang chờ ");

        loadingButtonImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadingButtonImageActionPerformed(evt);
            }
        });

        roomNameLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 14)); // NOI18N
        roomNameLabel.setForeground(new java.awt.Color(51, 51, 51));
        roomNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roomNameLabel.setText("roomtext");

        roomPasswordLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 14)); // NOI18N
        roomPasswordLabel.setForeground(new java.awt.Color(51, 51, 51));
        roomPasswordLabel.setText("pwtext");

        frameLabel.setFont(new java.awt.Font("FVF Fernando 08", 1, 18)); // NOI18N
        frameLabel.setForeground(new java.awt.Color(51, 51, 51));
        frameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        frameLabel.setText("SẢNH CHỜ");

        jSeparator1.setForeground(new java.awt.Color(102, 102, 102));

        jSeparator2.setForeground(new java.awt.Color(102, 102, 102));

        countDouwnTimeLabel.setFont(new java.awt.Font("FVF Fernando 08", 0, 18)); // NOI18N
        countDouwnTimeLabel.setText("00:00");

        jLabel1.setFont(new java.awt.Font("FVF Fernando 08", 0, 14)); // NOI18N
        jLabel1.setText("Phòng:");

        jSeparator3.setForeground(new java.awt.Color(102, 102, 102));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel2.setFont(new java.awt.Font("FVF Fernando 08", 0, 14)); // NOI18N
        jLabel2.setText("Mật khẩu:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roomNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roomPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pendingMessageLabel)
                                .addGap(72, 72, 72)
                                .addComponent(loadingButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(frameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(92, 92, 92))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(countDouwnTimeLabel)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(frameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(roomNameLabel)
                        .addComponent(jLabel1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(roomPasswordLabel)
                            .addComponent(jLabel2))
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(countDouwnTimeLabel)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loadingButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pendingMessageLabel))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadingButtonImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadingButtonImageActionPerformed
        if (isOpenning) return;
        try {
            Client.closeView(Client.View.WAITING_ROOM);
            Client.openView(Client.View.HOMEPAGE);
            Client.socketHandle.write("cancel-room,");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_loadingButtonImageActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel countDouwnTimeLabel;
    private javax.swing.JLabel frameLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JButton loadingButtonImage;
    private javax.swing.JLabel pendingMessageLabel;
    private javax.swing.JLabel roomNameLabel;
    private javax.swing.JLabel roomPasswordLabel;
    // End of variables declaration//GEN-END:variables
}
