package co.id.gamepenyebaranpenyakit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    AppCompatTextView btnPlay, btnLogout, btnRanking;
    TextView txtName;
    SharedPreferences sharedpreferences;
    String id, username;
    MediaPlayer ring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btn_play_games);
        btnLogout = findViewById(R.id.btn_logout);
        btnRanking = findViewById(R.id.btn_ranking);
        txtName = findViewById(R.id.namauser);
        ring= MediaPlayer.create(MainActivity.this,R.raw.backsound);
        ring.start();
        ring.setLooping(true);

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra("id");
        username = getIntent().getStringExtra("email");

        txtName.setText(getIntent().getStringExtra("name"));
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(MainActivity.this, MapsActivity.class);
                pindah.putExtra("chance", sharedpreferences.getString("chance",null));
                pindah.putExtra("id", sharedpreferences.getString("id",null));
                startActivity(pindah);
            }
        });

        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(MainActivity.this,ListScoreUser.class);
                startActivity(pindah);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString("id", null);
                editor.putString("email", null);
                editor.putString("name", null);
                editor.putString("chance", null);
                editor.putString("level", null);
                editor.putString("score", null);
                editor.commit();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ring.start();
        ring.setLooping(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ring.pause();
        ring.setLooping(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ring.stop();
        ring.setLooping(false);
    }
}