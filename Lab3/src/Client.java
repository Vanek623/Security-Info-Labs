import java.math.BigInteger;
import java.util.Random;

class Client {

    private BigInteger N, g, k;
    private BigInteger x, a, A, B, u, S, K, M, R;
    private String s;
    private Random random;

    private String myName, myPass;

    Client(BigInteger n, BigInteger g, BigInteger k, Random r) {
        N = n;
        this.g = g;
        this.k = k;
        random = r;
    }

    void setLogingData(String myName, String myPass) {
        this.myName = myName;
        this.myPass = myPass;
    }

    BigInteger calculateA() {
        A = g.modPow(generateA(), N);//g^a % N
        return A;
    }

    private BigInteger generateA() {
        a = BigInteger.probablePrime(256, random);
        return a;
    }

    private BigInteger calculateX() {
        x = SHA256.hash(s, myPass);
        return x;
    }

    BigInteger calculateV() {
        return g.modPow(calculateX(), N);
    }

    boolean calculateU(BigInteger B) {
        if (!B.equals(BigInteger.ZERO)) {
            this.B = B;
            print("B != 0");
            u = SHA256.hash(A, B);
        } else {
            print("B == 0");
        }
        return !u.equals(BigInteger.ZERO);
    }

    void calculateS() {
        S = (B.subtract(k.multiply(calculateV()))).modPow(a.add(u.multiply(calculateX())), N);
        calculateK();
    }

    private void calculateK() {
        K = SHA256.hash(S);
    }

    BigInteger calculateM() {

        M = SHA256.hash(SHA256.hash(N).xor(SHA256.hash(g)), SHA256.hash(myName), s, A, B, K);
        print("M="+M.toString());
        return M;
    }

    void checkServerR(BigInteger serverR) {
        if (calculateR().equals(serverR)) {
            print("Connection successful");
        } else {
            print("Disconnect");
        }

        clear();
    }

    private BigInteger calculateR() {

        R = SHA256.hash(A, M, K);
        return R;
    }

    String generateSalt(int size) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String result = "";

        for (int i = 0; i < size; i++) {

            result += alphabet.charAt(random.nextInt(alphabet.length()));
        }

        s = result;
        return result;
    }

    void clear() {
        s = myName = myPass = "";
    }

    void setSalt(String s) {
        this.s = s;
    }

    private void print(String message) {
        System.out.println(myName + " : " + message + ".");
    }

    String getName() {
        return myName;
    }
}
