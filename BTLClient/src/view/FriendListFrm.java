
package view;

import controller.Client;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.User;
public class FriendListFrm extends javax.swing.JFrame {
    private List<User> listFriend;
    private boolean isClicked;
    DefaultTableModel defaultTableModel;

    public FriendListFrm() {
        initComponents();
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf"); 
            SwingUtilities.updateComponentTreeUI(this);  // 'frame' là JFrame chính của bạn
        } catch (Exception e) {
            e.printStackTrace();
        }
        defaultTableModel = (DefaultTableModel) friendTable.getModel();
        this.setTitle("Danh sách bạn bè");
        this.setIconImage(new ImageIcon("assets/logo.png").getImage());
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        loadingButtonImage.setIcon(new ImageIcon(new ImageIcon("assets/sign-out.png")
                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        isClicked = false;
        requestUpdate();
        startThread();
    }

    public void stopAllThread() {
        isClicked = true;
    }

    public void startThread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (Client.friendListFrm.isDisplayable() && !isClicked) {
                    try {
                        System.out.println("Update danh sách bạn bè!");
                        requestUpdate();
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    public void requestUpdate() {
        try {
            Client.socketHandle.write("view-friend-list,");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }

    public void updateFriendList(List<User> friends) throws IOException {
        listFriend = friends;
        defaultTableModel.setRowCount(0);
        ImageIcon icon;
        for (User friend : listFriend) {
            if (!friend.isOnline()) {
                icon = new ImageIcon("assets/iconOffline.png");
            } else if (friend.isPlaying()) {
                icon = new ImageIcon("assets/iconPlaying.png");
            } else {
                icon = new ImageIcon("assets/iconOnline.png");
            }
            defaultTableModel.addRow(new Object[]{
                    "" + friend.getID(),
                    friend.getNickname(),
                    icon
            });
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        friendTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        friendTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        Object[][] rows = {
        };
        String[] columns = {"ID","Nickname",""};
        DefaultTableModel model = new DefaultTableModel(rows, columns){
            @Override
            public Class<?> getColumnClass(int column){
                switch(column){
                    case 0: return String.class;
                    case 1: return String.class;
                    case 2: return ImageIcon.class;
                    default: return Object.class;
                }
            }
        };
        friendTable = new javax.swing.JTable();
        frameLabel = new javax.swing.JLabel();
        loadingButtonImage = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        friendTable.setBackground(new java.awt.Color(240, 255, 255));
        friendTable.setFont(new java.awt.Font("FVF Fernando 08", 0, 16)); // NOI18N
        friendTable.setModel(model);
        friendTable.setRowHeight(60);
        friendTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                friendTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(friendTable);

        frameLabel.setFont(new java.awt.Font("FVF Fernando 08", 1, 18)); // NOI18N
        frameLabel.setForeground(new java.awt.Color(102, 102, 102));
        frameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        frameLabel.setText("Danh sách bạn bè");

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(407, 407, 407)
                        .addComponent(loadingButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(frameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(frameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(loadingButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void friendTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_friendTableMouseClicked
        try {
            if (friendTable.getSelectedRow() == -1) return;
            User friend = listFriend.get(friendTable.getSelectedRow());
            if (!friend.isOnline()) {
                throw new Exception("Người chơi không online");
            }
            if (friend.isPlaying()) {
                throw new Exception("Người chơi đang trong trận đấu");
            }
            isClicked = true;
            int res = JOptionPane.showConfirmDialog(rootPane, "Bạn có muốn thách đấu người bạn này không", "Xác nhận thách đầu", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                Client.closeAllViews();
                Client.openView(Client.View.GAME_NOTICE, "Thách đấu", "Đang chờ phản hồi từ đối thủ");
                Client.socketHandle.write("duel-request," + friend.getID());
            } else {
                isClicked = false;
                startThread();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_friendTableMouseClicked

    private void loadingButtonImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadingButtonImageActionPerformed
        Client.closeView(Client.View.FRIEND_LIST);
        try {
            Client.openView(Client.View.HOMEPAGE);
        } catch (IOException ex) {
            Logger.getLogger(FriendListFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_loadingButtonImageActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel frameLabel;
    private javax.swing.JTable friendTable;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton loadingButtonImage;
    // End of variables declaration//GEN-END:variables
}
