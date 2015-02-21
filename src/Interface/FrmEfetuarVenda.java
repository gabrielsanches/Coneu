package Interface;

import DAL.ConectaBd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class FrmEfetuarVenda extends javax.swing.JInternalFrame {

    Connection conecta; //É o objeto que conecta com o banco de dados
    PreparedStatement pst;
    ResultSet rs;

    public FrmEfetuarVenda() throws ClassNotFoundException {
        initComponents();
        this.setLocation(300, 65);
        conecta = ConectaBd.conectabd();
        listarProdutos();
        listarClientes();
    }

//Métodos para o painel 'Cliente'
    public void listarClientes() {
        String sql = "Select *from clientes order by codigo Asc"; //orderna pelo numero do codigo ascendentemente
        try {
            pst = conecta.prepareStatement(sql);
            rs = pst.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    public void pesquisarClientes() { //Pesquisa o NOME do cliente ao começar a digitar
        String sql = "Select *from clientes where nome like ?";
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, txtPesquisaClienteEfetuarVenda.getText() + "%");//Quando usar backspace funciona tambem por causa do %
            rs = pst.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    public void mostraClientes() { //preenche os campos do painel da venda
        int seleciona = jTable1.getSelectedRow(); //mostra nos campos da tela o que clicar na tabela           
        txtCodigoClienteEfetuarVenda.setText(jTable1.getModel().getValueAt(seleciona, 0).toString());
        txtClienteEfetuarVenda.setText(jTable1.getModel().getValueAt(seleciona, 1).toString());
    }

//Métodos para o painel 'Produto'
    public void listarProdutos() {
        String sql = "Select *from produtos_float order by codigo Asc"; //orderna pelo numero do codigo ascendentemente
        try {
            pst = conecta.prepareStatement(sql);
            rs = pst.executeQuery();
            jTable2.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    public void pesquisarProdutos() { //Pesquisa a DESCRIÇÃO do Produto ao começar a digitar
        String sql = "Select *from produtos_float where descricao like ?";
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, txtPesquisaProdutoEfetuarVenda.getText() + "%");//Quando usar backspace funciona tambem por causa do %
            rs = pst.executeQuery();
            jTable2.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    public void mostraProdutos() { //preenche os campos do painel da venda
        int seleciona = jTable2.getSelectedRow(); //mostra nos campos da tela o que clicar na tabela           
        txtCodigoProdutoEfetuarVenda.setText(jTable2.getModel().getValueAt(seleciona, 0).toString());
        txtProdutoEfetuarVenda.setText(jTable2.getModel().getValueAt(seleciona, 1).toString());
        valor.setText(jTable2.getModel().getValueAt(seleciona, 3).toString());
    }

    public void limparCampos() {
        txtCodigoClienteEfetuarVenda.setText("");
        txtClienteEfetuarVenda.setText("");
        txtCodigoProdutoEfetuarVenda.setText("");
        txtProdutoEfetuarVenda.setText("");
        quantidade_venda.setText("");
        valor.setText("");
        valor_total.setText("");
    }

    public void limparProduto() {
        txtCodigoProdutoEfetuarVenda.setText("");
        txtProdutoEfetuarVenda.setText("");
        quantidade_venda.setText("");
        valor.setText("");
        valor_total.setText("");
    }

//Método para concluir e armazenar nova venda
    public void cadastrarVendas() {
    // Cadastra novo cliente, nenhum campo obrigatório
        // O código é gerado automaticamente pelo banco de dados, em ordem crescente a partir do 1
        String sql = "Insert into vendas(codigocliente, cliente, codigoproduto, produto, quantidade, valorunitariovenda, valortotalvenda) values(?,?,?,?,?,?,?)";
        try {
            pst = conecta.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtCodigoClienteEfetuarVenda.getText())); //inteiro
            pst.setString(2, txtClienteEfetuarVenda.getText()); //varchar
            pst.setInt(3, Integer.parseInt(txtCodigoProdutoEfetuarVenda.getText())); //inteiro
            pst.setString(4, txtProdutoEfetuarVenda.getText());
            pst.setInt(5, Integer.parseInt(quantidade_venda.getText())); //inteiro
            pst.setDouble(6, Double.parseDouble(valor.getText())); //double
            pst.setDouble(7, Double.parseDouble(valor_total.getText())); //double
            pst.execute();
            JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            //listarClientes(); //atualiza a tabela sempre que um novo cliente é cadastrado      
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    private void calcular_valor() {
        valor_total.setText(Float.toString(Float.parseFloat(quantidade_venda.getText()) * Float.parseFloat(valor.getText())));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtPesquisaClienteEfetuarVenda = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtCodigoClienteEfetuarVenda = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtClienteEfetuarVenda = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCodigoProdutoEfetuarVenda = new javax.swing.JTextField();
        txtProdutoEfetuarVenda = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPesquisaProdutoEfetuarVenda = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        quantidade_venda = new javax.swing.JTextField();
        valor = new javax.swing.JTextField();
        valor_total = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Efetuar Venda");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(51, 102, 255))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/search.png"))); // NOI18N
        jLabel2.setText("PESQUISAR");

        txtPesquisaClienteEfetuarVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaClienteEfetuarVendaActionPerformed(evt);
            }
        });
        txtPesquisaClienteEfetuarVenda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaClienteEfetuarVendaKeyReleased(evt);
            }
        });

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

        jLabel1.setText("Código Cliente:");

        txtCodigoClienteEfetuarVenda.setBackground(new java.awt.Color(102, 102, 102));
        txtCodigoClienteEfetuarVenda.setEnabled(false);

        jLabel6.setText("Cliente:");

        txtClienteEfetuarVenda.setBackground(new java.awt.Color(102, 102, 102));
        txtClienteEfetuarVenda.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCodigoClienteEfetuarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtClienteEfetuarVenda))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPesquisaClienteEfetuarVenda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPesquisaClienteEfetuarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCodigoClienteEfetuarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtClienteEfetuarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Produto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(51, 102, 255))); // NOI18N

        jLabel5.setText("Produto:");

        txtCodigoProdutoEfetuarVenda.setBackground(new java.awt.Color(102, 102, 102));
        txtCodigoProdutoEfetuarVenda.setEnabled(false);
        txtCodigoProdutoEfetuarVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoProdutoEfetuarVendaActionPerformed(evt);
            }
        });

        txtProdutoEfetuarVenda.setBackground(new java.awt.Color(102, 102, 102));
        txtProdutoEfetuarVenda.setEnabled(false);
        txtProdutoEfetuarVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProdutoEfetuarVendaActionPerformed(evt);
            }
        });

        jLabel3.setText("Código Produto:");

        txtPesquisaProdutoEfetuarVenda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaProdutoEfetuarVendaKeyReleased(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel7.setText("Quantidade:");

        jLabel8.setText("Valor Unitário:");

        jLabel9.setText("Valor Total:");

        quantidade_venda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                quantidade_vendaKeyReleased(evt);
            }
        });

        valor_total.setEditable(false);
        valor_total.setBackground(new java.awt.Color(102, 153, 255));
        valor_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valor_totalActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/search.png"))); // NOI18N
        jLabel4.setText("PESQUISAR");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtProdutoEfetuarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtPesquisaProdutoEfetuarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtCodigoProdutoEfetuarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel5))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(valor)
                                        .addComponent(valor_total, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(quantidade_venda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisaProdutoEfetuarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(txtCodigoProdutoEfetuarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProdutoEfetuarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(quantidade_venda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(valor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(valor_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 204, 51));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/26_16x16.png"))); // NOI18N
        jButton1.setText("Confirmar venda");
        jButton1.setMargin(new java.awt.Insets(2, 4, 2, 14));
        jButton1.setPreferredSize(new java.awt.Dimension(145, 39));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 0, 0));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/cross.png"))); // NOI18N
        jButton2.setText("Cancelar venda");
        jButton2.setMaximumSize(new java.awt.Dimension(149, 39));
        jButton2.setMinimumSize(new java.awt.Dimension(149, 39));
        jButton2.setPreferredSize(new java.awt.Dimension(149, 39));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(1, 1, 1))
            .addGroup(layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtProdutoEfetuarVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProdutoEfetuarVendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProdutoEfetuarVendaActionPerformed

    private void valor_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valor_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valor_totalActionPerformed

    private void txtCodigoProdutoEfetuarVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoProdutoEfetuarVendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoProdutoEfetuarVendaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cadastrarVendas();
        limparProduto();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtPesquisaClienteEfetuarVendaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaClienteEfetuarVendaKeyReleased
        pesquisarClientes();
    }//GEN-LAST:event_txtPesquisaClienteEfetuarVendaKeyReleased

    private void txtPesquisaProdutoEfetuarVendaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaProdutoEfetuarVendaKeyReleased
        pesquisarProdutos();
    }//GEN-LAST:event_txtPesquisaProdutoEfetuarVendaKeyReleased

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        mostraProdutos();
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        limparCampos();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtPesquisaClienteEfetuarVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaClienteEfetuarVendaActionPerformed
        //pesquisarClientes();
    }//GEN-LAST:event_txtPesquisaClienteEfetuarVendaActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        mostraClientes();
    }//GEN-LAST:event_jTable1MouseClicked

    private void quantidade_vendaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quantidade_vendaKeyReleased
        calcular_valor();
    }//GEN-LAST:event_quantidade_vendaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField quantidade_venda;
    private javax.swing.JTextField txtClienteEfetuarVenda;
    private javax.swing.JTextField txtCodigoClienteEfetuarVenda;
    private javax.swing.JTextField txtCodigoProdutoEfetuarVenda;
    private javax.swing.JTextField txtPesquisaClienteEfetuarVenda;
    private javax.swing.JTextField txtPesquisaProdutoEfetuarVenda;
    private javax.swing.JTextField txtProdutoEfetuarVenda;
    private javax.swing.JTextField valor;
    private javax.swing.JTextField valor_total;
    // End of variables declaration//GEN-END:variables
}
