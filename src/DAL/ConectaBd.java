package DAL;
import java.sql.*;
import javax.swing.JOptionPane;
//faz a conexao do programa com o banco de dados
public class ConectaBd {
    public static Connection conectabd() throws ClassNotFoundException{
        try {
           Class.forName("org.postgresql.Driver");
           Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Coneu","postgres","pop080470");
           return con;
        }
        catch(SQLException error) {
            JOptionPane.showMessageDialog(null, error);
            return null;
        }
    }
}
