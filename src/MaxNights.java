public class MaxNights {

    public int maxNights(int[] nights) {
        int book = 0;
        int noBook = 0;

        for (int night : nights) {
            int prevBook = book;
            book = noBook + night;
            noBook = Math.max(prevBook, noBook);
        }

        return Math.max(book, noBook);
    }
}
