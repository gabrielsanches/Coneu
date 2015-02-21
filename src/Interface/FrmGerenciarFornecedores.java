package Interface;
import DAL.ConectaBd;
import java.sql.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/*
Na tabela de banco de dados dos fornecedores a primary key ta como codigofornecedore_pk
*/

public class FrmGerenciarFornecedores extends javax.swing.JInternalFrame {
    
    Connection conecta; //É o objeto que conecta com o banco de dados
    PreparedStatement pst;
    ResultSet rs;
    
    public FrmGerenciarFornecedores() throws ClassNotFoundException {
        initComponents();
        this.setLocation(300, 110);
        conecta = ConectaBd.conectabd();
        listarFornecedores(); //chama a tabela de FORNECEDORES sempre que abre o formulario
    }
    
    public void listarFornecedores(){
        String sql = "Select *from fornecedores order by codigo Asc"; //orderna pelo numero do codigo ascendentemente
        try{
            pst = conecta.prepareStatement(sql);
            rs = pst.executeQuery();
            tblFornecedores.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    public void cadastrarFornecedores(){ 
    // Cadastra novo usuario, nenhum campo obrigatório
    // O código é gerado automaticamente pelo banco de dados, em ordem crescente a partir do 1
        String sql = "Insert into fornecedores(fornecedor, cpf, cnpj, telefone, cep, endereco, cidade, uf, email) values(?,?,?,?,?,?,?,?,?)";
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, txtFornecedor.getText());
            pst.setString(2, txtCpfFornecedor.getText());
            pst.setString(3, txtCnpjFornecedor.getText());
            pst.setString(4, txtTelefoneFornecedor.getText());
            pst.setString(5, txtCepFornecedor.getText());
            pst.setString(6, txtEnderecoFornecedor.getText());
            pst.setString(7, txtCidadeFornecedor.getText());
            pst.setString(8, txtUfFornecedor.getText());
            pst.setString(9, txtEmailFornecedor.getText());
            
            pst.execute();
            JOptionPane.showMessageDialog(null, "Fornecedor cadastrado com sucesso!","Mensagem",JOptionPane.INFORMATION_MESSAGE);
            listarFornecedores(); //atualiza a tabela sempre que um novo fornecedor é cadastrado      
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog(null,error);
        }
    }
    
    public void pesquisarFornecedores(){ //Pesquisa o NOME do FORNECEDOR ao começar a digitar
        String sql = "Select *from fornecedores where fornecedor like ?";
        try{
            pst = conecta.prepareStatement(sql); //conecta é o metodo que chama pra conectar com o banco d dados
            pst.setString(1, txtPesquisarFornecedores.getText()+"%");//Quando usar backspace funciona tambem por causa do %
            rs = pst.executeQuery();
            tblFornecedores.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog (null, error);
        }
    }
    
    public void mostraItens(){ //preenche os campos de determinado usuario ao clicar sobre ele
        int seleciona = tblFornecedores.getSelectedRow(); //mostra nos campos da tela o que clicar na tabela           
        txtCodigoFornecedor.setText(tblFornecedores.getModel().getValueAt(seleciona, 0).toString());
        txtFornecedor.setText(tblFornecedores.getModel().getValueAt(seleciona, 1).toString());
        txtCpfFornecedor.setText(tblFornecedores.getModel().getValueAt(seleciona, 2).toString());
        txtCnpjFornecedor.setText(tblFornecedores.getModel().getValueAt(seleciona, 3).toString());
        txtTelefoneFornecedor.setText(tblFornecedores.getModel().getValueAt(seleciona, 4).toString());
        txtCepFornecedor.setText(tblFornecedores.getModel().getValueAt(seleciona, 5).toString());
        txtEnderecoFornecedor.setText(tblFornecedores.getModel().getValueAt(seleciona, 6).toString());
        txtCidadeFornecedor.setText(tblFornecedores.getModel().getValueAt(seleciona, 7).toString());
        txtUfFornecedor.setText(tblFornecedores.getModel().getValueAt(seleciona, 8).toString());
        txtEmailFornecedor.setText(tblFornecedores.getModel().getValueAt(seleciona, 9).toString());
    } 
    
    public void editarFornecedores(){
        String sql = "Update fornecedores set fornecedor = ?, cpf = ?, cnpj = ?, telefone = ?, cep = ?, endereco = ?, cidade = ?, uf = ?, email = ? where codigo = ?";
        try{
            pst = conecta.prepareStatement(sql);
            pst.setString(1, txtFornecedor.getText());
            pst.setString(2, txtCpfFornecedor.getText());
            pst.setString(3, txtCnpjFornecedor.getText());
            pst.setString(4, txtTelefoneFornecedor.getText());
            pst.setString(5, txtCepFornecedor.getText());
            pst.setString(6, txtEnderecoFornecedor.getText());
            pst.setString(7, txtCidadeFornecedor.getText());
            pst.setString(8, txtUfFornecedor.getText());
            pst.setString(9, txtEmailFornecedor.getText());
            pst.setInt(10, Integer.parseInt(txtCodigoFornecedor.getText()));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cadastro editado com sucesso");
            listarFornecedores();//atualiza a tabela após a edição
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    public void deletarFornecedores(){
        String sql = "Delete from fornecedores where codigo = ?"; //se usar *from ele deleta todos os clientes!! 
        try {
            pst = conecta.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtCodigoFornecedor.getText()));
            pst.execute();
            listarFornecedores();
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    public void limparCampos(){
        txtCodigoFornecedor.setText("");
        txtFornecedor.setText("");
        txtCpfFornecedor.setText("");
        txtCnpjFornecedor.setText("");
        txtTelefoneFornecedor.setText("");
        txtCepFornecedor.setText("");
        txtEnderecoFornecedor.setText("");
        txtCidadeFornecedor.setText("");
        txtUfFornecedor.setText("");
        txtEmailFornecedor.setText("");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtPesquisarFornecedores = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFornecedores = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCodigoFornecedor = new javax.swing.JTextField();
        txtFornecedor = new javax.swing.JTextField();
        txtEnderecoFornecedor = new javax.swing.JTextField();
        txtTelefoneFornecedor = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCidadeFornecedor = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCpfFornecedor = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCepFornecedor = new javax.swing.JFormattedTextField();
        txtCnpjFornecedor = new javax.swing.JFormattedTextField();
        txtEmailFornecedor = new javax.swing.JTextField();
        txtUfFornecedor = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setTitle("Fornecedores Gerenciar");

        txtPesquisarFornecedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarFornecedoresKeyReleased(evt);
            }
        });

        tblFornecedores.setModel(new javax.swing.table.DefaultTableModel(
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
        tblFornecedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFornecedoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblFornecedores);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/search.png"))); // NOI18N
        jLabel1.setText("BUSCAR");

        jLabel2.setText("Código:");

        jLabel3.setText("Telefone:");

        jLabel4.setText("Fornecedor:");

        jLabel5.setText("Endereço:");

        txtCodigoFornecedor.setBackground(new java.awt.Color(102, 102, 102));
        txtCodigoFornecedor.setEnabled(false);

        txtFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFornecedorActionPerformed(evt);
            }
        });

        try {
            txtTelefoneFornecedor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefoneFornecedor.setToolTipText("Digite somente números");
        txtTelefoneFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefoneFornecedorActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/user_add.png"))); // NOI18N
        jButton1.setText("Novo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/user_edit.png"))); // NOI18N
        jButton2.setText("Editar");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/user_delete.png"))); // NOI18N
        jButton3.setText("Deletar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/DeleteGrey.png"))); // NOI18N
        jButton4.setText("Limpar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel6.setText("email:");

        jLabel7.setText("Cidade:");

        jLabel8.setText("UF:");

        jLabel9.setText("CPF:");

        jLabel10.setText("CNPJ:");

        try {
            txtCpfFornecedor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCpfFornecedor.setToolTipText("Digite somente números");

        jLabel11.setText("CEP:");

        try {
            txtCepFornecedor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCepFornecedor.setToolTipText("Digite somente números");

        try {
            txtCnpjFornecedor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###############")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCnpjFornecedor.setToolTipText("Digite somente números");

        txtUfFornecedor.setToolTipText("Digite apenas a sigla do estado");
        txtUfFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUfFornecedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtPesquisarFornecedores)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtCepFornecedor, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtCpfFornecedor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                                    .addComponent(txtCodigoFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtCnpjFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTelefoneFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtFornecedor)
                                    .addComponent(txtEnderecoFornecedor)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(jButton2)
                                .addGap(50, 50, 50)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                .addComponent(jButton4))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(47, 47, 47)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtEmailFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                                            .addComponent(txtCidadeFornecedor)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(367, 367, 367)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtUfFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(6, 6, 6)))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisarFornecedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigoFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(txtCpfFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCnpjFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtTelefoneFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(txtCepFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(txtEnderecoFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtUfFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtCidadeFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEmailFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(30, 30, 30))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtCodigoFornecedor, txtTelefoneFornecedor});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2, jButton3, jButton4});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFornecedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFornecedorActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        limparCampos();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        deletarFornecedores();
        limparCampos();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtTelefoneFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefoneFornecedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefoneFornecedorActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cadastrarFornecedores();
        limparCampos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        editarFornecedores();
        limparCampos();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtPesquisarFornecedoresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarFornecedoresKeyReleased
       pesquisarFornecedores();
    }//GEN-LAST:event_txtPesquisarFornecedoresKeyReleased

    private void tblFornecedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFornecedoresMouseClicked
        limparCampos();
        mostraItens();
    }//GEN-LAST:event_tblFornecedoresMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        editarFornecedores();
    }//GEN-LAST:event_jButton2MouseClicked

    private void txtUfFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUfFornecedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUfFornecedorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblFornecedores;
    private javax.swing.JFormattedTextField txtCepFornecedor;
    private javax.swing.JTextField txtCidadeFornecedor;
    private javax.swing.JFormattedTextField txtCnpjFornecedor;
    private javax.swing.JTextField txtCodigoFornecedor;
    private javax.swing.JFormattedTextField txtCpfFornecedor;
    private javax.swing.JTextField txtEmailFornecedor;
    private javax.swing.JTextField txtEnderecoFornecedor;
    private javax.swing.JTextField txtFornecedor;
    private javax.swing.JTextField txtPesquisarFornecedores;
    private javax.swing.JFormattedTextField txtTelefoneFornecedor;
    private javax.swing.JTextField txtUfFornecedor;
    // End of variables declaration//GEN-END:variables
}