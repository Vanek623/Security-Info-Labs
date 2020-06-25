import java.math.BigInteger;
import java.util.Random;

public class Member {
    private int g,p;
    private int num;
    private int privateKey;
    private int publicKey;
    private String name;

    public Member( String name) {
        this.name = name;
    }

    public void createGP(){
        Random random = new Random();

        p = random.nextInt(10000) + 1;

        while (!isRoot(p)){
            p = random.nextInt(10000) + 1;
        }
        g = generateG(p);
        num = random.nextInt(10000) + 1;

        publicKey = generatePublicKey();
    }

    private int generateG(int p){
        //System.out.println("Generating g...");
        for (int i = 2; i < 100; i++) {

            if(BigInteger.valueOf(i).pow(p-1).mod(BigInteger.valueOf(p)).compareTo(BigInteger.ONE) == 0) {
                //System.out.println("g was found!");
                return i;
            }
        }

        System.out.println("g wasn't found!");
        return -1;
    }

    private boolean isRoot(int inputNum){
        if (inputNum <= 3 || inputNum % 2 == 0)
            return inputNum == 2 || inputNum == 3; //this returns false if number is <=1 & true if number = 2 or 3
        int divisor = 3;
        while ((divisor <= Math.sqrt(inputNum)) && (inputNum % divisor != 0))
            divisor += 2; //iterates through all possible divisors
        return inputNum % divisor != 0; //returns true/false
    }

    public int getG() {
        return g;
    }

    public int getP() {
        return p;
    }

    public void setGP(int g, int p) {
        this.g = g;
        this.p = p;
        Random random = new Random();
        num = random.nextInt(10000) + 1;
        publicKey = generatePublicKey();
    }

    private int generatePublicKey(){
        System.out.println(name + ": P = " + p + "; G = " + g + "; A = " + num);
        return BigInteger.valueOf(g).pow(num).mod(BigInteger.valueOf(p)).intValue();    //g^num % p
    }

    public void generatePrivateKey(int publicKey){
        privateKey = BigInteger.valueOf(publicKey).pow(num).mod(BigInteger.valueOf(p)).intValue();  //pubK ^ num % p
        System.out.println(name + "'s public key is " + publicKey);
        System.out.println(name + "'s private key is " + privateKey);
    }

    public int getPublicKey(){
        return publicKey;
    }
}
