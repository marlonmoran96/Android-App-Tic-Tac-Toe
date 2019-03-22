package android.wku.edu.tic_tac_toe;

//Marlon Moran
//Project 1
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //used to show who's turn it is, as well as the state of the game
    public TextView text;
    //text used to show instructions
    public TextView instrucitons;
    // For when using minimax algorithm
    public Computer alg;
    // Used to help the user select whether they want to go first or second
    public RadioButton sec, first;
    //set to false until events occur
    public boolean UserTurn = false;
    public boolean UserWon = false;
    public boolean cmpWon = false;
    public boolean tie = false;

    // buttons for the board and to reset and start game
    public Button button1;
    public Button button2;
    public Button button3;
    public Button button4;
    public Button button5;
    public Button button6;
    public Button button7;
    public Button button8;
    public Button button9;
    public Button start;
    public Button rst;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initiates views to visible
        // easy was to ensure that everything is visible in the beginning
        initViews();
        // for computer logic
        // calls class, to check is user wants to go second
        alg = new Computer();
    }

    private void initViews() {


        sec = (RadioButton) findViewById(R.id.second);
        first = (RadioButton) findViewById(R.id.first);

        text = (TextView) findViewById(R.id.text);
        instrucitons = (TextView) findViewById(R.id.ins);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        start = (Button) findViewById(R.id.start);
        rst = (Button) findViewById(R.id.rst);

        //listener for when buttons are pressed
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        start.setOnClickListener(this);
        rst.setOnClickListener(this);

    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // case for when the start button is pressed
            case R.id.start:

                //makes items invincible, so things look nice
                start.setVisibility(View.INVISIBLE);
                sec.setVisibility(View.INVISIBLE);
                first.setVisibility(View.INVISIBLE);
                instrucitons.setVisibility(View.INVISIBLE);

                // radio button if player wants to go first
                if(first.isChecked()) {
                    UserTurn = true;
                    //notifies player it is their turn
                    text.setText("Your turn! ");


                    // if player wants to go second
                } else if(sec.isChecked()) {
                    UserTurn = false;
                    //notifies player it is computer's turn
                    text.setText("Computer's turn");

                    // calls computer class for minimax
                    alg.nextMove(this);
                }

                break;

                // if reset button is pressed
            case R.id.rst:
                // Make all the invisible views visible again
                start.setVisibility(View.VISIBLE);
                sec.setVisibility(View.VISIBLE);
                first.setVisibility(View.VISIBLE);
                instrucitons.setVisibility(View.VISIBLE);

                text.setText("");

                UserTurn = false;
                UserWon = false;
                cmpWon = false;
                tie= false;
                // called to empty board
                rstHelper();

                break;
            default:
                // If button is pressed without starting the game
                // helped manage earlier bugs
                if(start.getVisibility() == View.VISIBLE)

                    break;
                // used to set move for user when spot of pressed
                // sends press to setMove class
                setMove(v.getId());
                // Use minimax for the computer;s following move
                alg.nextMove(this);
        }
    }
        // used to help clear board
    public void rstHelper() {
        button1.setText("");
        button2.setText("");
        button3.setText("");
        button4.setText("");
        button5.setText("");
        button6.setText("");
        button7.setText("");
        button8.setText("");
        button9.setText("");
    }
        // used to place text
    public void setMove(int id) {
        // holder for any of the nine spots that were pressed
        Button button = (Button) findViewById(id);
        //ensures the button is not empty
        // this was made to fix a bug that would allow the user to skip moves
        // if the same button was pressed more than once.
        if(!button.getText().equals(""))
            return;
        // if the user goes first, then they use X
        if(first.isChecked() && UserTurn)
        button.setText("X");
        // if thye go second, they use O
        else if(sec.isChecked() && UserTurn)
            button.setText("O");

        // checks if the game is over
        check();
        // tells user state of game in the text view box
        if(UserWon && !tie)
            text.setText("You Won!");
        else if(cmpWon && !tie)
            text.setText("Computer Won!");
        else if(tie)
            text.setText("It's a tie!");
        else
            // changes turn if nothing occurred
            changeTrn();
    }
        // shows user whose turn it is
    public void changeTrn() {
        // ensures  that change in turns has occurred
        UserTurn = !UserTurn;
        // tells user it is their turn
        if(UserTurn)
            text.setText("Your turn");
        else
            text.setText("Computer's turn");
    }
        // checks for a win
    public void check() {
        if (crossHorizontal() || crossVertical() || crossDiagonal()) {
            // if user goes first they are X, so it checks for X's wins
            if(first.isChecked())

                UserWon = true;
            else
                // if not the Computer is X
                cmpWon = true;

            // if they did not go first, by default, they are Os
        } else if (circleHorizontal() || circleVertical()
                || circleDiagonal()) {

            if(sec.isChecked())

                UserWon = true;


            else

                cmpWon = true;
            // if the board is filled, then it notices the user of a tie
        } else if(isfull() && !cmpWon && !UserWon )

            tie = true;
    }
    // all spots have been filled
    public boolean isfull(){
        return !button1.getText().equals("")
                && !button2.getText().equals("")
                && !button3.getText().equals("")
                && !button4.getText().equals("")
                && !button5.getText().equals("")
                && !button6.getText().equals("")
                && !button7.getText().equals("")
                && !button8.getText().equals("")
                && !button9.getText().equals("");
    }


        /// used to check for all potential wins, for any player
    public boolean crossHorizontal() {

        return (button1.getText().equals("X") && button2.getText().equals("X") && button3.getText().equals("X")) ||
                (button4.getText().equals("X") && button5.getText().equals("X") && button6.getText().equals("X")) ||
                (button7.getText().equals("X") && button8.getText().equals("X") && button9.getText().equals("X"));
    }

    public boolean crossVertical() {

        return (button1.getText().equals("X") && button4.getText().equals("X") && button7.getText().equals("X")) ||
                (button2.getText().equals("X") && button5.getText().equals("X") && button8.getText().equals("X")) ||
                (button3.getText().equals("X") && button6.getText().equals("X") && button9.getText().equals("X"));
    }

    public boolean circleVertical() {

        return (button1.getText().equals("O") && button4.getText().equals("O") && button7.getText().equals("O")) ||
                (button2.getText().equals("O") && button5.getText().equals("O") && button8.getText().equals("O")) ||
                (button3.getText().equals("O") && button6.getText().equals("O") && button9.getText().equals("O"));
    }

    public boolean circleHorizontal() {

        return (button1.getText().equals("O") && button2.getText().equals("O") && button3.getText().equals("O")) ||
                (button4.getText().equals("O") && button5.getText().equals("O") && button6.getText().equals("O")) ||
                (button7.getText().equals("O") && button8.getText().equals("O") && button9.getText().equals("O"));
    }

    public boolean crossDiagonal() {

        return (button1.getText().equals("X") && button5.getText().equals("X") && button9.getText().equals("X")) ||
                (button3.getText().equals("X") && button5.getText().equals("X") && button7.getText().equals("X"));
    }

    public boolean circleDiagonal() {

        return (button1.getText().equals("O") && button5.getText().equals("O") && button9.getText().equals("O")) ||
                (button3.getText().equals("O") && button5.getText().equals("O") && button7.getText().equals("O"));
    }
}



