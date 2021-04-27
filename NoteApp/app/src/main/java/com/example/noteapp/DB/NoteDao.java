package com.example.noteapp.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.noteapp.model.Note;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface NoteDao {

    @Query("select * from notes_table")
    List<Note> getNotes();

    @Insert(onConflict = REPLACE)
    void insertNote(Note note);

    //delete a query
    @Delete
    void deleteNote(Note note);

    //delete all queries
    @Delete
    void resetNote(List<Note> noteList);

    //update a query
    @Query ("UPDATE notes_table SET note_description = :noteDescription , note_title = :noteTitle WHERE noteId = :noteId")
    void updateNote(int noteId , String noteTitle , String noteDescription);
}
