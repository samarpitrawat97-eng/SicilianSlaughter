public class Move {
    public int from;
    public int to;
    public char promotion;
    public int score;
    public Move(int from, int to, char promotion) {
        this.from = from;
        this.to = to;
        this.promotion = promotion;
    }

    public String toString() {
        String f = "" + (char) ('a' + (from % 8)) + (8 - (from / 8));
        String t = "" + (char) ('a' + (to % 8)) + (8 - (to / 8));
        String p = promotion == ' ' ? "" : String.valueOf(promotion);
        return f + t + p;
    }
}
