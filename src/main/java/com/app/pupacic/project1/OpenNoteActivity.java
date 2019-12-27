package com.app.pupacic.project1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class OpenNoteActivity extends AppCompatActivity {
    static Note urlNote;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_note_layout);

        Button deleteNoteBtn = (Button) findViewById(R.id.deleteNoteBtn);
        Button updateNoteBtn = (Button) findViewById(R.id.updateNoteBtn);
        Note openedNote = null;
        Bundle extras = getIntent().getExtras();
        final int id = extras.getInt("opened-note");
        if (extras != null) {
            Integer noteId = extras.getInt("opened-note");
            openedNote = DataStorage.getNoteById(noteId);
            urlNote = openedNote;
        }
        final String title = openedNote.getTitle();
        if (openedNote != null) {
            TextView titleTv = (TextView) findViewById(R.id.noteTitleTv);
            TextView contentTv = (TextView) findViewById(R.id.noteContentTv);
            TextView noteIdTitle = (TextView) findViewById(R.id.openNoteTitle);
            TextView linkTv = (TextView) findViewById(R.id.noteLinkTv);
            ImageView picture = (ImageView) findViewById(R.id.picture);

            titleTv.setText(openedNote.getTitle());
            contentTv.setText(openedNote.getContent());
            linkTv.setText(openedNote.getLink());
            if(openedNote.getImage() != null) {
                byte[] byteArray = openedNote.getImage();
                Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                picture.setImageBitmap(bm);
            }
            noteIdTitle.setText("Note ID " + String.valueOf(openedNote.getId()) + ":");
        }
        deleteNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotesDataSource notesDataSource = new NotesDataSource(getApplicationContext());
                notesDataSource.deleteNote(id, title);

                // toast that its saved
                Toast.makeText(getApplicationContext(), "Note deleted! " , Toast.LENGTH_LONG).show();

                //Todo find a better way to return to main activity so list view is updated
                Intent openNoteIntent = new Intent(OpenNoteActivity.this, MainActivity.class);
                startActivity(openNoteIntent);
            }
        });

        updateNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openNoteIntent = new Intent(OpenNoteActivity.this, UpdateNoteActivity.class);
                openNoteIntent.putExtra("update-note", id);

                startActivity(openNoteIntent);

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.location:
                Toast.makeText(getApplicationContext(),"Location selected!",Toast.LENGTH_LONG).show();
                return true;
            case R.id.picture:
                Toast.makeText(getApplicationContext(),"Picture selected!",Toast.LENGTH_LONG).show();
                return true;
            case R.id.link:
                Toast.makeText(getApplicationContext(),"Link selected!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(urlNote.getLink()));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
