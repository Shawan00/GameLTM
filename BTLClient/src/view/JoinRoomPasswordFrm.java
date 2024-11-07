package view;
import controller.Client;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class JoinRoomPasswordFrm extends javax.swing.JFrame {
    private int room;
    private String password;

    public JoinRoomPasswordFrm(int room, String password) {
        initComponents();
        this.setTitle("Tham gia phòng đấu");
        this.setIconImage(new ImageIcon("assets/logo.png").getImage());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.room = room;
        this.password = password;
        loadingButtonImage.setIcon(new ImageIcon(new ImageIcon("assets/sign-out.png")
                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        passwordInput = new javax.swing.JTextField();
        goToRoomButton = new javax.swing.JButton();
        passwordLabel = new javax.swing.JLabel();
        loadingButtonImage = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        goToRoomButton.setBackground(new java.awt.Color(0, 153, 153));
        goToRoomButton.setFont(new java.awt.Font("FVF Fernando 08", 0, 14)); // NOI18N
        goToRoomButton.setForeground(new java.awt.Color(255, 255, 255));
        goToRoomButton.setText("Vào");
        goToRoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goToRoomButtonActionPerformed(evt);
            }
        });

        passwordLabel.setFont(new java.awt.Font("FVF Fernando 08", 1, 14)); // NOI18N
        passwordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        passwordLabel.setText("Mật khẩu");

        loadingButtonImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadingButtonImageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(goToRoomButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(loadingButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 37, Short.MAX_VALUE)
                        .addComponent(passwordInput, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(passwordLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordInput, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(loadingButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(goToRoomButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void goToRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goToRoomButtonActionPerformed
        try {
            String password = passwordInput.getText();
            if (!password.equals(this.password))
                throw new Exception("Mật khẩu không chính xác");
            Client.socketHandle.write("join-room," + this.room);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_goToRoomButtonActionPerformed

    private void loadingButtonImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadingButtonImageActionPerformed
        Client.closeView(Client.View.JOIN_ROOM_PASSWORD);
        try {
            Client.openView(Client.View.HOMEPAGE);
        } catch (IOException ex) {
            Logger.getLogger(JoinRoomPasswordFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_loadingButtonImageActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton goToRoomButton;
    private javax.swing.JButton loadingButtonImage;
    private javax.swing.JTextField passwordInput;
    private javax.swing.JLabel passwordLabel;
    // End of variables declaration//GEN-END:variables
}
