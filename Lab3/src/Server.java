import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

class Server {

    private BigInteger N, g, k;
    private Random random;
    private BigInteger A, b, B, u, S, K, clientM, serverM, R;
    private Data data;
    private Client myClient;
    private HashMap<String, Data> baseData = new HashMap<>();

    Server(BigInteger n, BigInteger g, BigInteger k, Random r) {
        N = n;
        this.g = g;
        this.k = k;
        random = r;

        myClient = new Client(n, g, k, r);
    }

    void addUser(String name, String password) {
        myClient.setLogingData(name, password);
        baseData.put(name, new Data(myClient.generateSalt(12), myClient.calculateV()));
        myClient.clear();
    }

    void login(String name, String password) {
        if (baseData.containsKey(name)) {
            myClient.setLogingData(name, password);
            data = baseData.get(name);

            A = myClient.calculateA();
            if (!A.equals(BigInteger.ZERO)) {
                print("A != 0");
                myClient.setSalt(data.getS());
                if (myClient.calculateU(calculateB())) {
                    calculateU();
                    myClient.calculateS();
                    calculateS();

                    clientM = myClient.calculateM();
                    calculateM();

                    if (clientM.equals(serverM)) myClient.checkServerR(calculateR());
                    else print("Invalid password");

                } else print("u == 0");

            } else print("A == 0");

        } else print("User\"" + name + "\" not found");
    }

    private BigInteger generateB() {
        b = BigInteger.probablePrime(256, random);
        return b;
    }

    private BigInteger calculateB() {
        B = (k.multiply(data.getV()).add(g.modPow(generateB(), N))).mod(N);//B=(k*V+(g^b%N))%N
        return B;
    }

    private void calculateU() {
        u = SHA256.hash(A, B);
    }

    private void calculateS() {

        S = A.multiply(data.getV().modPow(u, N)).modPow(b, N);//S=(A*(v^u)%N)^b%N
        calculateK();
    }

    private void calculateK() {

        K = SHA256.hash(S);
    }

    private void calculateM() {

        serverM = SHA256.hash(SHA256.hash(N).xor(SHA256.hash(g)), SHA256.hash(myClient.getName()), data.getS(), A, B, K);
        //H( H(N) XOR H(g), H(I), s, A, B, K)
        print("M="+serverM.toString());
    }

    private BigInteger calculateR() {

        R = SHA256.hash(A, serverM, K);
        return R;
    }

    private void print(String message) {
        System.out.println("Server : " + message + ".");
    }

    private static class Data {
        private BigInteger v;
        private String s;

        Data(String s, BigInteger v) {
            this.v = v;
            this.s = s;
        }

        BigInteger getV() {
            return v;
        }

        String getS() {
            return s;
        }
    }
}
