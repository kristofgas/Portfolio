import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class HashQualityEvaluation {

    static BitSet bitInput(int N, int seed) {
        BitSet bs = new BitSet(Integer.MAX_VALUE);
        Random rand = new Random(seed);
        int cardinality = 0;
        while (cardinality < N) {
            int v = rand.nextInt(Integer.MAX_VALUE);
            if (!bs.get(v)) {
                bs.set(v);
                cardinality++;
            }
        }
        return bs;
        
    }

    // public static int[] generateInput(int n) {
    //     long seed = 42;
    //     int[] arr = new Random(seed).ints(n, 1, n + 1).toArray();
    //     return arr;
    // }

    // //Added seed as argument - as explained by Matti on Teams
    // static int[] generateInput2(int n, int seed) {
    //     Set<Integer> elements = new HashSet<>();
    //     while (elements.size() != n) {
    //         int randInt = ThreadLocalRandom.current().nextInt(1, seed);
    //         elements.add(randInt);
    //     }
    //     return elements.stream().mapToInt(i -> i).toArray();
    // }

    // //Added new method because the above did not work for me
    // static int[] generateInputUniqueRandom(int n, int seed) {
    //     Random rand = new Random(seed);
    //     Set<Integer> elements = new HashSet<>();
    //     while (elements.size() < n) {
    //         int random_no = rand.nextInt(Integer.MAX_VALUE);
    //             while ((elements.add(random_no) != true) && (random_no != 0)) {}
    //     }
    //     return elements.stream().mapToInt(Integer::intValue).toArray();
    // }

    public static void main(String[] args) {

        HyperLogLog loglog = new HyperLogLog(1024);

        int[] arr = new Random().ints(1000000, 1, 1000000).map(i -> loglog.h(i)).toArray();

        HashMap<Integer, Integer> p_distribution = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            int key = loglog.p(arr[i]); // get p-value
            if (p_distribution.containsKey(key)) {
                int count = p_distribution.get(key);
                count++;
                p_distribution.put(key, count);
            } else {
                p_distribution.put(key, 1);
            }
        }

        /**
         * Print out for testing
         */
        for (int key : p_distribution.keySet()) {
            System.out.println("P-value: " + key + " with distribution: " + p_distribution.get(key));
        }

        /**
         * Write results to file
         */

        String eol = System.getProperty("line.separator");

        try (Writer writer = new FileWriter("hash_quality_results.csv")) {
            writer.append("p-value,frequency").append(eol);
            for (Map.Entry<Integer, Integer> entry : p_distribution.entrySet()) {
                writer.append(Integer.toString(entry.getKey())).append(',').append(Integer.toString(entry.getValue()))
                        .append(eol);
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

}
