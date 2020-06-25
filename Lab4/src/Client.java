import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Client {
    private BigInteger d, e, f, n;
    private Data publicKey;
    private BigInteger message;
    private String name;
    private int size = 64;

    Client(String name) {
        this.name = name;
    }

    public void generateKeys() {
        Random random = new Random();

        BigInteger p, q;
        //p = BigInteger.probablePrime(size, random);
        //q = BigInteger.probablePrime(size, random);
        p = random(3);
        q = random(3);
        n = p.multiply(q);
        f = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        print("F = " + f + ", p =" + p + ", q = " + q + ", n=" + n);

        calculateE(p, q);
        calculateD();

        print("Public key has generated(e,n)=" + e + ", " + n + ")");
        print("Private key has generated(d,n)=" + d + ", " + n + ")");

        publicKey = new Data(e, n);
    }

    private void calculateD() {
        print("Calculating d..");
        d = e.modInverse(f);
    }

    private void calculateE(BigInteger p, BigInteger q) {
        e = p.max(q);
        print("Calculating e..");
        while (true) {
            if (e.isProbablePrime(1) && e.compareTo(f) < 0 && f.mod(e).compareTo(BigInteger.ZERO) != 0)
                break;
            else
                e = e.add(BigInteger.ONE);
        }
    }


    public void setPublicKey(Data publicKey) {
        this.publicKey = publicKey;
    }

    public Data sendPublicKey() {
        return publicKey;
    }

    public BigInteger encryptMessage() {
        Random random = new Random();
        message = BigInteger.valueOf(random.nextInt(256));
        BigInteger secret = message.modPow(publicKey.e, publicKey.n);//msg^e %n
        print("Message = " + message);
        print("Encrypted message = " + secret);
        return secret;
    }

    public void decryptMessage(BigInteger secretNum) {
        message = secretNum.modPow(d, n);
        print("Decrypted message = " + message);
    }


    public void print(String message) {
        System.out.println(name + ": " + message + ".");
    }

    class Data {
        private BigInteger e, n;

        public Data(BigInteger e, BigInteger n) {
            this.e = e;
            this.n = n;
        }
    }

    private BigInteger random(int accuracy) {   //use Miller-Rabin test
        BigInteger num;

        while (true) {
            boolean isPrime = true;

            do {
                num = new BigInteger(size, new Random());
            } while (num.mod(BigIntegerTWO()).compareTo(BigInteger.ZERO) == 0 || num.compareTo(BigIntegerTWO()) < 0);

            BigInteger d = num.subtract(BigInteger.ONE);

            int s = 0;

            while (d.mod(BigIntegerTWO()).compareTo(BigInteger.ZERO) == 0) {
                d = d.divide(BigIntegerTWO());
                s++;
            }

            for (int i = 0; i <= accuracy; i++) {
                SecureRandom random = new SecureRandom();
                BigInteger a;
                do {
                    a = new BigInteger(size, random);
                } while (a.compareTo(BigIntegerTWO()) >= 0 &&
                        a.compareTo(num.subtract(BigIntegerTWO())) <= 0);

                BigInteger x = a.modPow(d, num);

                if (x.compareTo(BigInteger.ONE) == 0 || x.compareTo(num.subtract(BigInteger.ONE)) == 0)
                    continue;

                for (int r = 0; r < s; r++) {
                    x = x.modPow(BigIntegerTWO(), num);

                    if (x.compareTo(BigInteger.ONE) == 0)
                        isPrime = false;
                    else if (!isPrime || x.compareTo(num.subtract(BigInteger.ONE)) == 0)
                        break;
                }

                if (!isPrime || x.compareTo(num.subtract(BigInteger.ONE)) != 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime)
                return num;
        }
    }

    private BigInteger BigIntegerTWO() {
        return BigInteger.valueOf(2);
    }
}
