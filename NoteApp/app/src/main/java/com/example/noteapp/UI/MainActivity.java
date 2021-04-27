package com.example.noteapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
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

public class MainActivity extends AppCompatActivity{

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
        noteViewModel.getNotes();

        initRecyclerView();
        onClick();
        subscribeToLiveData();
    }

    private void initRecyclerView(){

        noteListAdapter = new NoteListAdapter();
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


    public void onClick(){
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

}