import java.util.*;

public class GuessNumber {
    private int count;
    private final String answer;
    private final Map<Integer, Set<Integer>> answerMap = new HashMap<>();
    private final Map<Integer, Integer> guessMap = new HashMap<>();

    public GuessNumber(String answer) {
        count = 0;
        this.answer = answer;
        answerMap.clear();
        guessMap.clear();

        for (int i = 0; i < answer.length(); i++) {
            int key = answer.charAt(i) - '0';
            if (answerMap.containsKey(key)) {
                answerMap.get(key).add(i);
            } else {
                Set<Integer> set = new HashSet<>();
                set.add(i);
                answerMap.put(key, set);
            }

        }
    }

    private int[] server(int[] guess) {
        count++;
        int[] res = new int[2];
        for (int i = 0; i < guess.length; i++) {
            if (answerMap.containsKey(guess[i])) {
                Set<Integer> set = answerMap.get(guess[i]);
                if (set.contains(i)) {
                    // same num at same index
//                    System.out.println("match num " + guess[i] + " at index " + i);
                    res[0] ++;
                }
            }
        }

//      System.out.println("answer: " + answer + " guess: " + intArrToString(guess) + " res: " + res[0] + " " + res[1]);
        return res;
    }

    private String intArrToString(int[] a) {
        return a[0] + "" + a[1] + "" + a[2] + "" + a[3];
    }

    public void guess() {
        // initial guess by entering 1111, 2222... 6666.
        int g = 1;
        while (g <= 6) {
            int[] guess = new int[] {g, g, g, g};
            int[] res = server(guess);
            if (res[0] == 4) {
                System.out.println("Count: " + count + " Guessed lucky: " + intArrToString(guess));
                return;
            }
            if (res[0] > 0 || res[1] > 0) {
                if (guessMap.containsKey(g)) {
                    guessMap.put(g, guessMap.get(g) + res[0] + res[1]);
                } else {
                    guessMap.put(g, res[0] + res[1]);
                }
            }
            g++;
        }

        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(guessMap.entrySet());
        entries.sort(new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        ArrayList<Integer> sortedNums = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : entries) {
            sortedNums.add(entry.getKey());
        }

        int[] answer = new int[4];
        int totalGuessed = 0;

        for (int num : sortedNums) {
            for (int i = 0; i < 4; i++) {
                int[] guess = new int[4];
                if (answer[i] == 0) {
                    guess[i] = num;
                    int[] res = server(guess);
                    if (res[0] == 1) {
                        answer[i] = num;
                        totalGuessed++;
                        if (totalGuessed == 4) {
                            System.out.println("Count: " + count + " Guessed middle: " + intArrToString(answer));
                            return;
                        }
                    }
                }
            }
        }
        System.out.println("Count: " + count + " Guessed final: " + intArrToString(answer));
    }

    public static void main(String[] args) {
        GuessNumber server = new GuessNumber("1234");
        server.guess();

        server = new GuessNumber("3262");
        server.guess();


        server = new GuessNumber("2222");
        server.guess();
    }
}
