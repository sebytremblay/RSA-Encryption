import java.math.BigInteger;
import java.util.BitSet;

public class sendMessage {
    public static String encryptMessage(BigInteger key, BigInteger n, String message) {
        // Converts string into byte array
        byte[] byteArray = message.getBytes();
        // Converts byte array into bit set
        BitSet bitSet = BitSet.valueOf(byteArray);
        // Converts bit set into long array
        long[] longArray = bitSet.toLongArray();
        // Creates empty array of strings to fill once we converts longs into strings
        String[] encryptedArray = new String[longArray.length];
        // Converts all longs into BigIntegers, encrypts them then converts them to a String, and then appends them to the previous String array
        for (int i = 0; i < longArray.length; i++) {
            // Converts long into BigInt
            BigInteger temp = BigInteger.valueOf(longArray[i]);
            // Encrypts each BigInt
            BigInteger encryptedMessage = temp.modPow(key, n);
            // Converts each BigInt into a string and appends to array
            encryptedArray[i] = encryptedMessage.toString();
        }
        // Creates empty string to return later
        String encryptedMessage = new String();
        // Combines every string into a single string with a "," between each value
        for (int i = 0; i < encryptedArray.length; i++) {
            encryptedMessage = encryptedMessage.concat(encryptedArray[i]);
            // Adds coma after each string as long as it is not the last string
            if ((i + 1) != encryptedArray.length) {
                encryptedMessage = encryptedMessage.concat(",");
            }
        }
        // Returns string containing all encrypted keys separated by commas
        return encryptedMessage;
    }
    
    // Decrypts message with provided decrypt key, semiprime n, and message
    public static String decryptMessage(BigInteger key, BigInteger n, String codedMessage) {
        // Splits string into each p
        String[] characters = codedMessage.split(",");
        // Empty array of long characters to fill with decrypted longs
        long[] decryptedLongs = new long[characters.length];
        // Convert each character into a big integer, decrypts it, converts it to long, appends to long array
        for (int i = 0; i < characters.length; i++) {
            // Converts character into bigInteger
            BigInteger encryptedBig = new BigInteger(characters[i]);
            // Decrypts bigInteger
            BigInteger decryptedBig = encryptedBig.modPow(key, n);
            // Converts decrypted bigInteger into long
            long decryptedLong = decryptedBig.longValueExact();
            // Appends long value to long array
            decryptedLongs[i] = decryptedLong;
        }
        // Converts long array into BitSet
        BitSet bitSet = BitSet.valueOf(decryptedLongs);
        // Converts BitSet into ByteArray
        byte[] byteArray = bitSet.toByteArray();
        // Converts ByteArray to String
        String decryptedMessage = new String(byteArray);
        // Returns decrypted message
        return decryptedMessage;
    }
}


