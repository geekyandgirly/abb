import java.util.*;

public class PyramidsTransition {
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        Map<String, LinkedHashSet<Character>> map = new HashMap<>();
        for (String a : allowed) {
            String key = a.substring(0, 2);
            Character c = a.charAt(2);
            // System.out.println("Key " + key + " value " + c);
            LinkedHashSet<Character> cSet = map.get(key);
            if (cSet == null) {
                cSet = new LinkedHashSet<>();
            }
            cSet.add(c);
            map.put(key, cSet);
        }

        return buildPyramid(bottom, map);
    }

    private boolean buildPyramid(String bottom, Map<String, LinkedHashSet<Character>> map) {
        List<LinkedHashSet<Character>> topSets = new ArrayList<>();
        for (int i = 0; i < bottom.length() - 1; i++) {
            String key = bottom.substring(i, i + 2);
            LinkedHashSet<Character> cSet = map.get(key);
            if (cSet == null) {
                // System.out.println("didn't find value for key: " + key);
                return false;
            }
            topSets.add(cSet);
        }
        if (topSets.size() == 1) {
            return true;
        }

        List<String> topStrings = new ArrayList<>();
        generateAllStrings(topSets, topStrings, new StringBuilder(), 0);
        for (String topString : topStrings) {
            // System.out.println("top string: " + topString);
            if (buildPyramid(topString, map)) {
                return true;
            }
        }
        return false;
    }

    private void generateAllStrings(List<LinkedHashSet<Character>> sets, List<String> strs, StringBuilder b, int start) {
        if (b.length() == sets.size()) {
            strs.add(b.toString());
            return;
        }
        if (start == sets.size()) {
            return;
        }
        for (int i = start; i < sets.size(); i++) {
            LinkedHashSet<Character> set = sets.get(i);
            for (Character c : set) {
                b.append(c);
                generateAllStrings(sets, strs, b, i+1);
                b.deleteCharAt(b.length() -1);
            }
        }
    }
}
