package binhtt.utils.password;

import java.security.MessageDigest;

public class PasswordUtils {
    public static final String encode(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes());
        return new String(hash);
    }
}
