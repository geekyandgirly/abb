import java.util.LinkedList;
import java.util.List;

public class TextJustification {

    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new LinkedList<>();
        int i = 0;
        while(i < words.length){
            int wordCnt = 0;
            int width = 0;
            int startIdx = i;
            while(i < words.length){
                width += words[i].length();
                wordCnt++;
                if(width + wordCnt - 1 <= maxWidth){
                    i++;
                }else{
                    width -= words[i].length();
                    wordCnt--;
                    break;
                }
            }
            int endIdx = i - 1;

            String oneline = arangespace(words, startIdx, endIdx, width, maxWidth - width);
            result.add(oneline);
        }
        return result;
    }

    private String arangespace(String[] words, int startIdx, int endIdx, int width, int spacecnt){
        int wordCnt = endIdx - startIdx + 1;
        StringBuilder sb = new StringBuilder();

        if(wordCnt == 1){//one word
            sb.append(words[endIdx]);
            for(int j = 0; j < spacecnt; j++) sb.append(" ");
            return sb.toString();
        }

        //more than one word
        int si = spacecnt / (wordCnt-1);
        int sj = spacecnt % (wordCnt-1);
        for(int wordi = startIdx; wordi <= endIdx; wordi++) {
            sb.append(words[wordi]);
            if (endIdx == words.length - 1) {//last line
                sb.append(" ");
                spacecnt--;
                if (wordi == endIdx) {
                    for (int j = 0; j < spacecnt; j++) sb.append(" ");
                }
            } else {//other line
                if (wordi != endIdx) {
                    for (int j = 0; j < si; j++) sb.append(" ");
                    if (sj > 0) sb.append(" ");
                    sj--;
                }
            }
        }
        return sb.toString();
    }
}
