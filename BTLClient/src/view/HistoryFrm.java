package view;

import controller.Client;
import java.awt.Dimension;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.History;

public class HistoryFrm extends javax.swing.JFrame {
    private String nameUser = Client.user.getNickname();
    DefaultTableModel defaultTableModel;
    public HistoryFrm(List<History> listHistory) {
        initComponents();
        this.setTitle("Lịch sử đấu");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("assets/logo.png").getImage());
        this.setResizable(false);
        this.getContentPane().setLayout(null);
        
        defaultTableModel = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        defaultTableModel.addColumn("Tên");
        defaultTableModel.addColumn("Kết quả");
        defaultTableModel.addColumn("Điểm");
        defaultTableModel.addColumn("-");
        defaultTableModel.addColumn("Điểm đối thủ");
        defaultTableModel.addColumn("Đối thủ");
        defaultTableModel.addColumn("Thời gian");
        historyTable.setModel(defaultTableModel);  
        historyTable.setShowGrid(false);
        historyTable.setIntercellSpacing(new Dimension(0, 0));
        historyTable.getTableHeader().setFont(new java.awt.Font("FVF Fernando 08", 1, 13));
        setData(listHistory);
        
    }
    
    private void setData(List<History>listHistory){
        for(History history:listHistory){
            defaultTableModel.addRow(new Object[]{Client.user.getNickname(),history.getResult(),history.getScoreUser1()+"","-",history.getScoreUser2()+"", history.getNameUser2(),history.getTime()+""});
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i = 0 ; i < 7;i++)
            historyTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        frameLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        frameLabel.setFont(new java.awt.Font("FVF Fernando 08", 1, 24)); // NOI18N
        frameLabel.setForeground(new java.awt.Color(51, 51, 51));
        frameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        frameLabel.setText("LỊCH SỬ ĐẤU");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cardlogo.png"))); // NOI18N

        historyTable.setBackground(new java.awt.Color(245, 255, 255));
        historyTable.setFont(new java.awt.Font("FVF Fernando 08", 0, 11)); // NOI18N
        historyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Tên", "Kết quả", "Điểm ", "-", "Điểm đối thủ", "Đối thủ", "Thời gian"
            }
        ));
        historyTable.setGridColor(new java.awt.Color(0, 255, 204));
        historyTable.setRowHeight(35);
        jScrollPane1.setViewportView(historyTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(174, 174, 174)
                .addComponent(frameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(366, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(frameLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel frameLabel;
    private javax.swing.JTable historyTable;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
