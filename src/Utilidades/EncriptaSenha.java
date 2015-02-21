/*package Utilidades;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

//ENCRIPTA A SENHA
//Depois de encriptografado nao tem mais como saber qual é asenha

public class EncriptaSenha {
    public static String encripita(String Senha){
        try{
            MessageDigest digest = MessageDigest.getInstance("MD5"); //digestor criado com o algoritmo MD5
            digest.update(senha.getBytes()); //o metodo update adiciona bytes a mensagem a ser criptografada
            BASE64Encoder encoder = new BASE64Encoder(); //cria um encoder para impedir que a senha ecriptada a apareça
            return encoder.encode(digest.digest()); // digest.digest retorna a mensagem encriptada e o encoder.encode substitui os caracteres problemáticos
        }catch (NoSuchAlgorithmException ns){
            ns.printStackTrace();
            return senha;
        }
    }

    public static String encripta(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}*/
