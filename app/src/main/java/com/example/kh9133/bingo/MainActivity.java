package com.example.kh9133.bingo;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Time;
import java.util.Date;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    Button[] btn = new Button [25];  //Array to store the button ids
    Queue<String> numberQueue = new LinkedList<String>(); //Allows user to change the button text manually
    CharSequence toastText;
    int duration = Toast.LENGTH_SHORT;  //toast duration

    /*
     *@function(Default)
     * descp    -Serves whenever the application is started, also stores the ids for the buttons in 'btn' array.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btn[0] =(Button)findViewById(R.id.button00);
        btn[1] =(Button)findViewById(R.id.button01);
        btn[2] =(Button)findViewById(R.id.button02);
        btn[3] =(Button)findViewById(R.id.button03);
        btn[4] =(Button)findViewById(R.id.button04);
        btn[5] =(Button)findViewById(R.id.button10);
        btn[6] =(Button)findViewById(R.id.button11);
        btn[7] =(Button)findViewById(R.id.button12);
        btn[8] =(Button)findViewById(R.id.button13);
        btn[9] =(Button)findViewById(R.id.button14);
        btn[10] =(Button)findViewById(R.id.button20);
        btn[11] =(Button)findViewById(R.id.button21);
        btn[12] =(Button)findViewById(R.id.button22);
        btn[13] =(Button)findViewById(R.id.button23);
        btn[14] =(Button)findViewById(R.id.button24);
        btn[15] =(Button)findViewById(R.id.button30);
        btn[16] =(Button)findViewById(R.id.button31);
        btn[17] =(Button)findViewById(R.id.button32);
        btn[18] =(Button)findViewById(R.id.button33);
        btn[19] =(Button)findViewById(R.id.button34);
        btn[20] =(Button)findViewById(R.id.button40);
        btn[21] =(Button)findViewById(R.id.button41);
        btn[22] =(Button)findViewById(R.id.button42);
        btn[23] =(Button)findViewById(R.id.button43);
        btn[24] =(Button)findViewById(R.id.button44);
    }

    /*
     *@function
     * params   -Default
     * returns  -void
     * descp    -Generates the text over the buttons randomly by internally using the function
     *           'shuffleArray', also clears the queue whenever new series of random numbers are
     *           generated.
     */
    public void onClickRandom(View v){

        int index;
        int num_array[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
        Button startBtn = (Button)findViewById(R.id.start);

        startBtn.setVisibility(View.VISIBLE); //Turns on the visibility of start button

        numberQueue.clear();    //clears the numbers queue

        shuffleArray(num_array);  //This is for shuffling the array

        for(index = 0;index < 25;index++){  //Assigning the random numbers to buttons
            btn[index].setText(num_array[index]+"");
        }
        for(index = 0;index < 25;index++){  //Assigning action onclick to buttons
            btn[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ButtonOnClick(v);
                }
            });
        }
    }

    /*
     *@function
     * params   -Default
     * returns  -void
     * descp    -This gets invoked on click action of a button referred as numbers in the bingo table,
     *           it extends its functionality to change the text over the buttons manually.Once the game starts
     *           manual changes gets disabled.
     */
    public void ButtonOnClick(View v) {

        Button random = (Button)findViewById(R.id.random);

        if(random.getVisibility() == View.VISIBLE){ //Before the game starts
            changeNumbers(v);
        }
        else{
            Button btn = (Button)v; //After the game starts the colour of the button clicked gets changed
            btn.setBackgroundColor(Color.parseColor("#2ECC71"));
        }

    }

    /*
     *@function
     * params   -integer array
     * returns  -void
     * descp    -It shuffles the entire array so that the final output will be never the same
     */
    public void shuffleArray(int array[]){

        Random r = new Random();
        int startIndex = r.nextInt(25);
        int lastIndex = r.nextInt(25);
        int temp;
        int iteration = 0;

        while(iteration < 50) {
            temp = array[startIndex];
            array[startIndex] = array[lastIndex];
            array[lastIndex] = temp;
            startIndex = r.nextInt(25);
            lastIndex = r.nextInt(25);
            iteration++;
        }
    }

    /*
     *@function
     * params   -Takes the view of the clicked button
     * returns  -void
     * descp    -For manual changing of numbers, here it uses the queue to store the numbers which are
     *           selected for changing
     */
    public void changeNumbers(View v){

        Button btn = (Button)v;

        try {
            if (btn.getText() == "" && !numberQueue.isEmpty()) {    //if a button text is null it assigns the element in the queue to it
                btn.setText(numberQueue.remove());
            } else {
                numberQueue.add(btn.getText() + "");    //on click of a button it stores the text(number) present on the button
                btn.setText("");
            }
        }catch(Exception e){
            Log.v("changeNumbers exception",e.toString()+"");
        }

    }

    /*
     *@function
     * params   -Default
     * returns  -void
     * descp    -on click of start buttons this fucntion gets invoked, it checks whether there is no empty grid is not present,
     *           if present it raises a toast message, else the game starts.
     */
    public void startGame(View v){

        Button randomBtn = (Button)findViewById(R.id.random);
        Button startBtn = (Button)findViewById(R.id.start);
        int isEmptyFlag = 0;

        for(int index = 0;index < 25;index++){
            if(btn[index].getText() == ""){
                isEmptyFlag = 1;
                break;
            }
        }
        if(isEmptyFlag == 1){
            toastText = "Please fill all the grids.";
            Context context = getApplicationContext();
            Toast toastMsg = Toast.makeText(context,toastText,duration);
            toastMsg.show();
        }
        else {
            randomBtn.setVisibility(v.GONE);
            startBtn.setVisibility(v.GONE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
