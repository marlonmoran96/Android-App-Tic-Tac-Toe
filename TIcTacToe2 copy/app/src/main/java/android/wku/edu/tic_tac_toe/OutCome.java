package android.wku.edu.tic_tac_toe;


// Helper class
public class OutCome {
    // used to check the status of the board for the AI
    String[] cBoard;
    // The score of the game
    //used to check for win on minimax algorithm
    int score;
    // The depth in the tree, for minimax
    // computer uses to make decisions  on minimax
    int move;


    // gets score
    public int getScore() {

        return score;
    }

    // references tbe state of the board
    public void setcBoard(String[] cBoard) {

        this.cBoard = cBoard;
    }

    public OutCome(String[] cBoard, int score, int depth) {
        this.cBoard = cBoard;
        this.score = score;
        this.move = depth;
    }
}



