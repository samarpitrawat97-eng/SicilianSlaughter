import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoveGenerator {
    private static final int[] DIR_X = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static final int[] DIR_Y = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] KNIGHT_X = {-2, -1, 1, 2, -2, -1, 1, 2};
    private static final int[] KNIGHT_Y = {-1, -2, -2, -1, 1, 2, 2, 1};
    private final Random random = new Random();

    private static final int[] CENTER_BONUS = {
            0,  0,  0,  0,  0,  0,  0,  0,
            0,  0,  0,  0,  0,  0,  0,  0,
            0,  0, 10, 20, 20, 10,  0,  0,
            0, 10, 30, 40, 40, 30, 10,  0,
            0, 10, 30, 40, 40, 30, 10,  0,
            0,  0, 10, 20, 20, 10,  0,  0,
            0,  0,  0,  0,  0,  0,  0,  0,
            0,  0,  0,  0,  0,  0,  0,  0
    };

    public List<Move> generateMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        boolean isWhite = board.isWhiteTurn;
        for (int i = 0; i < 64; i++) {
            char piece = board.squares[i];
            if (piece == ' ' || Character.isUpperCase(piece) != isWhite) continue;
            int x = i % 8, y = i / 8;
            char type = Character.toLowerCase(piece);
            if (type == 'p') generatePawnMoves(moves, board, i, x, y, isWhite);
            else if (type == 'n') generateKnightMoves(moves, board, i, x, y, isWhite);
            else generateSlidingMoves(moves, board, i, x, y, isWhite, type);
        }
        return moves;
    }

    private void generatePawnMoves(List<Move> moves, Board b, int i, int x, int y, boolean white) {
        int dir = white ? -1 : 1;
        if (y + dir >= 0 && y + dir < 8 && b.squares[(y + dir) * 8 + x] == ' ') {
            addMove(moves, b, i, (y + dir) * 8 + x);
            if (y == (white ? 6 : 1) && b.squares[(y + 2 * dir) * 8 + x] == ' ') addMove(moves, b, i, (y + 2 * dir) * 8 + x);
        }
        for (int dx : new int[]{-1, 1}) {
            if (x + dx >= 0 && x + dx < 8 && y + dir >= 0 && y + dir < 8) {
                char target = b.squares[(y + dir) * 8 + (x + dx)];
                if (target != ' ' && Character.isUpperCase(target) != white) addMove(moves, b, i, (y + dir) * 8 + (x + dx));
            }
        }
    }

    private void generateKnightMoves(List<Move> moves, Board b, int i, int x, int y, boolean white) {
        for (int j = 0; j < 8; j++) {
            int nx = x + KNIGHT_X[j], ny = y + KNIGHT_Y[j];
            if (nx >= 0 && nx < 8 && ny >= 0 && ny < 8) {
                char target = b.squares[ny * 8 + nx];
                if (target == ' ' || Character.isUpperCase(target) != white) addMove(moves, b, i, ny * 8 + nx);
            }
        }
    }

    private void generateSlidingMoves(List<Move> m, Board b, int i, int x, int y, boolean white, char type) {
        int start = (type == 'b') ? 0 : (type == 'r') ? 1 : 0;
        int step = (type == 'q' || type == 'k') ? 1 : 2;
        for (int j = start; j < 8; j += step) {
            int nx = x, ny = y;
            while (true) {
                nx += DIR_X[j]; ny += DIR_Y[j];
                if (nx < 0 || nx >= 8 || ny < 0 || ny >= 8) break;
                char target = b.squares[ny * 8 + nx];
                if (target != ' ' && Character.isUpperCase(target) == white) break;
                addMove(m, b, i, ny * 8 + nx);
                if (target != ' ' || type == 'k') break;
            }
        }
    }

    private void addMove(List<Move> moves, Board b, int from, int to) {
        Move move = new Move(from, to, ' ');
        char target = b.squares[to];
        int materialScore = 0;
        if (target != ' ') {
            char t = Character.toLowerCase(target);
            materialScore = (t == 'p') ? 100 : (t == 'n' || t == 'b') ? 300 : (t == 'r') ? 500 : 900;
            materialScore *= 10;
        }
        move.score = materialScore + CENTER_BONUS[to] + random.nextInt(5);
        moves.add(move);
    }
}