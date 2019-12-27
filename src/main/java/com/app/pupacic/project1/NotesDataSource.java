package com.app.pupacic.project1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;


public class NotesDataSource {

    private SQLiteDatabase database;
    private	MySQLiteHelper	dbHelper;

    public NotesDataSource(Context context)	{
        dbHelper = new MySQLiteHelper(context);

        try {
            this.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void	open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void	close()	{
        database.close();
    }

    public void addNoteToDb(String title, String content, String link, byte[] image) {
        ContentValues values = new ContentValues();
        values.put("title",	title);
        values.put("content", content);
        values.put("link", link);
        values.put("image", image);
        Log.d("Deleted", "added note: query: ");
        database.insert("notes", null, values);

        /*String sql = "INSERT INTO notes VALUES (NULL, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, title);
        statement.bindString(2, content);
        statement.bindString(3,link);
        statement.bindBlob(4, image);

        statement.executeInsert();
        Log.d("Deleted", "added note: query: ");*/
    }

    public Note getNoteById(int id) {
        Note noteToReturn = new Note();

        Cursor cursor = database.rawQuery("SELECT * FROM notes WHERE _id = '" + String.valueOf(id) + "'", null);

        cursor.moveToFirst();

        if (! cursor.isAfterLast()) {
            noteToReturn.setId(cursor.getInt(0));
            noteToReturn.setTitle(cursor.getString(1));
            noteToReturn.setContent(cursor.getString(2));
            noteToReturn.setLink(cursor.getString(3));
            noteToReturn.setImage(cursor.getBlob(4));
        }

        return noteToReturn;
    }

    public void deleteNote(int Id, String Title) {
        String query = "DELETE FROM notes WHERE _id = '" + String.valueOf(Id) + "'" + " AND title" + " = '" + Title + "'";
        Log.d("Deleted", "deleteNote: query: " + query);
        database.execSQL(query);
        //database.delete("notes", "id=?", new String[]{String.valueOf(Id)});
    }
    public void updateNote(int Id, String title, String content, String link, byte[] image) {
        ContentValues values = new ContentValues();
        values.put("title",	title);
        values.put("content", content);
        values.put("link", link);
        values.put("image", image);
        database.update("notes", values,"_id = ?",new String[]{String.valueOf(Id)});
    }

    public ArrayList<Note> getAllNotes()	{
        ArrayList<Note>	notes = new	ArrayList<Note>();

        Cursor cursor =	database.rawQuery("SELECT *	FROM notes", null);
        cursor.moveToFirst();

        while(!	cursor.isAfterLast()) {
            Note note =	new	Note();
            note.setId(cursor.getInt(0));
            note.setTitle(cursor.getString(1));
            note.setContent(cursor.getString(2));
            note.setLink(cursor.getString(3));
            note.setImage(cursor.getBlob(4));
            notes.add(note);
            cursor.moveToNext();
        }

        cursor.close();

        Log.d("ANTE", "Ready " + String.valueOf(notes.size()) + " notes");
        return	notes;
    }

}
