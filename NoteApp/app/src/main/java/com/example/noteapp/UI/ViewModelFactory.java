package com.example.noteapp.UI;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory{
    Context context;
    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(NoteViewModel.class)) {
            return (T) new NoteViewModel(context,"huda");
        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }
}
