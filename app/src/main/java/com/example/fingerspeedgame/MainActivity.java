package com.example.fingerspeedgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView timerTextView;
    private TextView aThousandTextView;
    private Button tapTapButton;
    private Button startButton;

    private CountDownTimer countDownTimer;

    private long initialCountDownInMillis = 60000;
    private int timerInterval = 1000;
    private int remainingTime = 60;

    private int aThousand = 330;

    private final String REMAINING_TIME_KEY = "remaining time key";
    private final String A_THOUSAND_KEY = "a thousand key";
    private boolean toStart = false;



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(REMAINING_TIME_KEY, remainingTime);
        outState.putInt(A_THOUSAND_KEY, aThousand);
        countDownTimer.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        timerTextView = findViewById(R.id.txtTimer);
        aThousandTextView = findViewById(R.id.txtAThousand);
        tapTapButton = findViewById(R.id.btnTap);
        startButton = findViewById(R.id.button_start);

        aThousandTextView.setText(aThousand + "");

        if (savedInstanceState != null) {
            toStart = true;
            remainingTime = savedInstanceState.getInt(REMAINING_TIME_KEY);
            aThousand = savedInstanceState.getInt(A_THOUSAND_KEY);

            restoreTheGame();

        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                countDownTimer.start();

                toStart = true;
            }

        });




            tapTapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (toStart) {

                        aThousand--;

                        aThousandTextView.setText(aThousand + "");
                    }
                    if (remainingTime > 0 && aThousand <= 0) {
                        Toast.makeText(MainActivity.this, "Congratulations, you won", Toast.LENGTH_LONG).show();

                        showAlert("Congratulations", "Please reset the Game");


                    }


                }
            });




        if (savedInstanceState == null) {


            countDownTimer = new CountDownTimer(initialCountDownInMillis, timerInterval) {
                @Override
                public void onTick(long millisUntilFinished) {

                    remainingTime = (int) millisUntilFinished / 1000;
                    timerTextView.setText(remainingTime + "");

                }

                @Override
                public void onFinish() {

                    Toast.makeText(MainActivity.this, "Countdown finished", Toast.LENGTH_SHORT).show();

                    showAlert("Not interesting", "Would you like to try again?");


                }
            };







        }
    }

    private void restoreTheGame() {

        int restoredRemainingTime = remainingTime;
        int restoredAThousand = aThousand;

        timerTextView.setText(restoredRemainingTime + "");
        aThousandTextView.setText(restoredAThousand + "");

        countDownTimer = new CountDownTimer((long)remainingTime * 1000, timerInterval) {
            @Override
            public void onTick(long millisUntilFinished) {

                remainingTime = (int) millisUntilFinished / 1000;
                timerTextView.setText(remainingTime + "");



            }

            @Override
            public void onFinish() {

                showAlert("Finished","Would you like to reset the game?");


            }
        };

        countDownTimer.start();

    }

    private void resetTheGame() {

        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }


        aThousand = 330;
        aThousandTextView.setText(Integer.toString(aThousand));

        timerTextView.setText(remainingTime + "");

        countDownTimer = new CountDownTimer(initialCountDownInMillis, timerInterval) {
            @Override
            public void onTick(long millisToFinish) {

                remainingTime = (int) millisToFinish / 1000;
                timerTextView.setText(remainingTime + "");


            }

            @Override
            public void onFinish() {

                showAlert("Finished","Would you like to reset the game?");

            }
        };

        countDownTimer.start();

    }

    private  void showAlert(String title, String message){

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        resetTheGame();



                    }
                }).show();
        alertDialog.setCancelable(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.info_item) {

            Toast.makeText(this, "This is the Current version of your game. Check out " +
                    "google play and make sure that you're playing tha latest version of the game "
                    + BuildConfig.VERSION_NAME, Toast.LENGTH_LONG).show();
        }
        return true;

    }
}