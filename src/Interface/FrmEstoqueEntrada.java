package Interface;

import DAL.ConectaBd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;

public class FrmEstoqueEntrada extends javax.swing.JInternalFrame {

    Connection conecta; //É o objeto que conecta com o banco de dados
    PreparedStatement pst;
    ResultSet rs;
    
    public FrmEstoqueEntrada() throws ClassNotFoundException {
        
        initComponents();
        txtCodigoProdutoEntradaEstoque.setEditable(false);
        this.setLocation(300, 65);
        conecta = ConectaBd.conectabd();
        listarProdutos();
    }
    
    public void listarProdutos() {
        String sql = "Select codigo, descricao, valor_venda, valor_compra from produtos_float order by codigo Asc"; //orderna pelo numero do codigo ascendentemente
        try {
            pst = conecta.prepareStatement(sql);
            rs = pst.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    public void pesquisarProdutos() { //Pesquisa a DESCRIÇÃO do Produto ao começar a digitar
        String sql = "Select *from produtos_float where descricao like ?";
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, jTextField1.getText() + "%");//Quando usar backspace funciona tambem por causa do %
            rs = pst.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    public void mostraProdutos() { //preenche os campos do painel da venda
        int seleciona = jTable1.getSelectedRow(); //mostra nos campos da tela o que clicar na tabela           
        txtCodigoProdutoEntradaEstoque.setText(jTable1.getModel().getValueAt(seleciona, 0).toString());
        txtProdutoEntradaEstoque.setText(jTable1.getModel().getValueAt(seleciona, 1).toString());
    }
    
    public void limparCampos() {
        txtCodigoProdutoEntradaEstoque.setText("");
        txtProdutoEntradaEstoque.setText("");        
        txtQuantidadeProdutoEntradaEstoque.setText("");
        jFormattedTextField1.setText("");
    }
    
    public void atualizaEstoque() throws ParseException {
        String sql = "Insert into controle_estoque(codigoproduto_estoque_, produto_estoque_, quantidade_entrada_produto_estoque, data) values(?,?,?,?)";
        try {
            pst = conecta.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtCodigoProdutoEntradaEstoque.getText())); //inteiro
            pst.setString(2, txtProdutoEntradaEstoque.getText());
            pst.setInt(3, Integer.parseInt(txtQuantidadeProdutoEntradaEstoque.getText())); //inteiro
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            
            pst.setDate(4, (java.sql.Date) (sqlDate)); //tipo data
            String s = sqlDate + "";
            System.out.println(s);            
            pst.execute();
            
            sql = "Update produtos_float set descricao=?, estoque=? where codigo=?";
            pst = conecta.prepareStatement(sql);
            pst.setString(1, txtProdutoEntradaEstoque.getText());
            pst.setInt(2, Integer.parseInt(txtQuantidadeProdutoEntradaEstoque.getText()));
            pst.setInt(3, Integer.parseInt(txtCodigoProdutoEntradaEstoque.getText()));
            pst.execute();
//            sql = "Update produtos_float set descricao='"+txtProdutoEntradaEstoque.getText()+"', estoque="+Integer.parseInt(txtQuantidadeProdutoEntradaEstoque.getText())+" where codigo="+Integer.parseInt(txtCodigoProdutoEntradaEstoque.getText());
//            pst.execute(sql);
            JOptionPane.showMessageDialog(null, "Estoque de produto atualizado com sucesso!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            //listarClientes(); //atualiza a tabela sempre que um novo cliente é cadastrado      
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtCodigoProdutoEntradaEstoque = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtQuantidadeProdutoEntradaEstoque = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtProdutoEntradaEstoque = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();

        setClosable(true);
        setIconifiable(true);
        setTitle("Estoque Entrada de Produtos");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Entrada de Produtos no Estoque", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(51, 102, 255))); // NOI18N

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/search.png"))); // NOI18N
        jLabel6.setText("BUSCAR");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Código Produto:");

        txtCodigoProdutoEntradaEstoque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoProdutoEntradaEstoqueKeyReleased(evt);
            }
        });

        jLabel2.setText("Quantidade de Produtos:");

        jLabel3.setText("Produto:");

        txtProdutoEntradaEstoque.setBackground(new java.awt.Color(102, 102, 102));
        txtProdutoEntradaEstoque.setEnabled(false);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/arrow_rotate_anticlockwise.png"))); // NOI18N
        jButton2.setText("Atualizar Estoque");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/DeleteGrey.png"))); // NOI18N
        jButton4.setText("Limpar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel4.setText("Data:");

        try {
            jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigoProdutoEntradaEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProdutoEntradaEstoque))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQuantidadeProdutoEntradaEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(138, 138, 138)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCodigoProdutoEntradaEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtProdutoEntradaEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtQuantidadeProdutoEntradaEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton2, jButton4});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            atualizaEstoque();
        } catch (ParseException ex) {
            Logger.getLogger(FrmEstoqueEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
        limparCampos();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        limparCampos();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        pesquisarProdutos();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        mostraProdutos();
    }//GEN-LAST:event_jTable1MouseClicked

    private void txtCodigoProdutoEntradaEstoqueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProdutoEntradaEstoqueKeyReleased
        
    }//GEN-LAST:event_txtCodigoProdutoEntradaEstoqueKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField txtCodigoProdutoEntradaEstoque;
    private javax.swing.JTextField txtProdutoEntradaEstoque;
    private javax.swing.JTextField txtQuantidadeProdutoEntradaEstoque;
    // End of variables declaration//GEN-END:variables

    private void formataData(JTextField jTextField2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
