package Interface;

import DAL.ConectaBd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class FrmEfetuarCompra extends javax.swing.JInternalFrame {

    Connection conecta; //É o objeto que conecta com o banco de dados
    PreparedStatement pst;
    ResultSet rs;

    public FrmEfetuarCompra() throws ClassNotFoundException {
        initComponents();
        this.setLocation(300, 65);
        conecta = ConectaBd.conectabd();
        listarProdutos();
        listarFornecedores();
        valor_total.setEditable(false);
    }

    //Métodos para o painel 'Fornecedor'
    public void listarFornecedores() {
        String sql = "Select *from fornecedores order by codigo Asc"; //orderna pelo numero do codigo ascendentemente
        try {
            pst = conecta.prepareStatement(sql);
            rs = pst.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    public void pesquisarFornecedores() { //Pesquisa o NOME do fornecedor ao começar a digitar
        String sql = "Select *from fornecedores where fornecedor like ?";
        try {
            pst = conecta.prepareStatement(sql);
            pst.setString(1, txtPesquisaFornecedorEfetuarCompra.getText() + "%");//Quando usar backspace funciona tambem por causa do %
            rs = pst.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    public void mostraFornecedores() { //preenche os campos do painel da venda
        int seleciona = jTable1.getSelectedRow(); //mostra nos campos da tela o que clicar na tabela           
        txtCodigoFornecedorEfetuarCompra.setText(jTable1.getModel().getValueAt(seleciona, 0).toString());
        txtFornecedorEfetuarCompra.setText(jTable1.getModel().getValueAt(seleciona, 1).toString());
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
            pst.setString(1, txtPesquisaProdutoEfetuarCompra.getText() + "%");//Quando usar backspace funciona tambem por causa do %
            rs = pst.executeQuery();
            jTable2.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    public void mostraProdutos() { //preenche os campos do painel da venda
        int seleciona = jTable2.getSelectedRow(); //mostra nos campos da tela o que clicar na tabela           
        txtCodigoProdutoEfetuarCompra.setText(jTable2.getModel().getValueAt(seleciona, 0).toString());
        txtProdutoEfetuarCompra.setText(jTable2.getModel().getValueAt(seleciona, 1).toString());
        valor.setText(jTable2.getModel().getValueAt(seleciona, 2).toString());
        calcular_valor();
    }

    public void limparCampos() {
        txtCodigoFornecedorEfetuarCompra.setText("");
        txtFornecedorEfetuarCompra.setText("");
        txtCodigoProdutoEfetuarCompra.setText("");
        txtProdutoEfetuarCompra.setText("");
        quantidade_produto.setText("");
        valor.setText("");
        valor_total.setText("");
    }

    public void limparProduto() {
        txtCodigoProdutoEfetuarCompra.setText("");
        txtProdutoEfetuarCompra.setText("");
        quantidade_produto.setText("");
        valor.setText("");
        valor_total.setText("");
    }

    //Método para concluir e armazenar nova compra
    public void cadastrarCompras() {
        // Cadastra novo cliente, nenhum campo obrigatório
        // O código é gerado automaticamente pelo banco de dados, em ordem crescente a partir do 1
        String sql = "Insert into compras(codigofornecedor, fornecedor, codigoproduto, produto, quantidade, valorunitariocompra, valortotalcompra, data_compra) values(?,?,?,?,?,?,?,?)";
        try {

            if (txtCodigoFornecedorEfetuarCompra.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Precisa ser escolhido um fornecedor!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (quantidade_produto.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Quantidade de produtos nescessária!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    pst = conecta.prepareStatement(sql);
                    pst.setInt(1, Integer.parseInt(txtCodigoFornecedorEfetuarCompra.getText())); //inteiro
                    pst.setString(2, txtFornecedorEfetuarCompra.getText());
                    pst.setInt(3, Integer.parseInt(txtCodigoProdutoEfetuarCompra.getText())); //inteiro
                    pst.setString(4, txtProdutoEfetuarCompra.getText());
                    pst.setInt(5, Integer.parseInt(quantidade_produto.getText())); //inteiro
                    pst.setDouble(6, Double.parseDouble(valor.getText())); //double
                    pst.setDouble(7, Double.parseDouble(valor_total.getText())); //double
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    java.util.Date date = new java.util.Date();
                    Timestamp t = new Timestamp(date.getTime());
                    pst.setString(8, df.format(t)); //double
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Compra realizada com sucesso!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
                    //listarClientes(); //atualiza a tabela sempre que um novo cliente é cadastrado   
                    baixaCompra();
                }

            }

        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtCodigoFornecedorEfetuarCompra = new javax.swing.JTextField();
        txtPesquisaFornecedorEfetuarCompra = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtFornecedorEfetuarCompra = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCodigoProdutoEfetuarCompra = new javax.swing.JTextField();
        txtProdutoEfetuarCompra = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPesquisaProdutoEfetuarCompra = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        quantidade_produto = new javax.swing.JTextField();
        valor = new javax.swing.JTextField();
        valor_total = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Efetuar Compra");
        setToolTipText("");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fornecedor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(51, 102, 255))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(255, 51, 51));
        jPanel1.setToolTipText("");
        jPanel1.setName(""); // NOI18N

        txtCodigoFornecedorEfetuarCompra.setBackground(new java.awt.Color(102, 102, 102));
        txtCodigoFornecedorEfetuarCompra.setEnabled(false);
        txtCodigoFornecedorEfetuarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoFornecedorEfetuarCompraActionPerformed(evt);
            }
        });

        txtPesquisaFornecedorEfetuarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaFornecedorEfetuarCompraActionPerformed(evt);
            }
        });
        txtPesquisaFornecedorEfetuarCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaFornecedorEfetuarCompraKeyReleased(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/search.png"))); // NOI18N
        jLabel1.setText("PESQUISAR");

        txtFornecedorEfetuarCompra.setBackground(new java.awt.Color(102, 102, 102));
        txtFornecedorEfetuarCompra.setEnabled(false);
        txtFornecedorEfetuarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFornecedorEfetuarCompraActionPerformed(evt);
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

        jLabel2.setText("Código Fornecedor:");

        jLabel4.setText("Fornecedor:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigoFornecedorEfetuarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFornecedorEfetuarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPesquisaFornecedorEfetuarCompra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisaFornecedorEfetuarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigoFornecedorEfetuarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtFornecedorEfetuarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Produto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(51, 102, 255))); // NOI18N

        jLabel5.setText("Produto:");

        txtCodigoProdutoEfetuarCompra.setBackground(new java.awt.Color(102, 102, 102));
        txtCodigoProdutoEfetuarCompra.setEnabled(false);

        txtProdutoEfetuarCompra.setBackground(new java.awt.Color(102, 102, 102));
        txtProdutoEfetuarCompra.setEnabled(false);
        txtProdutoEfetuarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProdutoEfetuarCompraActionPerformed(evt);
            }
        });

        jLabel3.setText("Código Produto:");

        txtPesquisaProdutoEfetuarCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaProdutoEfetuarCompraKeyReleased(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/search.png"))); // NOI18N
        jLabel6.setText("PESQUISAR");

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

        quantidade_produto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                quantidade_produtoKeyReleased(evt);
            }
        });

        valor_total.setEditable(false);
        valor_total.setBackground(new java.awt.Color(102, 153, 255));
        valor_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valor_totalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtPesquisaProdutoEfetuarCompra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel3))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(valor, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                .addComponent(valor_total)
                                .addComponent(quantidade_produto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtCodigoProdutoEfetuarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtProdutoEfetuarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisaProdutoEfetuarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(txtCodigoProdutoEfetuarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProdutoEfetuarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(quantidade_produto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jButton1.setText("Confirmar compra");
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
        jButton2.setText("Cancelar compra");
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtProdutoEfetuarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProdutoEfetuarCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProdutoEfetuarCompraActionPerformed

    private void txtCodigoFornecedorEfetuarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoFornecedorEfetuarCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoFornecedorEfetuarCompraActionPerformed

    private void txtPesquisaFornecedorEfetuarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaFornecedorEfetuarCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisaFornecedorEfetuarCompraActionPerformed

    private void valor_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valor_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valor_totalActionPerformed

    private void txtPesquisaFornecedorEfetuarCompraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaFornecedorEfetuarCompraKeyReleased
        pesquisarFornecedores();
    }//GEN-LAST:event_txtPesquisaFornecedorEfetuarCompraKeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        mostraFornecedores();
    }//GEN-LAST:event_jTable1MouseClicked

    private void txtPesquisaProdutoEfetuarCompraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaProdutoEfetuarCompraKeyReleased
        pesquisarProdutos();
    }//GEN-LAST:event_txtPesquisaProdutoEfetuarCompraKeyReleased

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        mostraProdutos();
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        limparCampos();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtFornecedorEfetuarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFornecedorEfetuarCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFornecedorEfetuarCompraActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cadastrarCompras();

        limparProduto();
    }//GEN-LAST:event_jButton1ActionPerformed

    public void baixaCompra() {
        String sql = "select estoque from produtos_float where codigo=" + Integer.parseInt(txtCodigoProdutoEfetuarCompra.getText());
        int estoque;
        try {
            Statement comando = conecta.createStatement();
            ResultSet rs = comando.executeQuery(sql);
            rs.next();
            estoque = rs.getInt(1);

            sql = "Update produtos_float set estoque=? where codigo=?";

            pst = conecta.prepareStatement(sql);
            pst.setInt(1, estoque + Integer.parseInt(quantidade_produto.getText()));
            pst.setInt(2, Integer.parseInt(txtCodigoProdutoEfetuarCompra.getText()));
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FrmEfetuarVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void quantidade_produtoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quantidade_produtoKeyReleased
        calcular_valor();
    }//GEN-LAST:event_quantidade_produtoKeyReleased

    private void calcular_valor() {
        if (!quantidade_produto.getText().equals("") && !valor.getText().equals(""))
            valor_total.setText(Float.toString(Float.parseFloat(quantidade_produto.getText()) * Float.parseFloat(valor.getText())));
    }

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
    private javax.swing.JTextField quantidade_produto;
    private javax.swing.JTextField txtCodigoFornecedorEfetuarCompra;
    private javax.swing.JTextField txtCodigoProdutoEfetuarCompra;
    private javax.swing.JTextField txtFornecedorEfetuarCompra;
    private javax.swing.JTextField txtPesquisaFornecedorEfetuarCompra;
    private javax.swing.JTextField txtPesquisaProdutoEfetuarCompra;
    private javax.swing.JTextField txtProdutoEfetuarCompra;
    private javax.swing.JTextField valor;
    private javax.swing.JTextField valor_total;
    // End of variables declaration//GEN-END:variables

}
