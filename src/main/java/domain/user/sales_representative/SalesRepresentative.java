package domain.user.sales_representative;

import domain.user.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class SalesRepresentative extends User {

    private static final int PASSWORD_ITTERATIONS = 65536;
    private static final int PASSWORD_LENGTH = 256; //32 bytes
    private static final SecretKeyFactory PASSWORD_FACTORY;

    static {
        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        PASSWORD_FACTORY = factory;
    }

    public static byte[] generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static byte[] calculateSecret(byte[] salt, String password){
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt,
                PASSWORD_ITTERATIONS,
                PASSWORD_LENGTH);
        try {
            return PASSWORD_FACTORY.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e){
            throw new RuntimeException(e);
        }
    }

    public static int getPasswordItterations() {
        return PASSWORD_ITTERATIONS;
    }

    public static int getPasswordLength() {
        return PASSWORD_LENGTH;
    }

    public static SecretKeyFactory getPasswordFactory() {
        return PASSWORD_FACTORY;
    }

    private final byte[] salt;
    private final byte[] secret;

    public SalesRepresentative(int id, String firstname, String lastname, String email, String phone, Address address, byte[] salt, byte[] secret) {
        super(id, firstname, lastname, email, phone, address, Type.SALESMAN);
        this.salt = salt;
        this.secret = secret;
    }

    public boolean isPasswordCorrect(String password) {
        return Arrays.equals(this.secret, calculateSecret(salt, password));
    }

    public byte[] getSalt() {
        return salt;
    }

    public byte[] getSecret() {
        return secret;
    }
}