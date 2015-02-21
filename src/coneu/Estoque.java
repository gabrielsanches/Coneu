package coneu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import Interface.FrmGerenciarProdutos;
import java.sql.SQLException;

public class Estoque {
    Connection conecta; //É o objeto que conecta com o banco de dados
    PreparedStatement pst;
    ResultSet rs;
    int estoqueincial;
    
    public static void main(String args[]){
        SimpleDateFormat formatador;  
        formatador = new SimpleDateFormat("dd/MM/yyyy");
    }
    
    public void SelecionaEstoqueInicial() throws SQLException{
        String sql = "Select estoque from produtos_float where descricao like ?";
        int estoqueinicial = rs.getInt(0); //PRA FAZER O ESTOQUE
    }
    
    public void AtualizaEstoqueAtual(){
        String sql = "Update produtos_float set estoque = ? codigo = ?";
        int estoque;
        //pst.setInt(4, Integer.parseInt(txtEstoqueProduto.getText())+estoqueinicial); // PRA FAZER O ESTOQUE
    }
}