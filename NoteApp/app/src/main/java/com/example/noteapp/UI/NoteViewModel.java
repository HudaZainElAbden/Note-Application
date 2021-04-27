package com.example.noteapp.UI;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.noteapp.DB.NotesDatabase;
import com.example.noteapp.model.Note;

import java.util.List;

public class NoteViewModel extends ViewModel {

    public MutableLiveData<List<Note>> notes = new MutableLiveData<>();

    private Context context;
    private NotesDatabase dataBase;

    public NoteViewModel(Context context ,String x) {
        this.context =context;
        dataBase = NotesDatabase.getInstance(context);
    }

    public void addNote(Note note){
        Log.d("lol","add notes");
        dataBase.noteDao().insertNote(note);
    }

    public void getNotes(){
        Log.d("lol","get notes");
        notes.setValue(dataBase.noteDao().getNotes());
    }

    public void deleteNote(Note note){
        dataBase.noteDao().deleteNote(note);
    }

}
