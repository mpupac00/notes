package com.app.pupacic.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newNoteBtn = (Button) findViewById(R.id.newNoteBtn);

        newNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newNoteActivityIntent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivity(newNoteActivityIntent);
            }
        });


        // fill data storage from database
        NotesDataSource notesDataSource = new NotesDataSource(getApplicationContext());

        DataStorage.allNotesList = notesDataSource.getAllNotes();

        ListView notesListView = (ListView) findViewById(R.id.notesListView);

        notesListView.setAdapter(new NotesListAdapter(getApplicationContext()));

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note clickedNote = DataStorage.allNotesList.get(position);

                Intent openNoteIntent = new Intent(MainActivity.this, OpenNoteActivity.class);
                openNoteIntent.putExtra("opened-note", clickedNote.getId());

                startActivity(openNoteIntent);
            }
        });
    }
}
