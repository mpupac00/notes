package com.app.pupacic.project1;

import java.util.ArrayList;

public class DataStorage {

    public static ArrayList<Note> allNotesList = new ArrayList<Note>();


    public static Note getNoteById(Integer id) {
        for (int i = 0; i < allNotesList.size(); i++) {
            if (allNotesList.get(i).getId() == id) {
                return allNotesList.get(i);
            }
        }

        return null;
    }

}
