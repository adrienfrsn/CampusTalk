package modele;

import java.util.Base64;

public class GenerateToken {
    public static String generateToken(String email, String password) {
            String emailPwd = email + ":" + password;
            String token = Base64.getEncoder().encodeToString(emailPwd.getBytes());
            return token;
    }

    public static void main(String[] args) {
        System.out.println("token :");
        System.out.println(generateToken("utilisateur3@exemple.com", "motdepasse123"));
    }
}