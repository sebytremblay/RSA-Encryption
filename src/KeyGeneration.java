import java.math.BigInteger;
import java.util.Random;

// Public Key: 100;776825089434135350515002411187;604776604057264423381394446921
// Private Key: 178052088767040206072146250201

public class KeyGeneration {
    
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger publicKey;
    private BigInteger privateKey;
    private Random r = new Random();
    
    public KeyGeneration(int bitLength) {
        // Finds a semiPrime with desired bitLength
        n = semiPrime(bitLength);
        // Finds public and private keys
        BigInteger[] keys = findKeys();
        publicKey = keys[0];
        privateKey = keys[1];
        // Converts found semiPrime and publicKey into strings and returns them in format "bitLength of semiPrime;semiPrime;publicKey"
        System.out.println("Output: " + getOutput());
        System.out.println("Private Key: " + privateKey);
    }
    
    // Returns public key
    public BigInteger getPublic() {
        return publicKey;
    }
    
    // Returns private key
    public BigInteger getPrivate() {
        return privateKey;
    }
    
    // Returns semiprime n 
    public BigInteger getN() {
        return n;
    }
    
    public String getOutput() {
        // Converts n, b, and e into strings
        String bitLength = Integer.toString(n.bitLength());
        String semiPrime = n.toString();
        String e = publicKey.toString();
        // Combines each variable into format "b;n;e"
        String keyOutput = bitLength + ";" + semiPrime + ";" + e;
        // Returns string of this format
        return keyOutput;
     }
    
    // Takes a desired bitLength as a parameter and returns a semiPrime with the provided bitLength
    private BigInteger semiPrime(int bitLength) {
        // Bit length must be greater than 4 so we can divide it by two to generate p and q
        if (bitLength < 4) throw new IllegalArgumentException();
        // Randomly generators two prime numbers that are each half the desired bitlength
        p = BigInteger.probablePrime(bitLength/2, r);
        q = BigInteger.probablePrime(bitLength/2, r);
        // Finds n by n = p*q
        BigInteger n = p.multiply(q);
        // If n does not have the desired bitlength, repeat this generation process
        if (n.bitLength() != bitLength) {
            return semiPrime(bitLength);
        }
        // Returns a BigInteger n that is a semiprime
        return n;
    }
    
    public BigInteger phi() {
        // Finds phi of n by function phi(n) = n - p - q + 1
        BigInteger phi = n.subtract(p).subtract(q).add(BigInteger.ONE);
        // Returns BigIntger phi(n)
        return phi;
    }
 
    private BigInteger[] findKeys() {
        // Empty array to return later
        BigInteger[] keys = new BigInteger[2];
        // Generates random BigInteger with same bitLength as the provided semiprime n
        BigInteger e = new BigInteger(n.bitLength(), r);
        // If e is less than 1, more than phi(n), or not relatively prime to phi(n), generate a new e
        if (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(phi()) >= 0 || !e.gcd(phi()).equals(BigInteger.ONE)) {
            return findKeys();
        }
        // Find a value of d such that d*e is congruent to 1(mod phi(n))
        BigInteger d = e.modInverse(phi());
        // Restarts if e and d are equal
        if (e.compareTo(d) == 0) {
           return findKeys(); 
        }
        // Adds public and private keys to key array if they pass all checks
        keys[0] = e;
        keys[1] = d;
        // Returns keys
        return keys;   
    }
   
    public static void main(String[] args) {
        KeyGeneration test = new KeyGeneration(64);
        String encryptedMessage = sendMessage.encryptMessage(test.getPublic(), test.getN(), "Hi, I am Seby!");
        System.out.println("Encrypted Message: " + encryptedMessage);
        String crackedMessage = breakRSA.output(test.getOutput(), encryptedMessage);
        System.out.println("Cracked Message: " + crackedMessage);
        String decryptedMessage = sendMessage.decryptMessage(test.getPrivate(), test.getN(), encryptedMessage);
        System.out.println("Decrypted Message: " + decryptedMessage);
    }
    
}
