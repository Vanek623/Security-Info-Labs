import java.util.*;
import java.util.stream.Collectors;

public class Caesar {
    private HashMap<Character,Integer> statistic;
    private int simCount = 0;
    private float maxPart;

    Caesar(String text){
        statistic = generateStatistic(text,true);
    }

    public String Encrypt(String messIn, int key){
        System.out.println("Start encrypting with key " + key);
        char simbols[] = new char[messIn.length()];

        for (int i = 0; i < messIn.length(); i++)
            simbols[i]= (char)((int)messIn.charAt(i) + key);
        System.out.println("Encrypting has been completed");
        return new String(simbols);
    }

    public String Decrypt(String messIn, int key){
        System.out.println("Start decrypting with key " + key);

        char simbols[] = new char[messIn.length()];

        for (int i = 0; i < messIn.length(); i++) {
            simbols[i]= (char)((int)messIn.charAt(i) - key);
        }
        System.out.println("Decrypting has been completed");
        return new String(simbols);
    }

    public int findKey(String messIn){
        System.out.println("Finding key");

        HashMap<Character,Integer> table = generateStatistic(messIn,false);
        char msgsim = maxPopular(table);
        char textSim = maxPopular(statistic);

        System.out.println("Key was found: " + (msgsim - textSim));
        return (msgsim - textSim);
    }

    private HashMap<Character, Integer> generateStatistic(String msg, boolean isStat){
        HashMap<Character,Integer> table = new HashMap<>();

        for (int i = 0; i < msg.length(); i++) {
            char sim = msg.charAt(i);
            Integer freq = table.get(sim);
            if(Character.isLetter(sim)) {
                table.put(sim, freq == null ? 1 : freq + 1);
                if(isStat)
                    simCount++;
            }
        }

        return  table;
    }

    private char maxPopular(HashMap<Character,Integer> table){
            Map.Entry<Character, Integer> max = null;

            for (Map.Entry<Character, Integer> entry : table.entrySet()) {
                if (max == null || entry.getValue().compareTo(max.getValue()) > 0)
                    max = entry;
            }
            maxPart = max.getKey() / (float) simCount;

            return max.getKey();
    }

    public void showMaxPopular() {
        System.out.println(maxPopular(statistic)+ ":" +maxPart*100 + "%");
    }
    public void showSimbolCount(){
        System.out.println(simCount);
    }

    public void showStat(){
        Map<Character,Integer> sorted =
                statistic.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(100)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        for (Map.Entry<Character, Integer> entry : sorted.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue()*100/(float)simCount + "%");
        }

        //System.out.println("Count of characters is " + simCount);
    }
}
