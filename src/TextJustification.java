import java.util.ArrayList;
import java.util.List;

public class TextJustification {

    public static List<String> fullJustify(String[] words, int maxWidth) {
        // First pack each line greedy
        List<String> ret = new ArrayList<>();
        int count = 0;
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
//            System.out.println("processing: " + word + ", current line: " + line.toString());
            if (word.length() > maxWidth) {
                // handle special case here.
                return ret;
            }
            line.append(word);
            count += word.length();
            if (i == words.length - 1) {
                ret.add(justifySpaces(line.toString(), maxWidth, true));
            } else {
                if (count + words[i + 1].length() >= maxWidth) {
                    ret.add(justifySpaces(line.toString(), maxWidth, false));
                    line = new StringBuilder();
                    count = 0;
                } else {
                    line.append(" ");
                    count++;
                }
            }
        }
        return ret;
    }

    private static String justifySpaces(String s, int maxWidth, boolean isLastLine) {
        StringBuilder b = new StringBuilder();
        String[] words = s.split(" ");

        // if it's last line or it's only one word
        if (isLastLine || words.length == 1) {
            b.append(s);
            for (int j = 0; j < maxWidth - s.length(); j++) {
                b.append(" ");
            }
        } else {
            int spaceCnt = maxWidth - s.length() + words.length - 1;
            int sEven = spaceCnt / (words.length - 1);
            int sExtra = spaceCnt % (words.length - 1);
            System.out.println("spaceCount= " + spaceCnt + " sEven=" + sEven +" sExtra=" + sExtra);
            for (int i = 0; i < words.length - 1; i++) {
                b.append(words[i]);
                for (int j = 0; j < sEven; j++) {
                    b.append(" ");
                }
                if (sExtra > 0) {
                    b.append(" ");
                    sExtra--;
                }
            }
            b.append(words[words.length - 1]);
        }
        return b.toString();
    }

    public static void main(String[] args) {
        String[] words = new String[]{"What","must","be","acknowledgment","shall","be"};
        List<String> justified = fullJustify(words, 16);
        for (String s : justified) {
            System.out.println("\"" + s + "\"");
        }

        words = new String[]{"Science","is","what","we","understand","well","enough","to","explain",
                "to","a","computer.","Art","is","everything","else","we","do"};

        justified = fullJustify(words, 20);
        for (String s : justified) {
            System.out.println("\"" + s + "\"");
        }

//        String s = "[{text: \"first word\"}, {text:\"my second sentence\"}, {text:\"now it's third\"}]";
//        JSONArray jsonArray = new JSONArray(s);
//        List<String> text = new ArrayList<>();
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject object = (JSONObject) jsonArray.get(i);
//            text.add(object.getString("text"));
//        }
    }
}
