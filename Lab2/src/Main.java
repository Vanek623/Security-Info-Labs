public class Main {
    public static void main(String[] args) {
        Member Alice = new Member("Alice");
        Member Max = new Member("Max");

        Alice.createGP();
        Max.setGP(Alice.getG(),Alice.getP());

        Alice.generatePrivateKey(Max.getPublicKey());
        Max.generatePrivateKey(Alice.getPublicKey());
    }
}
