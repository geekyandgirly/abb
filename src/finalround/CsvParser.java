package finalround;

public class CsvParser {
    static enum State {
        FIELD_START,
        REGULAR,
        OUTTER_QUOTE_START,
        IN_QUOTE,
        INNER_QUOTE_START,
        QUOTE_IN_QUOTE,
        INNER_QUOTE_END
    }

    static class ParseError extends Exception {
        ParseError(String msg) {
            super(msg);
        }
    }

    public static String parse(String line) throws ParseError {
        StringBuilder builder = new StringBuilder();
        State state = State.FIELD_START;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            switch (state) {
                case FIELD_START:
                    if (c == ',') {
                        builder.append('|');
                    } else if (c == '"') {
                        state = State.OUTTER_QUOTE_START;
                    } else {
                        builder.append(c);
                        state = State.REGULAR;
                    }
                    break;
                case REGULAR:
                    if (c == ',') {
                        builder.append('|');
                        state = State.FIELD_START;
                    } else if (c == '"') {
                        throw new ParseError("should not start a quote in middle");
                    } else {
                        builder.append(c);
                    }
                    break;
                case OUTTER_QUOTE_START:
                    if (c == '"') {
                        state = State.INNER_QUOTE_START;
                    } else {
                        builder.append(c);
                        state = State.IN_QUOTE;
                    }
                    break;
                case IN_QUOTE:
                    if (c == '"') {
                        if (i < line.length() - 1) {
                            char nextC = line.charAt(i + 1);
                            if (nextC == ',') {
                                builder.append('|');
                                state = State.FIELD_START;
                                i++;
                            } else if (nextC == '"') {
                                state = State.INNER_QUOTE_START;
                            } else {
                                // go to REG_FIELD
                                throw new ParseError("cannot have both quoted fields and regular fields in a record: " + line);
                            }
                        }
                    } else {
                        builder.append(c);
                    }
                    break;
                case INNER_QUOTE_START:
                    if (c != '"') {
                        throw new ParseError("inner quote must be followed by another quote");
                    }
                    builder.append(c);
                    state = State.QUOTE_IN_QUOTE;
                    break;
                case QUOTE_IN_QUOTE:
                    if (c == '"') {
                        if (line.charAt(i - 1) == '"') {
                            throw new ParseError("Too many inner quotes: " + line);
                        } else if (i < line.length() - 1 && line.charAt(i + 1) == '"') {
                            builder.append(c);
                            state = State.INNER_QUOTE_END;
                            i++;
                        } else {
                            throw new ParseError("inner quotes don't match: " + line);
                        }
                    } else {
                        builder.append(c);
                    }
                    break;
                case INNER_QUOTE_END:
                    if (c == '"') {
                        if (i < line.length() - 1) {
                            char nextC = line.charAt(i + 1);
                            if (nextC == ',') {
                                builder.append('|');
                                state = State.FIELD_START;
                                i++;
                            } else {
                                throw new ParseError("field with quote should ends with quote");
                            }
                        }
                    } else {
                        builder.append(c);
                        state = State.IN_QUOTE;
                    }
                    break;
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        try {
            String csv = ",aa,bb,\"cc,dd\",ee";
            System.out.println(csv + " ==> " + CsvParser.parse(csv));

            csv = "\"Alexandra \"\"Alex\"\"\",Menendez,alex.menendez@gmail.com,Miami,1";
            System.out.println(csv + " ==> " + CsvParser.parse(csv));

            csv = "\"\"\"A\"\"\"";
            System.out.println(csv + " ==> " + CsvParser.parse(csv));

            csv = "\"Alexandra \"\"Alex\"\" B\",Menendez,alex.menendez@gmail.com,Miami,1";
            System.out.println(csv + " ==> " + CsvParser.parse(csv));

            csv = "\"Alexandra \"\"Alex\"\" B\"";
            System.out.println(csv + " ==> " + CsvParser.parse(csv));
        } catch(ParseError e) {
            System.out.println(e.getMessage());
        }
    }
}
