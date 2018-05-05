package com.djes.game.my8puzzlegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button btnPlay = (Button) findViewById(R.id.btn_play);
        Button btnExit = (Button) findViewById(R.id.btn_salir);

        btnPlay.setOnClickListener(view ->{
            Intent intent = new Intent(MenuActivity.this,GameActivity.class);
            startActivity(intent);
        });

        btnExit.setOnClickListener(view ->{
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}
