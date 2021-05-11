package com.example.zobaer53.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
static ArrayList <String> notes = new ArrayList<>();
   static ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences;
    ListView listView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.addnotes_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);

         if(item.getItemId() == R.id.addNote){


             Intent intent = new Intent(getApplicationContext(),NoteEditorActivity.class);
             startActivity(intent);
             return true;

         }
         return  false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
         sharedPreferences = getApplicationContext().getSharedPreferences("com.example.zobaer53.notes", Context.MODE_PRIVATE);


         HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);

         if(set == null){

             notes.add("Example");
         }
         else  {
             notes = new ArrayList<>(set);
         }




        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,notes)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.BLACK);

                    if (position == 0) {
                        tv.setText(position + 1 + "st Note");
                    }
                    else if (position == 1)
                    {
                        tv.setText(position + 1 + "nd Note");
                    }
                    else if (position == 2)
                    {
                        tv.setText(position + 1 + "rd Note");
                    }
                    else
                    {
                        tv.setText(position + 1 + "th Note");
                    }




                // Generate ListView Item using TextView
                return view;
            }
        };
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            Intent intent = new Intent(getApplicationContext(),NoteEditorActivity.class);
            intent.putExtra("noteId",position);
            startActivity(intent);


        });
        listView.setOnItemLongClickListener((parent, view, position, id) -> {

            new AlertDialog.Builder(this)
                    .setTitle("Do you want to remove?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            notes.remove(position);
                            arrayAdapter.notifyDataSetChanged();

                            HashSet<String> set = new HashSet<>(notes);
                            sharedPreferences.edit().putStringSet("notes",set).apply();
                        }
                    })
                    .setNegativeButton("No",null)
                    .show();


            return true;
        });
    }
}