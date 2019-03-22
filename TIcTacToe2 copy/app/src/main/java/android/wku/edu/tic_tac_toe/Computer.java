package android.wku.edu.tic_tac_toe;

// Marlon Moran
// Project one
import android.os.Handler;

import java.util.ArrayList;


public class Computer {
    // board form main activity
    private MainActivity board;
    // the board as a string, used to check state of board
    private String[] strBoard = new String[9];

    // used to portray the state of the minimax algorithm
    private static String max;
    private static String min;

    // Level of state for minimax
    private String level;

    public void nextMove(final MainActivity board) {
        // references board in main class
        this.board = board;

        // if players turn, returns to main class
        if(board.UserTurn)
            return;

        if(board.sec.isChecked()) {
            // computer uses x and uses x for the max too
            //player goes second

            min = "O";
            max = "X";
        } else {
            // computer is O , player goes first
            // computer uses O for the max
            min = "X";
            max = "O";
        }
        // set to max, the goal for the computer is the max, never the min
        level = "max";



            // used for pause
        Handler pause = new Handler();



        // pauses
        pause.postDelayed(new Runnable() {
            @Override
            public void run() {
                // gets string version of board
                setTextBoard();

                // finds best move
                OutCome bestMove = minimax(strBoard, level, 0, 0);
                // applies  the helper cboard, to the regular board
                applyTextBoard(bestMove.cBoard);
                //checks for status of game, then states status
                board.check();

                if (board.UserWon && !board.tie)

                    board.text.setText("Player Won");

                else if (board.cmpWon && !board.tie)

                    board.text.setText("Computer Won");

                else if (board.tie)

                    board.text.setText("It's a draw");

                else

                    board.changeTrn();
            }
        }, 400);
    }

    private OutCome minimax(String[] textBoard, String level, int recurse, int move) {

        // Get list of all possible moves
        // empty moves are set to max
        ArrayList<String[]> PotentialSpots = findEmptySpots(textBoard, level);

        // ensures there are open spots by checking that there is Potential spots found
        // set potential spots to null in find empty spots class, if nothing is found
        if(gameOver(textBoard) || PotentialSpots == null  ) {
            // if no potential spots exist
            // if the game has ended as well
            return new OutCome(textBoard, getScore(textBoard), move);

        } else {
            // Gets a list of scores for all available potential spots
            ArrayList<OutCome> listScore = new ArrayList<>();

            for(int i = 0; i < PotentialSpots.size(); i++) {
                // recursively calls moves
                // invert levels so it can predict player move
                listScore.add(minimax(PotentialSpots.get(i), invertLvl(level), 1, move + 1));
            }
            // Gets the out come, or the score to the listScore list
            OutCome OC = getOutCome(listScore, level);
            // with maximum result
            // set to equal one to ensure it is ran
            if(recurse == 1)
                // sets best move to helper string array
                OC.setcBoard(textBoard);
            // so result can be set
            return OC;
        }
    }

    private OutCome getOutCome(ArrayList<OutCome> listScore, String level) {
        // gets score of first
        OutCome result = listScore.get(0);
        // checks to ensure it is the Computer by checking the level
        if(level.equals("max")) {
            // ran for as many times as the potential spots loop is ran
            for(int i = 1; i < listScore.size(); i++) {

                // checks for score
                // computer wants the highest score, or the max
                if((listScore.get(i).getScore() > result.getScore()) ||
                        // checks for  the result and that does not equal a win, but requires the least amount of moves.
                        (listScore.get(i).getScore() == result.getScore()
                                // checks for move that takes the least amount of turns
                                && listScore.get(i).move < result.move))
                    // makes the result the score
                    result = listScore.get(i);
            }
        } else {
            // ran to predict player moves
            for(int i = 1; i < listScore.size(); i++) {
                // ran for min, so you want the lowest score
                if((listScore.get(i).getScore() < result.getScore()) ||
                        // checks for user predicted wins that require the least amount of moves, which is why depth is checked
                        (listScore.get(i).getScore() == result.getScore()
                                // checks for move that takes the least amount of turns
                                && listScore.get(i).move < result.move))

                    result = listScore.get(i);
            }
        }

        return result;
    }



        // checks what spots are open and returns them
    private ArrayList<String[]> findEmptySpots(String[] textBoard, String level) {

        ArrayList<String[]> empty = new ArrayList<>();
        // runs it for length of board
        for(int i = 0; i < textBoard.length; i++) {
            // checks if spot is empty

            if(textBoard[i].equals("")) {
                // if it is, it is assigned to empty array of string
                String[] spot = new String[9];
                //copies empty spot board to main one, so string board is accurate on the state of the game
                System.arraycopy(textBoard, 0, spot, 0, 9);

                // if level is max, then spots are set to max for computer
                if(level.equals("max"))
                    spot[i] = max;


                // set to min for predicted user moves
                else if(level.equals("min"))
                    spot[i] = min;
                // adds empty locations to array
                empty.add(spot);
            }
        }
            //  if successor size equals 0, then set to null
        // means board is full and used to check in minimax class
        return (empty.size() == 0) ? null : empty;
    }

    // If the level is max, set to min
    // used to invert levels for minimax algorithm
    private String invertLvl(String level) {

        return (level.equals("max")) ? "min" : "max";

    }

    // game is not 0 , then game is over
    private boolean gameOver(String[] textBoard) {

        return (getScore(textBoard) != 0);
    }

    // returns +1 when the computer wins
    // returns -1 when the computer loses
    // returns 0 is nothing occurred
    private int getScore(String[] textBoard) {
        if(max.equals("O")) {
            if((textBoard[0].equals("O") && textBoard[1].equals("O") && textBoard[2].equals("O")) ||
                    (textBoard[3].equals("O") && textBoard[4].equals("O") && textBoard[5].equals("O")) ||
                    (textBoard[6].equals("O") && textBoard[7].equals("O") && textBoard[8].equals("O")) ||
                    (textBoard[0].equals("O") && textBoard[3].equals("O") && textBoard[6].equals("O")) ||
                    (textBoard[1].equals("O") && textBoard[4].equals("O") && textBoard[7].equals("O")) ||
                    (textBoard[2].equals("O") && textBoard[5].equals("O") && textBoard[8].equals("O")) ||
                    (textBoard[0].equals("O") && textBoard[4].equals("O") && textBoard[8].equals("O")) ||
                    (textBoard[2].equals("O") && textBoard[4].equals("O") && textBoard[6].equals("O")))
                return 1;
            else if((textBoard[0].equals("X") && textBoard[1].equals("X") && textBoard[2].equals("X")) ||
                    (textBoard[3].equals("X") && textBoard[4].equals("X") && textBoard[5].equals("X")) ||
                    (textBoard[6].equals("X") && textBoard[7].equals("X") && textBoard[8].equals("X")) ||
                    (textBoard[0].equals("X") && textBoard[3].equals("X") && textBoard[6].equals("X")) ||
                    (textBoard[1].equals("X") && textBoard[4].equals("X") && textBoard[7].equals("X")) ||
                    (textBoard[2].equals("X") && textBoard[5].equals("X") && textBoard[8].equals("X")) ||
                    (textBoard[0].equals("X") && textBoard[4].equals("X") && textBoard[8].equals("X")) ||
                    (textBoard[2].equals("X") && textBoard[4].equals("X") && textBoard[6].equals("X")))
                return -1;

        } else if(max.equals("X")) {

            if((textBoard[0].equals("X") && textBoard[1].equals("X") && textBoard[2].equals("X")) ||
                    (textBoard[3].equals("X") && textBoard[4].equals("X") && textBoard[5].equals("X")) ||
                    (textBoard[6].equals("X") && textBoard[7].equals("X") && textBoard[8].equals("X")) ||
                    (textBoard[0].equals("X") && textBoard[3].equals("X") && textBoard[6].equals("X")) ||
                    (textBoard[1].equals("X") && textBoard[4].equals("X") && textBoard[7].equals("X")) ||
                    (textBoard[2].equals("X") && textBoard[5].equals("X") && textBoard[8].equals("X")) ||
                    (textBoard[0].equals("X") && textBoard[4].equals("X") && textBoard[8].equals("X")) ||
                    (textBoard[2].equals("X") && textBoard[4].equals("X") && textBoard[6].equals("X")))


                return 1;


            else if((textBoard[0].equals("O") && textBoard[1].equals("O") && textBoard[2].equals("O")) ||
                    (textBoard[3].equals("O") && textBoard[4].equals("O") && textBoard[5].equals("O")) ||
                    (textBoard[6].equals("O") && textBoard[7].equals("O") && textBoard[8].equals("O")) ||
                    (textBoard[0].equals("O") && textBoard[3].equals("O") && textBoard[6].equals("O")) ||
                    (textBoard[1].equals("O") && textBoard[4].equals("O") && textBoard[7].equals("O")) ||
                    (textBoard[2].equals("O") && textBoard[5].equals("O") && textBoard[8].equals("O")) ||
                    (textBoard[0].equals("O") && textBoard[4].equals("O") && textBoard[8].equals("O")) ||
                    (textBoard[2].equals("O") && textBoard[4].equals("O") && textBoard[6].equals("O")))


                return -1;
        }

        return 0;
    }

        // used to apply computers move
    private void applyTextBoard(String[] textBoard) {
        board.button1.setText(textBoard[0]);
        board.button2.setText(textBoard[1]);
        board.button3.setText(textBoard[2]);
        board.button4.setText(textBoard[3]);
        board.button5.setText(textBoard[4]);
        board.button6.setText(textBoard[5]);
        board.button7.setText(textBoard[6]);
        board.button8.setText(textBoard[7]);
        board.button9.setText(textBoard[8]);
    }
    // makes board into strings
    // used to help with minimax
    private void setTextBoard() {
        strBoard[0] = board.button1.getText().toString();
        strBoard[1] = board.button2.getText().toString();
        strBoard[2] = board.button3.getText().toString();
        strBoard[3] = board.button4.getText().toString();
        strBoard[4] = board.button5.getText().toString();
        strBoard[5] = board.button6.getText().toString();
        strBoard[6] = board.button7.getText().toString();
        strBoard[7] = board.button8.getText().toString();
        strBoard[8] = board.button9.getText().toString();
    }



}

