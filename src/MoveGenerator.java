import java.util.ArrayList;
import java.util.List;

public class MoveGenerator {
    private static final int[] DIR_X = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static final int[] DIR_Y = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] KNIGHT_X = {-2, -1, 1, 2, -2, -1, 1, 2};
    private static final int[] KNIGHT_Y = {-1, -2, -2, -1, 1, 2, 2, 1};

    public List<Move> generateMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        boolean isWhite = board.isWhiteTurn;

        for (int i = 0; i < 64; i++) {
            char piece = board.squares[i];
            if (piece == ' ') continue;

            boolean isPieceWhite = Character.isUpperCase(piece);
            if (isWhite != isPieceWhite) continue;

            int x = i % 8;
            int y = i / 8;
            char type = Character.toLowerCase(piece);

            if (type == 'p') {
                int dir = isWhite ? -1 : 1;
                int startRow = isWhite ? 6 : 1;

                if (y + dir >= 0 && y + dir < 8 && board.squares[(y + dir) * 8 + x] == ' ') {
                    addPawnMove(moves, i, (y + dir) * 8 + x, y + dir);
                    if (y == startRow && board.squares[(y + 2 * dir) * 8 + x] == ' ') {
                        moves.add(new Move(i, (y + 2 * dir) * 8 + x, ' '));
                    }
                }
                for (int dx : new int[]{-1, 1}) {
                    if (x + dx >= 0 && x + dx < 8 && y + dir >= 0 && y + dir < 8) {
                        char target = board.squares[(y + dir) * 8 + (x + dx)];
                        if (target != ' ' && Character.isUpperCase(target) != isWhite) {
                            addPawnMove(moves, i, (y + dir) * 8 + (x + dx), y + dir);
                        }
                    }
                }
            } else if (type == 'n') {
                for (int j = 0; j < 8; j++) {
                    int nx = x + KNIGHT_X[j];
                    int ny = y + KNIGHT_Y[j];
                    if (isValidSquare(nx, ny, isWhite, board)) {
                        moves.add(new Move(i, ny * 8 + nx, ' '));
                    }
                }
            } else {
                int startDir = (type == 'b') ? 0 : (type == 'r') ? 1 : 0;
                int stepDir = (type == 'q' || type == 'k') ? 1 : 2;

                for (int j = startDir; j < 8; j += stepDir) {
                    int nx = x;
                    int ny = y;
                    while (true) {
                        nx += DIR_X[j];
                        ny += DIR_Y[j];
                        if (!isValidSquare(nx, ny, isWhite, board)) break;
                        moves.add(new Move(i, ny * 8 + nx, ' '));
                        if (board.squares[ny * 8 + nx] != ' ' || type == 'k') break;
                    }
                }
            }
        }
        return moves;
    }

    private void addPawnMove(List<Move> moves, int from, int to, int toY) {
        if (toY == 0 || toY == 7) {
            moves.add(new Move(from, to, 'q'));
            moves.add(new Move(from, to, 'r'));
            moves.add(new Move(from, to, 'b'));
            moves.add(new Move(from, to, 'n'));
        } else {
            moves.add(new Move(from, to, ' '));
        }
    }

    private boolean isValidSquare(int x, int y, boolean isWhite, Board board) {
        if (x < 0 || x >= 8 || y < 0 || y >= 8) return false;
        char target = board.squares[y * 8 + x];
        return target == ' ' || Character.isUpperCase(target) != isWhite;
    }
}