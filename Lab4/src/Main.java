public class Main {
    public static void main(String[] args) {
        Client sender, receiver;

        sender = new Client("Sender");
        receiver = new Client("Receiver");

        receiver.generateKeys();
        sender.setPublicKey(receiver.sendPublicKey());
        receiver.decryptMessage(sender.encryptMessage());
    }
}
