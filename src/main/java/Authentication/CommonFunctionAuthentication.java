/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Authentication;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class CommonFunctionAuthentication {
    public String hashPassword (String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());//default gensalt is 10;
    }
    
    public boolean checkHashPassword (String password, String hashedPassword){
        return BCrypt.checkpw(password, hashedPassword);
    }
}
