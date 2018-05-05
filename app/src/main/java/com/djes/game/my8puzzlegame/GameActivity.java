package com.djes.game.my8puzzlegame;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initializing Game
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Game game = Game.GetInstance();
        TextView moves = findViewById(R.id.txt_moves);
        game.SetTextMoves(moves);
        Chronometer chronometer = findViewById(R.id.mChronometer);
        game.SetChronometer(chronometer);
        TextView btnReset   = findViewById(R.id.btn_reset);
        game.SetBtnReset(btnReset);
        GridView container = findViewById(R.id.container);
        game.SetGrid(container);
        game.SetNumPieces(9);
        game.SetResources(getResources());
        game.SetImageResource(R.drawable.puzzle);


        //Start Game
        game.CreateGame(this);
    }


}
