import java.util.*;

public class SicilianSlaughter {
    public static void main(String[] args) {
        Board board = new Board();
        MoveGenerator moveGenerator = new MoveGenerator();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String[] tokens = scanner.nextLine().trim().split("\\s+");
            if (tokens.length == 0) continue;

            switch (tokens[0]) {
                case "uci":
                    System.out.println("id name SicilianSlaughter");
                    System.out.println("id author Samarpit Rawat");
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
                    }
                    for (int i = 0; i < tokens.length; i++) {
                        if (tokens[i].equals("moves")) {
                            for (int j = i + 1; j < tokens.length; j++) {
                                board.makeMove(tokens[j]);
                            }
                            break;
                        }
                    }
                    break;
                case "go":
                    List<Move> moves = moveGenerator.generateMoves(board);
                    if (moves.isEmpty()) {
                        System.out.println("bestmove 0000");
                    } else {
                        moves.sort((m1, m2) -> Integer.compare(m2.score, m1.score));
                        System.out.println("bestmove " + moves.get(0).toString());
                    }
                    break;
                case "quit":
                    scanner.close();
                    System.exit(0);
                    return;
            }
        }
    }
}