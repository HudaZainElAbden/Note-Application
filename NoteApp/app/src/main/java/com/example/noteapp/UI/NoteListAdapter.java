package com.example.noteapp.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.databinding.RecyclerRowBinding;
import com.example.noteapp.model.Note;
import com.example.noteapp.DB.NotesDatabase;
import com.example.noteapp.R;

import java.util.ArrayList;
import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.MyViewHolder> {

    private Context context;
    private List<Note> noteList;
    private NotesDatabase db;

    OnSelectItem onSelectItem;
    public void setOnSelectItem(OnSelectItem onSelectItem) {
        this.onSelectItem = onSelectItem;
    }

    public NoteListAdapter(){
        noteList = new ArrayList<>();
    }

    public void addNewNote(Note note){
        noteList.add(note);
        notifyDataSetChanged();
    }

    public void setNoteList(List<Note> noteList){
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerRowBinding binding = RecyclerRowBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.MyViewHolder holder, int position) {

        holder.onBind(noteList.get(position), position);

        //initialize database
        holder.itemBinding.noteTitleTextView.setText(this.noteList.get(position).getTitle());

        holder.itemBinding.editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //initialize Note
                Note note = noteList.get(holder.getAdapterPosition());
                int noteId = note.getNoteId();
                String noteTitle = note.getTitle();
                String noteDescription = note.getDescription();

                Intent intent = new Intent(context , AddNewNoteActivity.class);
                intent.putExtra("noteTitle" , noteTitle);
                intent.putExtra("noteDescription" , noteDescription);
                intent.putExtra("noteId" , noteId);

                context.startActivity(intent);
            }
        });

        holder.itemBinding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize Note
                Note note = noteList.get(holder.getAdapterPosition());
                //delete note from database
                db = NotesDatabase.getInstance(context);
                db.noteDao().deleteNote(note);

                //notify when data is deleted
                int position = holder.getAdapterPosition();
                noteList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position , noteList.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.noteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        RecyclerRowBinding itemBinding;

        public MyViewHolder(RecyclerRowBinding itemBinding){
            super(itemBinding.getRoot());
            this.itemBinding =itemBinding;
        }
        void onBind(Note item , int position)
        {
            itemBinding.setNote(item);
            itemBinding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onSelectItem.onClick(position);
                    return false;
                }
            });
        }
    }

    interface OnSelectItem{
        void onClick(int position);
    }
}
