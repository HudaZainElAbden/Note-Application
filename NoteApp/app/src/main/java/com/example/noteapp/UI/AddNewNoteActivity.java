package com.example.noteapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.noteapp.databinding.ActivityAddNewNoteBinding;
import com.example.noteapp.model.Note;
import com.example.noteapp.DB.NotesDatabase;
import com.example.noteapp.R;

import java.util.List;

public class AddNewNoteActivity extends AppCompatActivity {

    ActivityAddNewNoteBinding activityBinding;
    String passedNoteTitle;
    String passedNoteDescription;

    NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBinding = DataBindingUtil.setContentView(this , R.layout.activity_add_new_note);

        ViewModelFactory factory = new ViewModelFactory(this);
        noteViewModel = new ViewModelProvider(this,factory).get(NoteViewModel.class);
        noteViewModel.getNotes();

        passedNoteTitle = getIntent().getStringExtra("noteTitle");
        passedNoteDescription = getIntent().getStringExtra("noteDescription");
        if(passedNoteTitle != null && passedNoteDescription != null)
        {
            activityBinding.noteTitleInput.setText(passedNoteTitle);
            activityBinding.noteDescriptionInput.setText(passedNoteDescription);
        }

        onClick();
    }

    public void onClick()
    {
        activityBinding.saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passedNoteTitle != null && passedNoteDescription != null)
                    updateExitedNote(activityBinding.noteTitleInput.getText().toString()
                            , activityBinding.noteDescriptionInput.getText().toString());
                else
                    saveNewNote(activityBinding.noteTitleInput.getText().toString()
                            , activityBinding.noteDescriptionInput.getText().toString());
            }
        });
    }

    private void saveNewNote(String noteTitle , String noteDescription){
        NotesDatabase db = NotesDatabase.getInstance(this.getApplicationContext());
        Note note = new Note(noteTitle , noteDescription);
        Toast.makeText(AddNewNoteActivity.this,"Saved",Toast.LENGTH_SHORT).show();
        noteViewModel.addNote(note);

        //Intent intent = new Intent(this , MainActivity.class);
        //this.startActivity(intent);
        finish();
    }

    private void updateExitedNote(String updateNoteTitle , String updatedNoteDescription)
    {
        NotesDatabase db = NotesDatabase.getInstance(this.getApplicationContext());
        List<Note> noteList = db.noteDao().getNotes();

        int noteId = getIntent().getIntExtra("noteId" , 0);
        db.noteDao().updateNote(noteId , updateNoteTitle , updatedNoteDescription);

        //Intent intent = new Intent(this , MainActivity.class);
        //this.startActivity(intent);
        finish();
    }

}