package Interface;

import DAL.ConectaBd;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

//Na tabela do banco de dados usei produtos_float pq pensei que nao ia funcionar
//Ai com o _float eu saberia que o erro estava nisso e faria outra. Mas tudo certo!

public class FrmGerenciarProdutos extends javax.swing.JInternalFrame {
    Connection conecta; //É o objeto que conecta com o banco de dados
    PreparedStatement pst;
    ResultSet rs;  
    
    
    public FrmGerenciarProdutos() throws ClassNotFoundException {
        initComponents();       
        this.setLocation(300, 100);
        conecta = ConectaBd.conectabd();
        listarProdutos();//chama a tabela de PRODUTOS sempre que abre o formulario
    }
    
    public void listarProdutos(){
        String sql = "Select *from produtos_float order by codigo Asc"; //orderna pelo numero do codigo ascendentemente
        try{
            pst = conecta.prepareStatement(sql);
            rs = pst.executeQuery();
            tblProdutos.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
    }
     
     public void cadastrarProdutos(){ 
        String sql = "Insert into produtos_float(descricao, valor_compra, valor_venda, estoque) values(?,?,?,?)";
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, txtDescricaoProduto.getText());
            pst.setDouble(2, Double.parseDouble(txtValorCompraProduto.getText()));
            pst.setDouble(3, Double.parseDouble(txtValorVendaProduto.getText()));
            pst.setInt(4, Integer.parseInt(txtEstoqueProduto.getText()));
            
            /*pst.setInt(9, Integer.parseInt(txtCodigo.getText()));    
            Integer.parseInt(txtEstoqueProduto.trim()); //.trim() serve para ignorar os 'espaços' digitado pelo usuario, as vezes por engano
            num1 = Integer.parseInt(valor.trim());*/
            
            pst.execute();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!","Mensagem",JOptionPane.INFORMATION_MESSAGE);
            listarProdutos(); //atualiza a tabela sempre que um novo produto é cadastrado      
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog(null,error);
        }
    }
     
    public void pesquisarProdutos(){ //Pesquisa a DESCRIÇÃO do Produto ao começar a digitar
        String sql = "Select *from produtos_float where descricao like ?";
        try{
            pst = conecta.prepareStatement (sql);
            pst.setString(1, txtPesquisarProdutos.getText()+"%");//Quando usar backspace funciona tambem por causa do %
            //int estoque = rs.getInt(0); PRA FAZER O ESTOQUE
            rs = pst.executeQuery();
            tblProdutos.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog (null, error);
        }
    }
    
    public void mostraItens(){ //preenche os campos de determinado usuario ao clicar sobre ele
        int seleciona = tblProdutos.getSelectedRow(); //mostra nos campos da tela o que clicar na tabela           
        txtCodigoProduto.setText(tblProdutos.getModel().getValueAt(seleciona, 0).toString());
        txtDescricaoProduto.setText(tblProdutos.getModel().getValueAt(seleciona, 1).toString());
        txtValorCompraProduto.setText(tblProdutos.getModel().getValueAt(seleciona, 2).toString());
        txtValorVendaProduto.setText(tblProdutos.getModel().getValueAt(seleciona, 3).toString());
        txtEstoqueProduto.setText(tblProdutos.getModel().getValueAt(seleciona, 4).toString());
    }
    
    public void deletarProdutos(){
        String sql = "Delete from produtos_float where codigo = ?"; //se usar *from ele deleta todos os clientes!! 
        try {
            pst = conecta.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtCodigoProduto.getText()));
            pst.execute();
            listarProdutos();
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    public void limparCampos(){
        txtCodigoProduto.setText("");
        txtDescricaoProduto.setText("");
        txtValorVendaProduto.setText("");
        txtValorCompraProduto.setText("");
        txtEstoqueProduto.setText("");
    }
    
     public void editarProdutos(){
        String sql = "Update produtos_float set descricao = ?, valor_Compra = ?, valor_venda = ?, estoque = ? where codigo = ?";
        try{
            pst = conecta.prepareStatement(sql);
            
            pst.setString(1, txtDescricaoProduto.getText());
            pst.setDouble(2, Double.parseDouble(txtValorCompraProduto.getText()));
            pst.setDouble(3, Double.parseDouble(txtValorVendaProduto.getText()));
            pst.setInt(4, Integer.parseInt(txtEstoqueProduto.getText()));
            
            //pst.setInt(4, Integer.parseInt(txtEstoqueProduto.getText())+a); // PRA FAZER O ESTOQUE
            
            
            pst.setInt(5, Integer.parseInt(txtCodigoProduto.getText()));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto editado com sucesso");
            listarProdutos();//atualiza a tabela após a edição
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
    }

     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblProdutos = new javax.swing.JTable();
        txtValorCompraProduto = new javax.swing.JTextField();
        txtValorVendaProduto = new javax.swing.JTextField();
        txtPesquisarProdutos = new javax.swing.JTextField();
        txtEstoqueProduto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        txtCodigoProduto = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDescricaoProduto = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setTitle("Produtos Gerenciar");

        tblProdutos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdutosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProdutos);

        txtValorVendaProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorVendaProdutoActionPerformed(evt);
            }
        });

        txtPesquisarProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisarProdutosActionPerformed(evt);
            }
        });
        txtPesquisarProdutos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPesquisarProdutosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarProdutosKeyReleased(evt);
            }
        });

        txtEstoqueProduto.setToolTipText("Valor inicial do produto no estoque");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/search.png"))); // NOI18N
        jLabel1.setText("BUSCAR");

        jButton1.setText("Cadastrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Código Produto:");

        jButton3.setText("Deletar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel3.setText("Descrição:");

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/DeleteGrey.png"))); // NOI18N
        jButton4.setText("Limpar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        txtCodigoProduto.setBackground(new java.awt.Color(102, 102, 102));
        txtCodigoProduto.setEnabled(false);

        jButton2.setText("Editar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Valor de compra:");

        jLabel5.setText("Valor de venda:");

        jLabel6.setText("Estoque:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(txtPesquisarProdutos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEstoqueProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtValorCompraProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtValorVendaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDescricaoProduto))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3)
                                .addGap(55, 55, 55)
                                .addComponent(jButton4)))))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisarProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescricaoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txtValorCompraProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValorVendaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEstoqueProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton2))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2, jButton3, jButton4});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutosMouseClicked
        limparCampos();
        mostraItens();
    }//GEN-LAST:event_tblProdutosMouseClicked

    private void txtValorVendaProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorVendaProdutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorVendaProdutoActionPerformed

    private void txtPesquisarProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisarProdutosActionPerformed
        //pesquisarProdutos();
    }//GEN-LAST:event_txtPesquisarProdutosActionPerformed

    private void txtPesquisarProdutosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarProdutosKeyReleased
        pesquisarProdutos();
    }//GEN-LAST:event_txtPesquisarProdutosKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cadastrarProdutos();
        limparCampos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        deletarProdutos();
        limparCampos();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        limparCampos();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        editarProdutos();
        limparCampos();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtPesquisarProdutosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarProdutosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisarProdutosKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProdutos;
    private javax.swing.JTextField txtCodigoProduto;
    private javax.swing.JTextField txtDescricaoProduto;
    private javax.swing.JTextField txtEstoqueProduto;
    private javax.swing.JTextField txtPesquisarProdutos;
    private javax.swing.JTextField txtValorCompraProduto;
    private javax.swing.JTextField txtValorVendaProduto;
    // End of variables declaration//GEN-END:variables
}
