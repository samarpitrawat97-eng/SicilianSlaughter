import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SicilianSlaughter {
    public static void main(String[] args) {
        Board board = new Board();
        MoveGenerator moveGenerator = new MoveGenerator();
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            String[] tokens = input.split("\\s+");
            if (tokens.length == 0) continue;

            switch (tokens[0]) {
                case "uci":
                    System.out.println("id name SicilianSlaughter");
                    System.out.println("id author You");
                    System.out.println("uciok");
                    break;
                case "isready":
                    System.out.println("readyok");
                    break;
                case "ucinewgame":
                    board.reset();
                    break;
                case "position":
                    if (tokens.length > 1 && tokens[1].equals("startpos")) {
                        board.reset();
                    } else if (tokens.length > 1 && tokens[1].equals("fen")) {
                        StringBuilder fen = new StringBuilder();
                        int idx = 2;
                        while (idx < tokens.length && !tokens[idx].equals("moves")) {
                            fen.append(tokens[idx]).append(" ");
                            idx++;
                        }
                        board.parseFen(fen.toString().trim());
                    }

                    int movesIndex = -1;
                    for (int i = 0; i < tokens.length; i++) {
                        if (tokens[i].equals("moves")) {
                            movesIndex = i;
                            break;
                        }
                    }
                    if (movesIndex != -1) {
                        for (int i = movesIndex + 1; i < tokens.length; i++) {
                            board.makeMove(tokens[i]);
                        }
                    }
                    break;
                case "go":
                    List<Move> legalMoves = moveGenerator.generateMoves(board);
                    if (legalMoves.isEmpty()) {
                        System.out.println("bestmove 0000");
                    } else {
                        Move bestMove = legalMoves.get(random.nextInt(legalMoves.size()));
                        System.out.println("bestmove " + bestMove.toString());
                    }
                    break;
                case "quit":
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }
}