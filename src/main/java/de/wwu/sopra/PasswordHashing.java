package de.wwu.sopra;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Klasse verwaltet das Hashing und das Validieren von Passwörtern
 */
public class PasswordHashing {
    /**
     * Wrapper für Salt- und Hash-Wert
     */
    public static class PasswordHash {
        /**
         * Salt Wert
         */
        public final String salt;
        /**
         * Hash Wert
         */
        public final String hash;

        /**
         * PasswortHash, welcher Salt- und Hash-Wert wrappt.
         * @param salt Salt Wert
         * @param hash Hash Wert
         */
        public PasswordHash(String salt, String hash) {
            this.salt = salt;
            this.hash = hash;
        }
    }

    /**
     * Hasht ein Passwort mit einem zufällig generiertem Salt-Wert.
     *
     * @param password Zu hashendes Passwort
     * @return Passwort-Hash und Salt-Wert
     */
    public static PasswordHash hashPassword(String password) {
        var random = new SecureRandom();
        var salt = new byte[16];
        random.nextBytes(salt);

        try {
            var spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 64 * 8);
            var factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            var hash = factory.generateSecret(spec).getEncoded();
            return new PasswordHash(toHex(salt), toHex(hash));
        } catch (Exception ignored) {}

        return null;
    }

    /**
     * Validiert ein passwort anhand seines vorliegenden Hashes
     *
     * @param password Zu validierendes Passwort
     * @param passwordHash Vorliegender Hash
     * @return true: Passwort korrekt, false: sonst
     */
    public static boolean validatePassword(String password, PasswordHash passwordHash) {
        var salt = fromHex(passwordHash.salt);
        var hash = fromHex(passwordHash.hash);

        var spec = new PBEKeySpec(password.toCharArray(), salt, 65536, hash.length * 8);
        try {
            var factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            var testHash = factory.generateSecret(spec).getEncoded();

            var diff = hash.length ^ testHash.length;
            for (var i = 0; i < hash.length && i < testHash.length; i++)
                diff |= hash[i] ^ testHash[i];

            return diff == 0;
        } catch (Exception ignored) {}

        return false;
    }

    /**
     * Wandelt einen byte-Array in einen HEX-Encoded String um.
     *
     * @param array Byte-Array zum HEX-Encoden
     * @return HEX-Encodeter String
     */
    private static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);

        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }


    /**
     * Wandelt einen HEX-Encoded String in ein byte-Array um.
     *
     * @param hex HEX-Encodeter String
     * @return byte-Array
     */
    private static byte[] fromHex(String hex)
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
