
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import static edu.princeton.cs.algs4.StdRandom.uniformInt;

public class BoggleBoard
{
    private char[][] Board;
    private int rows;
    private int cols;
    // Initializes a random 4-by-4 Boggle board.
    // (by rolling the Hasbro dice)
    public BoggleBoard(){
        Board = new char[4][4];
        this.rows = 4;
        this.cols = 4;
        for(int i = 0;i<4;i++){
            for(int j = 0;j<4;j++){
                Board[i][j] = (char)uniformInt('A','Z' + 1);
            }
        }
    }

    // Initializes a random m-by-n Boggle board.
    // (using the frequency of letters in the English language)
    public BoggleBoard(int m, int n){
        Board = new char[m][n];
        rows = m;
        cols = n;
        for(int i = 0;i<m;i++){
            for(int j = 0;j<n;j++){
                Board[i][j] = (char)uniformInt('A','Z' + 1);
            }
        }
    }

    // Initializes a Boggle board from the specified filename.
    public BoggleBoard(String filename){
        In in = new In(filename);
        String[] mn = in.readLine().split(" ");
        int m = Integer.parseInt(mn[0]);
        rows = m;
        int n = Integer.parseInt(mn[1]);
        cols = n;
        Board = new char[m][n];
        for(int i = 0;i<m;i++){
            String input = in.readLine().replaceAll("\\s+"," ");
            String[] ss = input.split(" ");
            for(int j = 0;j<n;j++){
                char c = ss[j].charAt(0);
                Board[i][j] = c;
            }
        }
    }

    // Initializes a Boggle board from the 2d char array.
    // (with 'Q' representing the two-letter sequence "Qu")
    public BoggleBoard(char[][] a){
        Board = a;
        rows = Board.length;
        cols = Board[0].length;
    }

    // Returns the number of rows.
    public int rows(){
        return rows;
    }

    // Returns the number of columns.
    public int cols(){
        return cols;
    }

    // Returns the letter in row i and column j.
    // (with 'Q' representing the two-letter sequence "Qu")
    public char getLetter(int i, int j){
        return Board[i][j];
    }

    // Returns a string representation of the board.
    public String toString(){
        StringBuilder str = new StringBuilder();
        for(int i = 0;i<rows;i++){
            for(int j = 0;j<cols;j++){
                str.append(Board[i][j]+" ");
            }
            str.append("\n");
        }
        return str.toString();
    }
}