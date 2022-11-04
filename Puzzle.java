import java.util.Scanner;

/**
 * The Puzzle program is a sliding arrays of number game
 * that you can move numbers to form a correct ascending sequence.
 *
 * @author
 * @version 1.0
 * @since   2022-11-04
 */
public class Puzzle {
    private static final int MAX_MOVES = 100;
    private final int[][] startBoard;
    private int[][] board;
    private int c;
    private int r;
    private char[] moves;
    private int moveCount;


    public Puzzle(int[][] board) {
        this.startBoard = board;

        // Copy to a new object
        this.board = new int[board.length][board[0].length];
        for (int j = 0; j < board.length; j++) {
            for (int i = 0; i < board[0].length; i++) {
                this.board[j][i] = board[j][i];
            }
        }

        this.moves = new char[MAX_MOVES];

        for (int j = 0; j < board[0].length; j++) {
            for (int i = 0; i < board.length; i++) {
                if(board[j][i]==0){
                    this.c= i;
                    this.r= j;
                    break;
                }
            }
        }
    }

    public Puzzle copy(){
        return new Puzzle(startBoard);
    }

    public void display(){
        for (int i = 0; i < board.length; i++)
            System.out.printf("%4d%4d%4d%4d\n", board[i][0], board[i][1], board[i][2], board[i][3]);
    }

    public void doMove(char m){
        // Check to ignore uppercase lowercase
        if(Character. isLowerCase(m))
            m = Character.toUpperCase(m);

        int prev = 0;
        if(validMove(m)) {
            moves[moveCount] = m;
            moveCount++;
            switch (m) {
                case 'L':
                    prev = c;
                    c--;
                    board[r][prev] = board[r][c];
                    board[r][c] = 0;
                    break;
                case 'R':
                    prev = c;
                    c++;
                    board[r][prev] = board[r][c];
                    board[r][c] = 0;
                    break;
                case 'D':
                    prev = r;
                    r++;
                    board[prev][c] = board[r][c];
                    board[r][c] = 0;
                    break;
                case 'U':
                    prev = r;
                    r--;
                    board[prev][c] = board[r][c];
                    board[r][c] = 0;
                    break;
            }
        }
    }

    public boolean validMove(char m){
        switch (m){
            case 'L':
                if(c > 0)
                    return true;
                break;
            case 'R':
                if(c < board[0].length-1)
                    return true;
                break;
            case 'D':
                if(r < board.length-1)
                    return true;
                break;
            case 'U':
                if(r > 0)
                    return true;
                break;
        }
        return false;
    }

    public boolean solved(){
        int x = 0;
        for (int j = 0; j < board.length; j++) {
            for (int i = 0; i < board[0].length; i++) {
                if(board[j][i] != x)
                    return false;
                x++;
            }
        }
        return true;
    }

    public boolean checkSolution(char[] moves){
        Puzzle pz = this.copy();
        for (char ch :
                moves) {
            pz.doMove(ch);
        }
        return solved();
    }

    public char[] getMoves() {
        return moves;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public static void main(String[] args) {
        int[][] sample;
        int size = 4;
        Scanner scanner = new Scanner(System.in);
        System.out.println("WELCOME TO THE SLIDING ARRAYS GAME!\n");
        System.out.println(">> Press S to continue with sample data or I to input your board's dara [S,I]: ");
        char userInput = scanner.next(".").charAt(0);
        if(userInput=='S' || userInput=='s') {
            sample = new int[][]{{5, 0, 2, 3}, {1, 4, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}};
            // sample2 = new int[][]{{6, 7, 1, 9}, {10, 8, 0, 5}, {3, 15, 12, 4}, {11, 13, 2, 14}};
            // sample3 = new int[][]{{6, 7, 1, 9}, {10, 8, 0, 5}, {3, 15, 12, 4}, {11, 13, 2, 14}};
        }else{
            sample = new int[size][size];
            for (int j = 0; j < size; j++) {
                for (int i = 0; i < size; i++) {
                    sample[j][i] = scanner.nextInt();
                }
            }
        }

        Puzzle puzzle = new Puzzle(sample);

        while(!puzzle.solved()) {
            puzzle.display();
            System.out.print("\nNext Move> ");
            puzzle.doMove(scanner.next(".").charAt(0));
        }

        // Double Check for the provided solution using checkSolution()
        if(puzzle.checkSolution(puzzle.getMoves()))
            System.out.println("\nSolution " + String.valueOf(puzzle.getMoves(),0,puzzle.getMoveCount()) + " double checked and you have moved correctly!" +
                    "\n\n**** **** **|...YOU WIN...|** **** ****");
        else
            System.out.println("Provided Solution seems not correct!");

    }
}