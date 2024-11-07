package view;
import controller.Client;
import java.awt.Image;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class RoomListFrm extends javax.swing.JFrame {
    private Vector<String> listRoom;
    private Vector<String> listPassword;
    private boolean isPlayThread;
    private final boolean isFiltered;
    DefaultTableModel defaultTableModel;

    public RoomListFrm() {
        initComponents();
        this.setTitle("Danh sách phòng");
        this.setIconImage(new ImageIcon("assets/logo.png").getImage());
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        defaultTableModel = (DefaultTableModel) roomTextArea.getModel();
        isPlayThread = true;
        isFiltered = false;
        loadingButtonImage.setIcon(new ImageIcon(new ImageIcon("assets/sign-out.png")
                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (Client.roomListFrm.isDisplayable() && isPlayThread && !isFiltered) {
                    try {
                        Client.socketHandle.write("view-room-list,");
                        Thread.sleep(500);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                    } catch (InterruptedException ex) {
                        JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                    }
                }
            }
        };
        thread.start();
    }

    public void updateRoomList(Vector<String> listData, Vector<String> listPassword) {
        this.listRoom = listData;
        this.listPassword = listPassword;
        defaultTableModel.setRowCount(0);
        ImageIcon imageIcon;
        for (int i = 0; i < listRoom.size(); i++) {
            if (listPassword.get(i).equals(" ")){
                ImageIcon originalIcon = new ImageIcon("assets/iconopendoor.png");
                Image scaledImage = originalIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(scaledImage);
            }
                
            else{
                ImageIcon originalIcon = new ImageIcon("assets/iconclosedoor.png");
                Image scaledImage = originalIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(scaledImage);
            }
                
            defaultTableModel.addRow(new Object[]{
                    listRoom.get(i),
                    imageIcon
            });
        }
        
        // Tạo một renderer để căn giữa nội dung
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        roomTextArea.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        Object[][] rows = {
        };
        String[] columns = {"Tên phòng",""};
        DefaultTableModel model = new DefaultTableModel(rows, columns){
            @Override
            public Class<?> getColumnClass(int column){
                switch(column){
                    case 0: return String.class;
                    case 1: return ImageIcon.class;
                    default: return Object.class;
                }
            }
        };
        roomTextArea = new javax.swing.JTable();
        loadingButtonImage = new javax.swing.JButton();
        frameLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        roomTextArea.setBackground(new java.awt.Color(235, 255, 255));
        roomTextArea.setFont(new java.awt.Font("Tekton Pro", 1, 18)); // NOI18N
        roomTextArea.setModel(model);
        roomTextArea.setFillsViewportHeight(true);
        roomTextArea.setRowHeight(60);
        roomTextArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roomTextAreaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(roomTextArea);
        if (roomTextArea.getColumnModel().getColumnCount() > 0) {
            roomTextArea.getColumnModel().getColumn(0).setMinWidth(240);
            roomTextArea.getColumnModel().getColumn(0).setPreferredWidth(240);
            roomTextArea.getColumnModel().getColumn(0).setMaxWidth(240);
            roomTextArea.getColumnModel().getColumn(1).setMinWidth(120);
            roomTextArea.getColumnModel().getColumn(1).setPreferredWidth(120);
            roomTextArea.getColumnModel().getColumn(1).setMaxWidth(120);
        }

        loadingButtonImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadingButtonImageActionPerformed(evt);
            }
        });

        frameLabel.setBackground(new java.awt.Color(255, 255, 255));
        frameLabel.setFont(new java.awt.Font("FVF Fernando 08", 1, 24)); // NOI18N
        frameLabel.setForeground(new java.awt.Color(51, 51, 51));
        frameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        frameLabel.setText("PHÒNG TRỐNG");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(loadingButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
            .addGroup(layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(frameLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(frameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(loadingButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void roomTextAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roomTextAreaMouseClicked
        if (roomTextArea.getSelectedRow() == -1) {
        } else {
            try {
                isPlayThread = false;
                int index = roomTextArea.getSelectedRow();
                int room = Integer.parseInt(listRoom.get(index).split(" ")[1]);
                String password = listPassword.get(index);
                if (password.equals(" ")) {
                    Client.socketHandle.write("join-room," + room);
                    Client.closeView(Client.View.ROOM_LIST);
                } else {
                    Client.closeView(Client.View.ROOM_LIST);
                    Client.openView(Client.View.JOIN_ROOM_PASSWORD, room, password);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }
    }//GEN-LAST:event_roomTextAreaMouseClicked

    private void loadingButtonImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadingButtonImageActionPerformed
        Client.closeView(Client.View.ROOM_LIST);
        try {
            Client.openView(Client.View.HOMEPAGE);
        } catch (IOException ex) {
            Logger.getLogger(RoomListFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_loadingButtonImageActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel frameLabel;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton loadingButtonImage;
    private javax.swing.JTable roomTextArea;
    // End of variables declaration//GEN-END:variables
}
