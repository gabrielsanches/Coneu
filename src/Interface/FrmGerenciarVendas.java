package Interface;

import DAL.ConectaBd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class FrmGerenciarVendas extends javax.swing.JInternalFrame {
    Connection conecta; //É o objeto que conecta com o banco de dados
    PreparedStatement pst;
    ResultSet rs;
    
    public FrmGerenciarVendas() throws ClassNotFoundException {
        initComponents();
        this.setLocation(200, 90);
        conecta = ConectaBd.conectabd();
        listarVendas();
    }
    
    public void listarVendas(){
        String sql = "Select *from vendas order by codigo Asc";
        try{
            pst = conecta.prepareStatement(sql);
            rs = pst.executeQuery();
            jTableGerenciarVendas.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    public void pesquisarVendas(){
        String sql = "Select *from vendas where cliente like ?";
        try{
            pst = conecta.prepareStatement (sql);
            pst.setString(1, txtPesquisarVendas.getText()+"%");//Quando usar backspace funciona tambem por causa do %
            rs = pst.executeQuery();
            jTableGerenciarVendas.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog (null, error);
        }
    }
    
    public void deletarVendas(){
        String sql = "Delete from vendas where codigo = ?"; //se usar *from ele deleta todos os clientes!! 
        try {
            pst = conecta.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtCodigoVendaGerenciarVendas.getText()));
            pst.execute();
            listarVendas();
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    public void mostraCodigo(){ //mostra o código da venda ao usuario selecionar a venda na tabela
        int seleciona = jTableGerenciarVendas.getSelectedRow();
        txtCodigoVendaGerenciarVendas.setText(jTableGerenciarVendas.getModel().getValueAt(seleciona, 0).toString());
    }
    
    public void limparCampos(){
        txtCodigoVendaGerenciarVendas.setText("");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableGerenciarVendas = new javax.swing.JTable();
        txtPesquisarVendas = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigoVendaGerenciarVendas = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setTitle("Vendas Gerenciar");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Vendas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(51, 102, 255))); // NOI18N

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/printer.png"))); // NOI18N
        jButton6.setText("Imprimir venda");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/delete.png"))); // NOI18N
        jButton3.setText("Excluir venda");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTableGerenciarVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableGerenciarVendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableGerenciarVendasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableGerenciarVendas);

        txtPesquisarVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisarVendasActionPerformed(evt);
            }
        });
        txtPesquisarVendas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarVendasKeyReleased(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/search.png"))); // NOI18N
        jLabel6.setText("BUSCAR");

        jLabel1.setText("Código Venda:");

        txtCodigoVendaGerenciarVendas.setBackground(new java.awt.Color(102, 102, 102));
        txtCodigoVendaGerenciarVendas.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtPesquisarVendas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 346, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(150, 150, 150)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCodigoVendaGerenciarVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton3, jButton6});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisarVendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCodigoVendaGerenciarVendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton3, jButton6});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPesquisarVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisarVendasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisarVendasActionPerformed

    private void txtPesquisarVendasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarVendasKeyReleased
        pesquisarVendas();
    }//GEN-LAST:event_txtPesquisarVendasKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        deletarVendas();
        JOptionPane.showMessageDialog(null, "Venda excluída com sucesso");
        limparCampos();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTableGerenciarVendasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableGerenciarVendasMouseClicked
       mostraCodigo();
    }//GEN-LAST:event_jTableGerenciarVendasMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableGerenciarVendas;
    private javax.swing.JTextField txtCodigoVendaGerenciarVendas;
    private javax.swing.JTextField txtPesquisarVendas;
    // End of variables declaration//GEN-END:variables
}
