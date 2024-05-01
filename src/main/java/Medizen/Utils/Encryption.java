package Medizen.Utils;
import org.mindrot.jbcrypt.BCrypt;

public class Encryption {
    public static String EncryptPassword(String password){

        return BCrypt.hashpw(password, BCrypt.gensalt(12));

    }
    public static boolean checkPassword(String password, String hashedPassword){
        try{

            return BCrypt.checkpw(password, hashedPassword);

        }catch (Exception e){
            return false;
        }
    }
}