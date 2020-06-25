import java.math.BigInteger;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        BigInteger q, N, g, k;
        String name, s, pass;

        Random random = new Random();

        q = BigInteger.probablePrime(1024, random);
        //N = 2 * q + 1;
        N = q.multiply(BigInteger.valueOf(2)).add(BigInteger.ONE);

        g = BigInteger.valueOf(2);
        k = SHA256.hash(N, g);

        Server server = new Server(N, g, k, random);

        server.addUser("user1", "useless");

        server.login("user1", "yseless");
        server.login("yser1", "useless");
        server.login("user1", "useless");
    }
}
