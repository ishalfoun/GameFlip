package com.ishalfoun.gameflip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    TextView randomGametv;
    SharedPreferences prefs = null;
    List<String> games;
    Set<String> gamesSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        randomGametv = (TextView) findViewById(R.id.randomGametv);

        prefs = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        gamesSet = prefs.getStringSet("games", new HashSet<String>());
        games = new ArrayList<String>(gamesSet);
    }

    public void onEditGames(View view) {
        startActivity(new Intent(MainActivity.this, EditGames.class));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        prefs = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        gamesSet = prefs.getStringSet("games", new HashSet<String>());
        games = new ArrayList<String>(gamesSet);
    }

    public void onFlipGames(View view) {

        if (games.size()!=0) {
            int rand = (int) Math.floor(Math.random() * games.size());
            randomGametv.setText("" + games.get(rand));
        }
        else

            randomGametv.setText("No games added");
    }
}
