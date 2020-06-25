import java.io.*;
import java.util.Scanner;

public class Main {

    static String pathIn = "C:\\Users\\van62\\YandexDisk\\Учебные материалы\\7_semak\\SecurityInfo\\Lab1\\Input.txt";
    static String pathOut = "C:\\Users\\van62\\YandexDisk\\Учебные материалы\\7_semak\\SecurityInfo\\Lab1\\Output.txt";
    static String pathTom = "C:\\Users\\van62\\YandexDisk\\Учебные материалы\\7_semak\\SecurityInfo\\Lab1\\Tom1.txt";

    public static void main(String[] args) {
        String input, output, tom;
        input = "";

        try {

            input = read(pathIn);
            tom = read(pathTom);

            Caesar caesar = new Caesar(tom);
            //System.out.println(input);
            output = caesar.Encrypt(input,1);
            //System.out.println(output);
            //System.out.println(caesar.Decrypt(output, 1));
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathOut));
            System.out.println("File opened!");
            writer.write(output);
            System.out.println("Load completed!");


            writer.close();
            System.out.println(caesar.Decrypt(output,caesar.findKey(output)));
            //caesar.showMaxPopular();
            caesar.showStat();
            //caesar.showSimbolCount();
        } catch (IOException e){
            System.out.println("Output file not found!");
        }



    }

    private static String read(String path){
        StringBuilder stringBuilder = new StringBuilder();

        try {
            Scanner scanner = new Scanner(new File(path));
            System.out.println("File opened!");

            while (scanner.hasNextLine())
                stringBuilder.append(scanner.nextLine()+"\n");

            System.out.println("Read completed!");
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found!");
        }


        return stringBuilder.toString();
    }
}
