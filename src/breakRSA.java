import java.math.BigInteger;

public class breakRSA {
    
    public static String output(String entry, String message) {
        // Splits input into bitLength, semiPrime, and public Key
        String[] temp = entry.split(";");
        String bitLength = temp[0];
        BigInteger semiPrime = new BigInteger(temp[1]);
        BigInteger publicKey = new BigInteger(temp[2]);
        // Finds factors p and q using fermats
        BigInteger[] factors = fermats(semiPrime, bitLength);
        BigInteger p = factors[0];
        BigInteger q = factors[1];
        // Finds privateKey using method from KeyGeneration
        BigInteger privateKey = publicKey.modInverse(phi(semiPrime,p,q));
        // Returns decoded message
        return sendMessage.decryptMessage(privateKey, semiPrime, message);
    }
    
    private static BigInteger[] fermats(BigInteger n, String bitLength) {
        // Starts x at sqrt n and y at 0
        BigInteger x = n.sqrt();
        BigInteger y = BigInteger.ZERO;
        // Finds value of x^2 - y^2
        BigInteger value = amount(x,y);
        // Loops until x^2 - y^2 == n
        while (value.compareTo(n) != 0) {
            // If x^2 -y^2 < n, add one to x and re-calculate value of x^2 - y^2 
            if (value.compareTo(n) < 0) {
                x = x.add(BigInteger.ONE);
                value = amount(x,y);
            }
            // If x^2 -y^2 > n, add one to y and re-calculate value of x^2 - y^2 
            if (value.compareTo(n) > 0) {
               y = y.add(BigInteger.ONE);
               value = amount(x,y);
            }
        }
        // Find p by p = x + y
        BigInteger p = x.add(y);
        // Find q by q = x - y
        BigInteger q = x.subtract(y);
        // Returns p and q in array factors
        BigInteger[] factors = new BigInteger[2];
        factors[0] = p;
        factors[1] = q;
        return factors;    
    }
    
    // Finds and returns value of x^2 - y^2
    private static BigInteger amount(BigInteger x, BigInteger y) {
        BigInteger x1 = x.pow(2);
        BigInteger y1 = y.pow(2);
        return x1.subtract(y1);
    }
    
    // Finds and returns value of phi(n) given n, p, and q
    private static BigInteger phi(BigInteger n, BigInteger p, BigInteger q) {
        // Finds phi of n by function phi(n) = n - p - q + 1
        BigInteger phi = n.subtract(p).subtract(q).add(BigInteger.ONE);
        // Returns BigIntger phi(n)
        return phi;
    }
        
    public static void main(String[] args) {
        String entry = "50;563013064761493;282169471635199";
        String message = "337300504997709";
        System.out.println(entry);
        System.out.println("Decoded Message: " + output(entry,message));
    }

}
