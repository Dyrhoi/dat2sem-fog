package api;

import java.io.StringWriter;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;
import org.apache.commons.text.WordUtils;
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Util {
    private static final Locale LOCALE = new Locale("da", "DK");

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

    public static String formatDateTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm - dd. MMMM, yyyy").localizedBy(LOCALE);
        return WordUtils.capitalize(time.format(formatter));
    }

    public static String formatDate(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMMM, yyyy").localizedBy(LOCALE);
        return WordUtils.capitalize(time.format(formatter));
    }
}
