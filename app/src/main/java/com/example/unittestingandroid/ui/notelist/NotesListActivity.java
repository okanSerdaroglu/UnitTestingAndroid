package com.example.unittestingandroid.ui.notelist;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.unittestingandroid.R;
import com.example.unittestingandroid.repository.NoteRepository;
import com.example.unittestingandroid.ui.note.NoteActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class NotesListActivity extends DaggerAppCompatActivity {

    private static final String TAG = "NotesListActivity";

    @Inject
    NoteRepository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: " + noteRepository);

        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);

    }
}