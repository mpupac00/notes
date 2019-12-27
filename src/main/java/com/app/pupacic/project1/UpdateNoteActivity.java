package com.app.pupacic.project1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.app.pupacic.project1.NewNoteActivity.getBytesFromBitmap;

public class UpdateNoteActivity extends AppCompatActivity {
    ImageView pictureUp;
    byte[] byteArray;
    final int REQUEST_CODE_GALLERY = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        final EditText titleEt = (EditText) findViewById(R.id.titleEt);
        final EditText contentEt = (EditText) findViewById(R.id.contentEt);
        final EditText linkEt = (EditText) findViewById(R.id.linkEt);
        Button updateNoteBtn = (Button) findViewById(R.id.updateNoteBtn);
        Button pictureUpBtn = (Button) findViewById(R.id.pictureUpBtn);
        pictureUp = (ImageView) findViewById(R.id.pictureUp);
        Note openedNote = null;
        Bundle extras = getIntent().getExtras();
        final int id = extras.getInt("update-note");
        if (extras != null) {
            Integer noteId = extras.getInt("update-note");
            openedNote = DataStorage.getNoteById(noteId);
        }

        if (openedNote != null) {
            titleEt.setText(openedNote.getTitle());
            contentEt.setText(openedNote.getContent());
            linkEt.setText(openedNote.getLink());
            if(openedNote.getImage() != null) {
                byteArray = openedNote.getImage();
                Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                pictureUp.setImageBitmap(bm);
            }
        }
        pictureUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        UpdateNoteActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });
        updateNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save note to DB
                NotesDataSource notesDataSource = new NotesDataSource(getApplicationContext());
                notesDataSource.updateNote(id,String.valueOf(titleEt.getText()), String.valueOf(contentEt.getText()),String.valueOf(linkEt.getText()), byteArray);

                // toast that its saved
                Toast.makeText(getApplicationContext(), "Note updated: " + titleEt.getText().toString(), Toast.LENGTH_LONG).show();

                //Todo find a better way to return to main activity so list view is updated
                Intent openNoteIntent = new Intent(UpdateNoteActivity.this, MainActivity.class);
                startActivity(openNoteIntent);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                byteArray = getBytesFromBitmap(bitmap, 50);
                Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                pictureUp.setImageBitmap(bm);
                Toast.makeText(getApplicationContext(), "You selected picture!", Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
