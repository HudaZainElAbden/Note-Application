package com.example.noteapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.noteapp.databinding.ActivityMainBinding;
import com.example.noteapp.model.Note;
import com.example.noteapp.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteListAdapter.OnSelectEditButton
        , NoteListAdapter.OnSelectDeleteButton{

    private NoteListAdapter noteListAdapter;
    NoteViewModel noteViewModel;
    ActivityMainBinding activityMainBinding;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        ViewModelFactory factory = new ViewModelFactory(this);
        noteViewModel = new ViewModelProvider(this,factory).get(NoteViewModel.class);

        initRecyclerView();
        onClickAddNewNote();
        subscribeToLiveData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        noteViewModel.getNotes();
    }

    private void initRecyclerView(){

        noteListAdapter = new NoteListAdapter();
        noteListAdapter.setOnSelectEditButton(this);
        noteListAdapter.setOnSelectDeleteButton(this);

        //callBack
        noteListAdapter.setOnSelectItem(new NoteListAdapter.OnSelectItem() {
            @Override
            public void onClick(int position) {
                Toast.makeText(MainActivity.this,"Position on List "+position,Toast.LENGTH_LONG).show();
            }
        });

        layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        activityMainBinding.recyclerview.setAdapter(noteListAdapter);
        activityMainBinding.recyclerview.setLayoutManager(layoutManager);
    }


    public void onClickAddNewNote(){
        activityMainBinding.addNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddNewNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    void subscribeToLiveData(){
        noteViewModel.notes.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteListAdapter.setNoteList(notes);
            }
        });
    }

    //callBack
    @Override
    public void passNotesInfoToBeUpdated(int noteId, String noteTitle, String noteDescription) {
        Intent intent = new Intent(MainActivity.this , AddNewNoteActivity.class);
        intent.putExtra("noteTitle" , noteTitle);
        intent.putExtra("noteDescription" , noteDescription);
        intent.putExtra("noteId" , noteId);

        startActivity(intent);

    }

    @Override
    public void deleteSelectedNote(Note note) {
        noteViewModel.deleteNote(note);
    }
}