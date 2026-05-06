public class Board {
    public char[] squares = new char[64];
    public boolean isWhiteTurn = true;

    public Board() {
        reset();
    }

    public void reset() {
        String startFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        parseFen(startFen);
    }

    public void parseFen(String fen) {
        String[] parts = fen.split(" ");
        int rank = 0, file = 0;

        for (int i = 0; i < 64; i++) squares[i] = ' ';

        for (int i = 0; i < parts[0].length(); i++) {
            char c = parts[0].charAt(i);
            if (c == '/') {
                rank++;
                file = 0;
            } else if (Character.isDigit(c)) {
                file += Character.getNumericValue(c);
            } else {
                squares[rank * 8 + file] = c;
                file++;
            }
        }
        isWhiteTurn = parts[1].equals("w");
    }

    public void makeMove(String uciMove) {
        int fromCol = uciMove.charAt(0) - 'a';
        int fromRow = 8 - Character.getNumericValue(uciMove.charAt(1));
        int toCol = uciMove.charAt(2) - 'a';
        int toRow = 8 - Character.getNumericValue(uciMove.charAt(3));

        int from = fromRow * 8 + fromCol;
        int to = toRow * 8 + toCol;

        char piece = squares[from];
        squares[from] = ' ';
        squares[to] = piece;

        if (uciMove.length() == 5) {
            squares[to] = isWhiteTurn ? Character.toUpperCase(uciMove.charAt(4)) : Character.toLowerCase(uciMove.charAt(4));
        }

        isWhiteTurn = !isWhiteTurn;
    }
}