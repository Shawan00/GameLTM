package view;

import controller.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.User;

public class RankFrm extends javax.swing.JFrame {
    private final DefaultTableModel tableModel;
    private List<User> listUserStatics;
    private final List<String> rankSrc;
    public RankFrm() {
        initComponents();
        this.setTitle("Bảng xếp hạng");
        tableModel = (DefaultTableModel) rankTextArea.getModel();
        this.setIconImage(new ImageIcon("assets/logo.png").getImage());
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        rankSrc = new ArrayList<>();
        rankSrc.add("iconrank1");
        rankSrc.add("iconrank2");
        rankSrc.add("iconrank3");
        try {
            Client.socketHandle.write("get-rank-charts,");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }

    public void setDataToTable(List<User> users) {
        this.listUserStatics = users;
        tableModel.setRowCount(0);
        int i = 0;
        for (User user : listUserStatics) {
            tableModel.addRow(new Object[]{
                    user.getNickname(),
                    (user.getNumberOfDraw()+user.getNumberOfWin()*3)+"",
                    i<3?new ImageIcon("assets/" + rankSrc.get(i) + ".png"):new ImageIcon()
            });
            i++;
        }
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        rankTextArea.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        rankTextArea.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Object[][] rows = {
        };
        String[] columns = {"Nickname","Điểm","Rank"};
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
        rankTextArea = new javax.swing.JTable();
        frameLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cardlogo.png"))); // NOI18N

        rankTextArea.setBackground(new java.awt.Color(250, 255, 255));
        rankTextArea.setFont(new java.awt.Font("FVF Fernando 08", 0, 14)); // NOI18N
        rankTextArea.setModel(model);
        rankTextArea.setFillsViewportHeight(true);
        rankTextArea.setRowHeight(62);
        rankTextArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rankTextAreaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(rankTextArea);

        frameLabel.setFont(new java.awt.Font("FVF Fernando 08", 1, 18)); // NOI18N
        frameLabel.setForeground(new java.awt.Color(102, 102, 102));
        frameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        frameLabel.setText("BẢNG XẾP HẠNG");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(frameLabel)
                        .addGap(41, 41, 41)
                        .addComponent(jLabel2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(frameLabel)
                    .addComponent(jLabel2))
                .addGap(18, 23, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rankTextAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rankTextAreaMouseClicked
        if (rankTextArea.getSelectedRow() == -1)
            return;
        if (listUserStatics.get(rankTextArea.getSelectedRow()).getID() == Client.user.getID()) {
            JOptionPane.showMessageDialog(rootPane, "Thứ hạng của bạn là " + (rankTextArea.getSelectedRow() + 1));
            return;
        }
        Client.openView(Client.View.COMPETITOR_INFO, listUserStatics.get(rankTextArea.getSelectedRow()));
    }//GEN-LAST:event_rankTextAreaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel frameLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable rankTextArea;
    // End of variables declaration//GEN-END:variables
}
