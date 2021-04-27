package com.example.noteapp.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.noteapp.model.Note;

@Database(entities = {Note.class} , version =1)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NoteDao noteDao(); //db can access entities through dao
    private static NotesDatabase instance; //singleton pattern

    public static synchronized NotesDatabase getInstance(Context context) {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NotesDatabase.class , "notes_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
