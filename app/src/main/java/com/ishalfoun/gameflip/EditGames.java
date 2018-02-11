package com.ishalfoun.gameflip;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EditGames extends AppCompatActivity {

    List<String> games;
    Set<String> gamesSet;
    ListView gamesListView;
    GamesAdapter mygamesAdapter;

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_games);



        gamesListView = (ListView) findViewById(R.id.gamesListView);

        if (games == null)
        {
            prefs = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
            gamesSet = prefs.getStringSet("games", new HashSet<String>());
            games = new ArrayList<String>(gamesSet);
        }
        mygamesAdapter = new GamesAdapter(games);
        gamesListView.setAdapter(mygamesAdapter);



        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createNewNote();
            }
        });
        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fabDeleteAll);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onDeleteAll();
            }
        });


    }

    public void onDeleteAll()
    {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Delete All?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        games = new ArrayList<String>();
                        Toast.makeText(EditGames.this, "Deleted All",Toast.LENGTH_SHORT).show();
                        mygamesAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton( "No" , null)
                .create();
        dialog.show();
    }

    public void createNewNote()
    {
        final EditText editText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add game")
                .setView(editText)
                .setPositiveButton("create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        games.add(editText.getText().toString());
                        Toast.makeText(EditGames.this, "Added: "+editText.getText().toString() ,Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton( "cancel" , null)
                .create();
        dialog.show();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        prefs = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        gamesSet = new HashSet<String>(games);
        editor.putStringSet("games" , gamesSet);
        editor.commit();
        Log.i("MYAPP", "saved in prefs");
    }


    class GamesAdapter extends BaseAdapter {
        List<String> games;

        TextView textNameTV;
        Button fabDelete;
        public GamesAdapter() {
            games = null;
        }


        public GamesAdapter(List<String> games) {
            this.games = games;
        }

        public int getCount() {
            return games.size();
        }

        @Override
        public Object getItem(int arg0) {
            return games.get(arg0);
        }

        public Object getName(int arg0) {
            return games.get(arg0);
        }

        public Object getInv(int arg0) {
            return games.get(arg0);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.game, viewGroup, false);
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClicked(i);
                }
            };
            row.setOnClickListener(listener);


            textNameTV = (TextView) row.findViewById(R.id.gameTextView);
            textNameTV.setText(games.get(i));

             return (row);
        }

    }
    public void onItemClicked(final int i)
    {
        final EditText editText = new EditText(this);
        editText.setText(games.get(i));
        AlertDialog dialog = new AlertDialog.Builder(EditGames.this)
                .setTitle("Edit " + games.get(i))
                .setView(editText)
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(EditGames.this, "deleted: "+games.get(i) ,Toast.LENGTH_SHORT).show();
                        games.remove(i);
                        mygamesAdapter.notifyDataSetChanged();
                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        games.set(i, editText.getText().toString());
                        Toast.makeText(EditGames.this, "Saved: "+editText.getText().toString() ,Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton( "cancel" , null)
                .create();
        dialog.show();
//        android.R.drawable.ic_menu_delete
    }

//
//        public View getView(final int position, View convertView, ViewGroup parent) {
//
//        }
}
