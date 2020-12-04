package api;

import java.security.SecureRandom;
import java.util.Base64;

public class Util {
    public static String generateSecureToken() {
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        SecureRandom random = new SecureRandom();

        byte[] bytes = new byte[24];
        random.nextBytes(bytes);
        return encoder.encodeToString(bytes);
    }

    public static String phoneNumberStrip(String phonenumber) {
        phonenumber = phonenumber.strip();
        if(phonenumber.length() > 8) phonenumber = phonenumber.substring(3);
        return phonenumber.replaceAll("[\\s-]+", "");
    }
}